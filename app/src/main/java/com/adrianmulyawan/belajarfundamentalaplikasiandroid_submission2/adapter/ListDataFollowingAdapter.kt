package com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.R
import com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.model.DataFollowing
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.list_user.view.*

class ListDataFollowingAdapter(private val listDataFollowing: ArrayList<DataFollowing>) :
    RecyclerView.Adapter<ListDataFollowingAdapter.ListDataFollowingHolder>() {

    fun setData(items: ArrayList<DataFollowing>) {
        listDataFollowing.clear()
        listDataFollowing.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListDataFollowingHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_user, viewGroup, false)
        return ListDataFollowingHolder(mView)
    }

    override fun onBindViewHolder(holder: ListDataFollowingHolder, i: Int) {
        holder.bind(listDataFollowing[i])
    }

    override fun getItemCount(): Int {
        return listDataFollowing.size
    }

   inner class ListDataFollowingHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dataFollowing: DataFollowing) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(dataFollowing.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(ci_avatar)

                tv_fullName.text = dataFollowing.name
                tv_username.text = itemView.context.getString(R.string.adrianmulyawan, dataFollowing.username)
                tv_location.text = itemView.context.getString(R.string.location, dataFollowing.location)
                tv_followers.text = itemView.context.getString(R.string.follower, dataFollowing.followers)
                tv_following.text = itemView.context.getString(R.string.following, dataFollowing.following)
            }
        }
    }
}