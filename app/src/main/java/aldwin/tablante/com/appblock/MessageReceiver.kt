package aldwin.tablante.com.appblock

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_message_receiver.*

class MessageReceiver : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_receiver)

        var msgs = intent.getStringExtra("msg")
        var id = intent.getStringExtra("id")



        messagessss.setText(msgs)
        var db = FirebaseFirestore.getInstance()
        db.collection("Devices").document(id).update("ChildMessage", "")

        close.setOnClickListener {
            finish()
        }
    }
}
