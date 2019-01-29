package aldwin.tablante.com.appblock

import aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.Models.Applications
import aldwin.tablante.com.appblock.Account.Model.Apps
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import android.support.v4.view.ViewPager
import android.view.View


class RunningAppList:AppCompatActivity() {
    var runningapps: ArrayList<Apps> = ArrayList()
    private var id = ""
    var adapter: ArrayAdapter<String>? = null
    private var serial = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.runningapps)
        id = intent.getStringExtra("id")
        serial = intent.getStringExtra("serial")

        var db = FirebaseFirestore.getInstance()

        db.collection("Devices")
                .whereEqualTo("Serial", serial)
                .addSnapshotListener(object : EventListener<QuerySnapshot> {

                    override fun onEvent(p0: QuerySnapshot?, p1: FirebaseFirestoreException?) {
                        if (p0!!.isEmpty) {
                            Toast.makeText(applicationContext, "Device is Restarting the Connector", Toast.LENGTH_SHORT).show()
                        } else {
                            for (doc in p0!!.documents) {
                                var label: Applications = doc.toObject(Applications::class.java)
                                var labelList = label.Applications
                                val recyclerView = findViewById<RecyclerView>(R.id.recycleView)
                                recyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayout.VERTICAL, false)

                                for (i in 0 until labelList.size) {
                                    runningapps.add(Apps(labelList[i], R.drawable.parentsize))
                                }
                                var adapter = AppsAdapter(runningapps)
                                recyclerView.adapter = adapter
                            }
                        }

                    }
                })
    }

}








