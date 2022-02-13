package com.example.androidintro.ui.age

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v4.app.Fragment
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.widget.*
import com.example.androidintro.R
import com.example.androidintro.databinding.FragmentAgeBinding
import java.text.SimpleDateFormat
import java.time.*
import java.util.*


class AgeFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentAgeBinding? = null
    private lateinit var spinner: Spinner
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var calendar: Calendar = Calendar.getInstance()
    var txtBirthDate : TextView? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val ageViewModel =
                ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(AgeViewModel::class.java)

        _binding = FragmentAgeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textAge
        ageViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.timeUnits,
                android.R.layout.simple_spinner_item
        )

        txtBirthDate =  binding.txtBirthDate
        txtBirthDate!!.text = LocalDate.now(ZoneId.systemDefault()).toString()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                updateDateInView()
            }


        spinner = binding.spnTimeUnit
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this
        txtBirthDate!!.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                dateSetListener, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        return root
    }

    private fun updateDateInView() {
        val dateFormat = "MM/dd/yyyy"
        val sdf = SimpleDateFormat(dateFormat,Locale.US)
        txtBirthDate!!.text = sdf.format(calendar.time)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val text: String = parent?.getItemAtPosition(position).toString()
        Toast.makeText(requireContext(),text,Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}


