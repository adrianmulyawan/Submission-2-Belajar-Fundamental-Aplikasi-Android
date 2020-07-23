package com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.adapter.ViewPagerDetailAdapter
import com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.model.DataUsers
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail_user.*

class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }

    private var title: String = "Detail User"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        val orientation = resources.configuration.orientation

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            viewpager.layoutParams.height = resources.getDimension(R.dimen.height).toInt()
        } else {
            viewpager.layoutParams.height = resources.getDimension(R.dimen.height1).toInt()
        }

        setActionBarTitle(title)
        configViewPagerDetail()
        setData()
    }

    private fun setActionBarTitle(title: String) {
        if (supportActionBar != null) {
            supportActionBar!!.title = title
        }
    }

    private fun configViewPagerDetail() {
        val viewPagerDetailAdapter = ViewPagerDetailAdapter(this, supportFragmentManager)
        viewpager.adapter = viewPagerDetailAdapter
        tabs.setupWithViewPager(viewpager)
        supportActionBar?.elevation = 0f
    }

    private fun setData() {
        val dataUsers = intent.getParcelableExtra(EXTRA_DETAIL) as DataUsers
        Glide.with(this)
            .load(dataUsers.avatar)
            .apply(RequestOptions().override(140, 140))
            .into(ci_avatar)
        tv_fullName.text = dataUsers.name
        tv_username.text = getString(R.string.adrianmulyawan, dataUsers.username)
        tv_work.text = dataUsers.company
        tv_location.text = dataUsers.location
        tv_repository.text = dataUsers.repository
        tv_followers.text = dataUsers.followers
        tv_following.text = dataUsers.following
    }
}