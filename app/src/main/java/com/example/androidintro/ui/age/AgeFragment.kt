package com.example.androidintro.ui.age

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v4.app.Fragment
import android.arch.lifecycle.ViewModelProvider
import android.os.Build
import android.support.annotation.RequiresApi
import android.widget.*
import com.example.androidintro.R
import com.example.androidintro.databinding.FragmentAgeBinding
import java.text.SimpleDateFormat
import java.util.*


class AgeFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentAgeBinding? = null
    private lateinit var spinner: Spinner
    private val binding get() = _binding!!
    var calendar: Calendar = Calendar.getInstance()
    var txtBirthDate : TextView? = null
    @SuppressLint("SetTextI18n")
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

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                updateDateInView()
                val age = ageInYears(calendar,Calendar.getInstance())
                binding.txtResult.text = "You have $age years"
            }

        val presentDate =  SimpleDateFormat("MM/dd/yyy",Locale.US).format(Calendar.getInstance().time)
        val btnCalculate = binding.btnCalculate


        btnCalculate.setOnClickListener {
            val ageInView = binding.txtResult.text.split("\\s+".toRegex())[2]

            if(ageInView == "0"){
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Enter your birthdate first")
                builder.setMessage("Please enter your birthdate first, thanks")
                builder.setIcon(android.R.drawable.ic_dialog_info)
                builder.setNeutralButton("Ok"){
                        _, _ ->}

                val alertDialog : AlertDialog=builder.create()
                alertDialog.show()

            }else{
                val age = ageInYears(calendar, Calendar.getInstance())
                val txtResult = binding.txtResult
                when(spinner.selectedItem.toString()){
                    "Months" -> txtResult.text = "You have ${age*12} Months"
                    "Weeks"  -> txtResult.text = "You have ${age*52} Weeks"
                    "Days" -> txtResult.text = "You have ${age*365} Days"
                    "Hours" -> txtResult.text = " You have ${age*8766} Hours"
                }
            }
        }

        txtBirthDate =  binding.txtBirthDate
        txtBirthDate!!.text = presentDate.toString()

        spinner = binding.spnTimeUnit
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this

        txtBirthDate!!.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                dateSetListener,
                Calendar.getInstance().get(Calendar.YEAR),
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

    private fun ageInYears(birthDate: Calendar, presentDay: Calendar ): Int {
        var age = presentDay.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)
        if (presentDay.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)){
            age--
        }
        if (age > 0)
            return age
        else
            Toast.makeText(requireContext(), " Please enter a valid date ... ", Toast.LENGTH_LONG).show()
            return 0
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        /*val text: String = parent?.getItemAtPosition(position).toString()
        Toast.makeText(requireContext(),text,Toast.LENGTH_SHORT).show()*/
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}



