package aldwin.tablante.com.appblock

import aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.FragmentHolder.Fragment_Devices
import aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.FragmentHolder.Fragment_First
import aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.FragmentHolder.Fragment_Intro
import aldwin.tablante.com.appblock.Account.Introduction.Main.Activity.MainActivity
import aldwin.tablante.com.appblock.Notifiers.GpsService
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.animation.BounceInterpolator
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_parent_layout.*
import kotlinx.android.synthetic.main.fragment_home.*

class Parent_View : AppCompatActivity() {


    var id = ""
    var fullname = ""
    val bundle = Bundle()
    val manager = supportFragmentManager
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                try {
                   showFragmentAbout()
                    return@OnNavigationItemSelectedListener true
                }
                catch (e: Exception){
                    showFragmentAbout()
                    return@OnNavigationItemSelectedListener true

                }
            }
            R.id.navigation_dashboard -> {

                try {
                    showFragmentParent()
                    return@OnNavigationItemSelectedListener true
                }
                catch (e: Exception){
                    showFragmentParent()
                    return@OnNavigationItemSelectedListener true

                }
            }
            R.id.navigation_notifications -> {
                try {
                    showFragmentotherDevice()
                    return@OnNavigationItemSelectedListener true
                }
                catch (e: Exception){

                    showFragmentotherDevice()
                    return@OnNavigationItemSelectedListener true

                }
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_parent_layout)

      /*  val adapter = myViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Fragment_Intro())
        adapter.addFragment(Fragment_First())
        adapter.addFragment(Fragment_Devices())
        frag.adapter = adapter

*/



        val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ,android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.SYSTEM_ALERT_WINDOW
                ,android.Manifest.permission.BLUETOOTH,android.Manifest.permission.INTERNET
        )
        ActivityCompat.requestPermissions(this, permissions,0)
        //////////////////////////////////////


        ////////////////////////////////////////

        val iin = intent
        var b: Bundle = iin.extras
        if(navigation.selectedItemId == R.id.navigation_home) {
            mOnNavigationItemSelectedListener.onNavigationItemSelected(navigation.menu.getItem(0))
        }
        if(navigation.selectedItemId == R.id.navigation_dashboard) {
            mOnNavigationItemSelectedListener.onNavigationItemSelected(navigation.menu.getItem(1))
        }
        if(navigation.selectedItemId == R.id.navigation_notifications) {
            mOnNavigationItemSelectedListener.onNavigationItemSelected(navigation.menu.getItem(2))
        }
try {

    fullname = b.getString("name")
    bundle.putString("value", b.getString("id"))
    bundle.putString("name", fullname)
    bundle.putString("noSerial", b.getString("noSerial"))
    bundle.putString("no", b.getString("no"))
    bundle.putString("email",b.getString("email"))

    var fBase = FirebaseFirestore.getInstance()
    var rBase = fBase.collection("Parent")
    rBase.document(b.getString("id"))

    var intservice = Intent(this@Parent_View, GpsService::class.java)
    intservice.putExtra("id", b.getString("id"))
    startService(intservice)
}
catch (e:Exception){

    e.printStackTrace()
}
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }


    fun showFragmentParent() {
try {
    val transaction = manager.beginTransaction()
    val fragment = Fragment_First()
    fragment.arguments = bundle
    transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
    transaction.replace(R.id.frag, fragment, "tagname")
    transaction.addToBackStack("tagname")
    transaction.commit()
}
catch (e:IllegalStateException){

    e.printStackTrace()
    showFragmentParent()
}

    }

    fun showFragmentotherDevice() {
try {
    val transaction = manager.beginTransaction()
    val fragment = Fragment_Devices()
    fragment.arguments = bundle
    transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
    transaction.replace(R.id.frag, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
}
catch (e:IllegalStateException){
    e.printStackTrace()
    showFragmentotherDevice()

}

    }


    fun showFragmentAbout() {
try {
    val transaction = manager.beginTransaction()
    val fragment = Fragment_Intro()
    fragment.arguments = bundle
    transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
    transaction.replace(R.id.frag, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
}
catch (e:IllegalStateException){
    e.printStackTrace()
    showFragmentAbout()

}

    }

    override fun onPause() {
        super.onPause()
        if(navigation.selectedItemId == R.id.navigation_home) {
            mOnNavigationItemSelectedListener.onNavigationItemSelected(navigation.menu.getItem(0))
        }
        if(navigation.selectedItemId == R.id.navigation_dashboard) {
            mOnNavigationItemSelectedListener.onNavigationItemSelected(navigation.menu.getItem(1))
        }
        if(navigation.selectedItemId == R.id.navigation_notifications) {
            mOnNavigationItemSelectedListener.onNavigationItemSelected(navigation.menu.getItem(2))
        }

    }

    override fun onResume() {
        super.onResume()
        if(navigation.selectedItemId == R.id.navigation_home) {
            mOnNavigationItemSelectedListener.onNavigationItemSelected(navigation.menu.getItem(0))
        }
        if(navigation.selectedItemId == R.id.navigation_dashboard) {
            mOnNavigationItemSelectedListener.onNavigationItemSelected(navigation.menu.getItem(1))
        }
        if(navigation.selectedItemId == R.id.navigation_notifications) {
            mOnNavigationItemSelectedListener.onNavigationItemSelected(navigation.menu.getItem(2))
        }



    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(navigation.selectedItemId == R.id.navigation_home) {
            mOnNavigationItemSelectedListener.onNavigationItemSelected(navigation.menu.getItem(0))
        }
        if(navigation.selectedItemId == R.id.navigation_dashboard) {
            mOnNavigationItemSelectedListener.onNavigationItemSelected(navigation.menu.getItem(1))
        }
        if(navigation.selectedItemId == R.id.navigation_notifications) {
            mOnNavigationItemSelectedListener.onNavigationItemSelected(navigation.menu.getItem(2))
        }
    }



   inner class myViewPagerAdapter(manager:FragmentManager) : FragmentPagerAdapter(manager){
        private val fragmentlist : MutableList<Fragment> = ArrayList()
        override fun getCount(): Int {
return  fragmentlist.size       }

        override fun getItem(position: Int): Fragment {
            fragmentlist[position].arguments =bundle
            mOnNavigationItemSelectedListener.onNavigationItemSelected(navigation.menu.getItem(position))
return fragmentlist[position]        }
         fun addFragment(fragment:Fragment){
             fragmentlist.add(fragment)

         }
    }
}
