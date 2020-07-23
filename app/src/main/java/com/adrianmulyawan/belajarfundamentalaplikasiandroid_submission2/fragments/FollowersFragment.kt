package com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.R
import com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.adapter.ListDataFollowersAdapter
import com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.model.DataFollowers
import com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.model.DataUsers
import com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.viewModel.FollowersViewModel
import kotlinx.android.synthetic.main.fragment_followers.*

class FollowersFragment : Fragment() {

    companion object {
        val TAG = FollowersFragment::class.java.simpleName
        const val EXTRA_DETAIL = "extra_detail"
    }

    private val listData: ArrayList<DataFollowers> = ArrayList()
    private lateinit var listDataFollowersAdapter: ListDataFollowersAdapter
    private lateinit var followersViewModel: FollowersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listDataFollowersAdapter = ListDataFollowersAdapter(listData)

        followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(FollowersViewModel::class.java)

        val dataFollowers = activity!!.intent.getParcelableExtra(EXTRA_DETAIL) as DataUsers

        followersViewModel.getDataUserGit(activity!!.applicationContext, dataFollowers.username.toString())

        followersViewModel.getListFollower().observe(activity!!, Observer { listFollowers ->
            if (listFollowers != null) {
                listDataFollowersAdapter.setData(listFollowers)
                showLoading(false)
            }
        })

        config()
        showLoading(true)
    }

    private fun config() {
        recyclerViewFollowers.layoutManager = LinearLayoutManager(activity)
        recyclerViewFollowers.setHasFixedSize(true)
        recyclerViewFollowers.adapter = listDataFollowersAdapter
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressbarFollowers.visibility = View.VISIBLE
        } else {
            progressbarFollowers.visibility = View.INVISIBLE
        }
    }
}