package com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.R
import com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.fragments.FollowersFragment
import com.adrianmulyawan.belajarfundamentalaplikasiandroid_submission2.fragments.FollowingFragment

class ViewPagerDetailAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val tabLayoutTitles = intArrayOf(
        R.string.follower_tabs,
        R.string.following_tabs
    )

    private val pages = listOf(
        FollowersFragment(),
        FollowingFragment()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(tabLayoutTitles[position])
    }
}