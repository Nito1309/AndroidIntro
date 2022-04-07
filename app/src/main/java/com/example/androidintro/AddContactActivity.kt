package com.example.androidintro

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.androidintro.databinding.ActivityAddContactBinding
import com.example.androidintro.ui.home.ContactViewModel as ContactViewModel

class AddContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnAddContact = binding.btnAddContact
        val etxtBirthdate = binding.etxtBirthDate


        btnAddContact.setOnClickListener {

            val etxtName = binding.etxtName.text.toString()
            val etxtEmail = binding.etxtEmail.text.toString()
            val etxtAddress = binding.etxtAddress.text.toString()
            val etxtTel = binding.etxtTel.text.toString()
            val data = Intent()

            data.putExtra("name", etxtName)
            data.putExtra("email", etxtEmail)
            data.putExtra("birthdate", etxtBirthdate.text.toString())
            data.putExtra("address", etxtAddress)
            data.putExtra("tel", etxtTel)

            setResult(Activity.RESULT_OK,data)
            finish()
        }
    }
}





