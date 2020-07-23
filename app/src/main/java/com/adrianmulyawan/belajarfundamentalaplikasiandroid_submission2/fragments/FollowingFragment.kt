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
import com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.adapter.ListDataFollowingAdapter
import com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.model.DataFollowing
import com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.model.DataUsers
import com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.viewModel.FollowingViewModel
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment() {

    companion object {
        val TAG = FollowingFragment::class.java.simpleName
        const val EXTRA_DETAIL = "extra_detail"
    }

    private val listData: ArrayList<DataFollowing> = ArrayList()
    private lateinit var listDataFollowingAdapter: ListDataFollowingAdapter
    private lateinit var followingViewModel: FollowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listDataFollowingAdapter = ListDataFollowingAdapter(listData)

        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(FollowingViewModel::class.java)

        val dataFollowings = activity!!.intent.getParcelableExtra(EXTRA_DETAIL) as DataUsers

        followingViewModel.getDataUserGit(activity!!.applicationContext, dataFollowings.username.toString())

        followingViewModel.getListFollowing().observe(activity!!, Observer { listFollowing ->
            if (listFollowing != null) {
                listDataFollowingAdapter.setData(listFollowing)
                showLoading(false)
            }
        })

        config()
        showLoading(true)
    }

    private fun config() {
        recyclerViewFollowing.layoutManager = LinearLayoutManager(activity)
        recyclerViewFollowing.setHasFixedSize(true)
        recyclerViewFollowing.adapter = listDataFollowingAdapter
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressbarFollowing.visibility = View.VISIBLE
        } else {
            progressbarFollowing.visibility = View.INVISIBLE
        }
    }
}