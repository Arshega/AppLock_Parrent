package aldwin.tablante.com.appblock.Histories

import aldwin.tablante.com.appblock.Account.AppBlock.Model.OtherDevice
import aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.AdapterHolder.OtherDeviceAdapter
import aldwin.tablante.com.appblock.R
import aldwin.tablante.com.appblock.SearchText
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.firestore.*
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_otherdevices.*
import kotlinx.android.synthetic.main.search_history.*

class SearchHistory_Fragment:Fragment() {
    var adapter: SearchAdapter? = null
    var data = FirebaseDatabase.getInstance()
    var dataref: DatabaseReference = data.getReference("Devices")

    var FireData = FirebaseFirestore.getInstance()

    var id = ""
    var name = ""
    var arrofDevices: ArrayList<SearchText> = ArrayList()

    override fun onStart() {
        super.onStart()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        devicesdb(this.arguments.getString("serial"))
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.search_history,container, false)
        return view
    }




    fun devicesdb(serial:String) {

        FireData.collection("Search")
                .document(serial)
                .collection("History")
                .whereEqualTo("DeviceID",serial)
                .orderBy("TimeS",Query.Direction.DESCENDING)

                .addSnapshotListener(object:EventListener<QuerySnapshot>{

                    override fun onEvent(p0: QuerySnapshot?, p1: FirebaseFirestoreException?) {
                        arrofDevices!!.clear()
                    for(doc in p0!!.documents){
                        var devicet = doc.toObject(SearchText::class.java)
                        arrofDevices!!.add(devicet)


                    }
                        adapter = SearchAdapter(arrofDevices, activity.applicationContext)
                        var layout_manager = LinearLayoutManager(activity.applicationContext)
                        layout_manager.reverseLayout = false
                        searchRview.layoutManager = layout_manager
                        searchRview.setHasFixedSize(true)
                       searchRview.adapter = adapter

                    }

                })



    }

}