package aldwin.tablante.com.appblock

import android.os.Build
import android.os.Bundle

import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.time_set.*

class TimerSet : AppCompatActivity() {
    var id = ""
    var serial = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.time_set)

        id = intent.getStringExtra("id")
        serial = intent.getStringExtra("serial")


        trigger.setOnClickListener {
            var db = FirebaseFirestore.getInstance()
            db.collection("Devices").document(serial).update("TriggerAlarm", true)
            Thread.sleep(2000)
            db.collection("Devices").document(serial).update("TriggerAlarm", false)
        }

    }


}