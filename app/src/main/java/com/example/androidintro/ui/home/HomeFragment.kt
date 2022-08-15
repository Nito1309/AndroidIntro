package com.example.androidintro.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
//import android.support.v4.app.Fragment
//import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
//import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidintro.AddContactActivity
import com.example.androidintro.MainActivity
import com.example.androidintro.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val contactData = ArrayList<ContactViewModel>()
    private val adapter = ContactRecyclerAdapter(contactData)
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("NotifyDataSetChanged")
    fun addContact(contact : ContactViewModel){
        contactData.add(contact)
        adapter.notifyDataSetChanged()
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
        val btnAddContact = binding.btnAddContact
        val recyclerView = binding.recycleView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        btnAddContact.setOnClickListener {
            activity?.let {
                val intent = Intent (it, AddContactActivity::class.java)
                startActivityForResult(intent, 1)
            }
        }
        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK)return
        when(requestCode){
            1 -> {
                val contact = ContactViewModel(
                    data?.getStringExtra("name").toString(),
                    data?.getStringExtra("email").toString(),
                    data?.getStringExtra("birthdate").toString(),
                    data?.getStringExtra("address").toString(),
                    data?.getStringExtra("tel").toString()
                )
                addContact(contact)
                //Toast.makeText(requireContext(),"hollaaaa", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



