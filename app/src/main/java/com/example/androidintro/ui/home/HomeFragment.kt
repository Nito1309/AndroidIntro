package com.example.androidintro.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.content.Intent
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidintro.AddContactActivity
import com.example.androidintro.AlarmActivity.AlarmActivity
import com.example.androidintro.AndroidIntroApplication.Companion.contactData
import com.example.androidintro.R
import com.example.androidintro.databinding.FragmentHomeBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    //private val contactData = ArrayList<ContactViewModel>()

    private val adapter = ContactRecyclerAdapter(contactData)
    private var addClicked = false
    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_open_anim)}
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_close_anim)}
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.from_bottom_anim)}
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.to_bottom_anim)}
    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == Activity.RESULT_OK){
                when(it.data?.getStringExtra("requestCode").toString()){
                    "1" -> {
                        val contact = ContactViewModel(
                            it.data?.getStringExtra("name").toString(),
                            it.data?.getStringExtra("email").toString(),
                            it.data?.getStringExtra("birthdate").toString(),
                            it.data?.getStringExtra("address").toString(),
                            it.data?.getStringExtra("tel").toString()
                        )
                        addContact(contact)
                    }
                    "2" -> {

                    }
                }
            }
        }
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    @SuppressLint("NotifyDataSetChanged")
    fun addContact(contact : ContactViewModel){
        contactData.add(contact)
        adapter.notifyItemInserted(adapter.itemCount)
    }
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
                ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        val btnAdd = binding.btnAdd
        val btnAddContact = binding.btnAddContact
        val btnAddAlarm = binding.btnAddAlarm
        val recyclerView = binding.recycleView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        btnAdd.setOnClickListener {
            activity?.let {
                onAddButtonClicked(btnAdd,btnAddContact,btnAddAlarm)
            }
        }
        btnAddContact.setOnClickListener {
            activity?.let {
                val intent = Intent (context, AddContactActivity::class.java)
                onAddButtonClicked(btnAdd,btnAddContact,btnAddAlarm)
                getResult.launch(intent)
            }
        }
        btnAddAlarm.setOnClickListener {
            activity?.let {
                val intent = Intent (context, AlarmActivity::class.java)
                onAddButtonClicked(btnAdd,btnAddContact,btnAddAlarm)
                getResult.launch(intent)
            }
        }
        return root
    }

    private fun onAddButtonClicked(btnAdd: FloatingActionButton, btnAddContact: FloatingActionButton, btnAddAlarm: FloatingActionButton){
        setVisility(addClicked, btnAddContact, btnAddAlarm)
        setAnimation(addClicked, btnAdd, btnAddContact, btnAddAlarm)
        addClicked = !addClicked
    }

    private fun setAnimation(clicked: Boolean, btnAdd: FloatingActionButton, btnAddContact: FloatingActionButton, btnAddAlarm: FloatingActionButton) {
        if(!clicked){
            btnAddContact.startAnimation(fromBottom)
            btnAddAlarm.startAnimation(fromBottom)
            btnAdd.startAnimation(rotateOpen)
        }else{
            btnAddContact.startAnimation(toBottom)
            btnAddAlarm.startAnimation(toBottom)
            btnAdd.startAnimation(rotateClose)
        }
    }

    @SuppressLint("RestrictedApi")
    private fun setVisility(clicked: Boolean, btnAddContact: FloatingActionButton, btnAddAlarm: FloatingActionButton) {
        if(!clicked){
            btnAddContact.visibility = View.VISIBLE
            btnAddAlarm.visibility = View.VISIBLE
        }else{
            btnAddContact.visibility = View.INVISIBLE
            btnAddAlarm.visibility = View.INVISIBLE
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



