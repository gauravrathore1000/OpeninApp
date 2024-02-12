package com.example.openinapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openinapp.Model.dashboardAPIResponse.DashboardApiResponse
import com.example.openinapp.Model.dashboardAPIResponse.TopLink
import com.example.openinapp.databinding.LinksRvItemsBinding

class LinksAdapter(var context: Context, var topLink: ArrayList<TopLink>) : RecyclerView.Adapter<LinksAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LinksRvItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.binding.tvLinksName.text = topLink[position].title
            holder.binding.tvDate.text = topLink[position].times_ago
            holder.binding.tvClicksNumbers.text = topLink[position].total_clicks.toString()
            holder.binding.tvLinksAddress.text = topLink[position].web_link
            Glide.with(context).load(topLink[position].original_image).into(holder.binding.amazonIcon)
        }catch (e:Exception){
            e.message
        }
    }
    override fun getItemCount(): Int = topLink.size

    class ViewHolder(val binding : LinksRvItemsBinding)  : RecyclerView.ViewHolder(binding.root) {}
}