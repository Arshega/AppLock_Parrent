package aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.FragmentHolder

import aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.Models.Applications
import aldwin.tablante.com.appblock.Account.Model.Apps
import aldwin.tablante.com.appblock.AppsAdapter
import aldwin.tablante.com.appblock.R
import aldwin.tablante.com.appblock.R.layout.runningapps
import android.app.Fragment
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import android.support.v7.widget.DefaultItemAnimator



class RunningAppFragment : android.support.v4.app.Fragment() {
    var runningapps: ArrayList<Apps> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.activity_running_apps, container, false)
        var mRecyclerView = view?.findViewById(R.id.recycleView) as RecyclerView
        var mLayoutManager = LinearLayoutManager(this.getActivity())
        mRecyclerView.setLayoutManager(mLayoutManager)
        var arrayList = arrayListOf<Apps>(Apps("Hi", R.drawable.parentsize))
        var adapter = AppsAdapter(arrayList)
        mRecyclerView.setAdapter(adapter)
        return view
//        var db = FirebaseFirestore.getInstance()
//        var serial = Build.SERIAL
//        db.collection("Devices")
//                .whereEqualTo("Serial", serial)
//                .addSnapshotListener(object : EventListener<QuerySnapshot> {
//                    override fun onEvent(p0: QuerySnapshot?, p1: FirebaseFirestoreException?) {
//                        if (p0!!.isEmpty) {
//                            Toast.makeText(activity, "Device is Restarting the Connector", Toast.LENGTH_SHORT).show()
//                        } else {
//                            for (doc in p0!!.documents) {
//                                var label: Applications = doc.toObject(Applications::class.java)
//                                var labelList = label.Applications
//                                recyclerView?.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
//                                for (i in 0 until labelList.size) {
//                                    runningapps.add(Apps(labelList[i], R.drawable.parentsize))
//                                }
//                                var adapter = AppsAdapter(runningapps)
//                                recyclerView?.adapter = adapter
//                            }
//                        }
//                    }
//                })
    }

}// Required empty public constructor