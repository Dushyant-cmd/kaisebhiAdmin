package com.example.kaisebhiadmin.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MainViewPagerAdapter(private val fm: FragmentManager): FragmentPagerAdapter(fm) {
    private val fragmentList = ArrayList<Fragment>()
    private val titleList = ArrayList<String>()
    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

    fun addFragmentAndTitle(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        titleList.add(title)
    }
}