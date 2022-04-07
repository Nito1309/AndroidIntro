package com.example.androidintro.ui.home


import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.androidintro.R
import com.example.androidintro.ui.home.ContactRecyclerAdapter.*

class ContactRecyclerAdapter (private val data: List<ContactViewModel>) : RecyclerView.Adapter<ViewHolder>() {

    private var mList: MutableList<ContactViewModel> = data as MutableList<ContactViewModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycle_contact_view, parent, false)
        return  ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ContactViewModel = mList[position]
        holder.txtName.text = "${holder.txtName.text} ${ContactViewModel.name}"
        holder.txtEmail.text = "${holder.txtEmail.text} ${ContactViewModel.email}"
        holder.txtBirthdate.text = "${holder.txtBirthdate.text} ${ContactViewModel.birthdate}"
        holder.txtAddress.text = "${holder.txtAddress.text} ${ContactViewModel.address}"
        holder.txtTel.text = "${holder.txtTel.text} ${ContactViewModel.tel}"
        holder.btnDelete.setOnClickListener {
            deleteItem(holder.adapterPosition)
            notifyItemRemoved(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
    
    private fun deleteItem(index : Int){
        mList.removeAt(index)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtEmail: TextView = itemView.findViewById(R.id.txtEmail)
        val txtBirthdate: TextView = itemView.findViewById(R.id.txtBirthDate)
        val txtAddress: TextView = itemView.findViewById(R.id.txtAddress)
        val txtTel: TextView = itemView.findViewById(R.id.txtTelephone)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete)
    }
}



