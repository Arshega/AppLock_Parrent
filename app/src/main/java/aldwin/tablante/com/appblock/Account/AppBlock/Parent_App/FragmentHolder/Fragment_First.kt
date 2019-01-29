package aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.FragmentHolder

import aldwin.tablante.com.appblock.Account.AppBlock.Model.MyDevices
import aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.Models.deviceInfo
import aldwin.tablante.com.appblock.Account.Fetcher.MyDeviceFetch
import aldwin.tablante.com.appblock.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.appblock_intro.*
import kotlinx.android.synthetic.main.fragment_home.*

// Fragment for My current Devices
class Fragment_First : android.support.v4.app.Fragment() {
    var device: ArrayList<MyDevices> = ArrayList()
    var id: String = ""
    var email: String =""
    var noSerial: String = ""
    var no : String = ""
    var name : String =""


    var text = ""
    var user: deviceInfo? = null
    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.appblock_intro, container, false)




        return view


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        id = this.arguments.getString("value")
        email = this.arguments.getString("email")
        noSerial = this.arguments.getString("noSerial")
        no = this.arguments.getString("no")
        name = this.arguments.getString("name")

        if (!id.equals("")) {
            MyDeviceFetch().getAccountDevices(name,no,email,noSerial,id, context, recycle,imageView10,textView15)

        }
    }



}