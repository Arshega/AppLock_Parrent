package aldwin.tablante.com.appblock

import aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.FragmentHolder.AppUsageFragment
import aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.FragmentHolder.RunningAppFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MyPagerAdapter(fm:FragmentManager):FragmentPagerAdapter(fm) {


    var mFm = fm
    var mFragmentItems:ArrayList<Fragment> = ArrayList()
    var mFragmentTitles:ArrayList<String> = ArrayList()

    //we need to create function to add fragments

    fun addFragments(fragmentItem:Fragment,fragmentTitle:String){
        mFragmentItems.add(fragmentItem)
        mFragmentTitles.add(fragmentTitle)
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentItems[position]
    }

    override fun getCount(): Int {
        return mFragmentItems.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mFragmentTitles[position]
    }

}