package aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.FragmentHolder

import aldwin.tablante.com.appblock.R
import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class AppUsageFragment : android.support.v4.app.Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.activity_app_usage, container, false)
    }

}