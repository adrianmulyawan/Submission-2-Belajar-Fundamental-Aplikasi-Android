package com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.DetailUserActivity
import com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.R
import com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.model.DataUsers
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.list_user.view.*

class ListDataUserAdapter(private val listDataUserGit: ArrayList<DataUsers>) :
    RecyclerView.Adapter<ListDataUserAdapter.ListDataUserHolder>(){

    fun setData(items: ArrayList<DataUsers>) {
        listDataUserGit.clear()
        listDataUserGit.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListDataUserHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_user, viewGroup, false)
        return ListDataUserHolder(mView)
    }

    override fun getItemCount(): Int {
        return listDataUserGit.size
    }

    override fun onBindViewHolder(holder: ListDataUserHolder, i: Int) {
        holder.bind(listDataUserGit[i])

        val data = listDataUserGit[i]
        holder.itemView.setOnClickListener {
            val dataUserIntent = DataUsers(
                data.username,
                data.name,
                data.avatar,
                data.location,
                data.company,
                data.repository,
                data.followers,
                data.following
            )
            val intent = Intent(it.context, DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.EXTRA_DETAIL, dataUserIntent)
            it.context.startActivity(intent)
        }
    }

    inner class ListDataUserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dataUsers: DataUsers) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(dataUsers.avatar)
                    .apply(RequestOptions().override(100,100))
                    .into(ci_avatar)

                tv_fullName.text = dataUsers.name
                tv_username.text = itemView.context.getString(R.string.adrianmulyawan, dataUsers.username)
                tv_location.text = itemView.context.getString(R.string.location, dataUsers.location)
                tv_followers.text = itemView.context.getString(R.string.follower, dataUsers.followers)
                tv_following.text = itemView.context.getString(R.string.following, dataUsers.following)
            }
        }
    }
}