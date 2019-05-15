package br.com.brozoski.cowabunga.adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import br.com.brozoski.cowabunga.fragments.MainFragment
import br.com.brozoski.cowabunga.fragments.SearchFragment
import br.com.brozoski.cowabunga.fragments.UserFragment


class FragmentViewPagerAdapter(context : Context, fragmentManager : FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return UserFragment()
            1 -> return MainFragment()
            else -> return SearchFragment()
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return super.getPageTitle(position)
    }
}
