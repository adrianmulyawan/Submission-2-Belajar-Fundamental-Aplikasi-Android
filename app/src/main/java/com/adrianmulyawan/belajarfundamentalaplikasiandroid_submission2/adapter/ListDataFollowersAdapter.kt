package com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.R
import com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.model.DataFollowers
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.list_user.view.*

class ListDataFollowersAdapter(private val listDataFollowers: ArrayList<DataFollowers>) :
    RecyclerView.Adapter<ListDataFollowersAdapter.ListDataFollowersHolder>() {

    fun setData(items: ArrayList<DataFollowers>) {
        listDataFollowers.clear()
        listDataFollowers.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListDataFollowersHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_user, viewGroup, false)
        return ListDataFollowersHolder(mView)
    }

    override fun onBindViewHolder(holder: ListDataFollowersHolder, i: Int) {
        holder.bind(listDataFollowers[i])
    }

    override fun getItemCount(): Int {
        return listDataFollowers.size
    }

    inner class ListDataFollowersHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dataFollowers: DataFollowers) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(dataFollowers.avatar)
                    .apply(RequestOptions().override(100,100))
                    .into(ci_avatar)

                tv_fullName.text = dataFollowers.name
                tv_username.text = itemView.context.getString(R.string.adrianmulyawan, dataFollowers.username)
                tv_location.text = itemView.context.getString(R.string.location, dataFollowers.location)
                tv_followers.text = itemView.context.getString(R.string.follower, dataFollowers.followers)
                tv_following.text = itemView.context.getString(R.string.following, dataFollowers.following)
            }
        }
    }

}