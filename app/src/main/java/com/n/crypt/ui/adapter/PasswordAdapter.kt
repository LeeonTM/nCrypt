package com.n.crypt.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.n.crypt.R
import com.n.crypt.database.model.Password
import kotlinx.android.synthetic.main.item_password.view.*

class PasswordAdapter(private var passwords: List<Password>): RecyclerView.Adapter<PasswordAdapter.ViewHolder>() {
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_password, parent, false))
    }

    override fun getItemCount(): Int {
        return passwords.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(passwords[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(password: Password) {
            itemView.tvName.text = password.name
        }
    }
}