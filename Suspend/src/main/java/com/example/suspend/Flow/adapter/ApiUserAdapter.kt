package com.example.suspend.Flow.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.suspend.Flow.model.ApiUser
import com.example.suspend.R
import kotlinx.android.synthetic.main.item_layout.view.*

class ApiUserAdapter(private val users:ArrayList<ApiUser>):RecyclerView.Adapter<ApiUserAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        fun bind(user: ApiUser){
            itemView.textViewUserName.text = user.name
            itemView.textViewUserEmail.text = user.email
            Glide.with(itemView.imageViewAvatar.context)
                    .load(user.avatar)
                    .into(itemView.imageViewAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
       var view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return DataViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int  = users.size

    fun addData(list: List<ApiUser>){
        users.clear()
        users.addAll(list)
        notifyDataSetChanged()
    }
}