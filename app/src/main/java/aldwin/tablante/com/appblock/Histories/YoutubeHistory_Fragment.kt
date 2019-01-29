package aldwin.tablante.com.appblock.Histories

import aldwin.tablante.com.appblock.Account.AppBlock.Model.OtherDevice
import aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.AdapterHolder.OtherDeviceAdapter
import aldwin.tablante.com.appblock.R
import aldwin.tablante.com.appblock.SearchText
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.youtube_view.*
import android.webkit.WebViewClient
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class YoutubeHistory_Fragment : Fragment() {


    override fun onStart() {
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.youtube_view, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewWeb.settings.javaScriptEnabled = true
        viewWeb.webViewClient = WebViewClient()
        viewWeb.loadUrl("https://www.youtube.com/feed/history")


    }



}