package aldwin.tablante.com.appblock

import aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.Models.Applications
import aldwin.tablante.com.appblock.Account.Model.Apps
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

class ApplicationUsage : AppCompatActivity() {
    var runningapps: ArrayList<Usage> = ArrayList()
    private var id = ""
    var adapter: ArrayAdapter<String>? = null
    private var serial = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_usage2)
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
                                var label: AppUsage = doc.toObject(AppUsage::class.java)
                                var labelList = label.AppUsage
                                labelList = ArrayList(LinkedHashSet<String>(labelList))
                                val recyclerView = findViewById<RecyclerView>(R.id.recycleView)
                                recyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayout.VERTICAL, false)
                                labelList = ArrayList(LinkedHashSet<String>(labelList))
                                for (i in 0 until labelList.size) {
                                    runningapps.add(Usage(labelList[i], R.drawable.smartphone))
                                }
                                var adapter = UsageAdapter(runningapps)
                                recyclerView.adapter = adapter
                            }
                        }

                    }
                })
    }
}
