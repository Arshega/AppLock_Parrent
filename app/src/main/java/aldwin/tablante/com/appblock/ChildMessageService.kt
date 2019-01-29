//package aldwin.tablante.com.appblock
//
//import aldwin.tablante.com.appblock.Account.Model.ChildMessage
//import aldwin.tablante.com.appblock.Account.Model.OptionMessage
//import android.app.Service
//import android.content.Intent
//import android.os.IBinder
//import android.widget.Toast
//import com.google.firebase.firestore.EventListener
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.firestore.FirebaseFirestoreException
//import com.google.firebase.firestore.QuerySnapshot
//
//class ChildMessageService : Service() {
//
//    override fun onBind(intent: Intent): IBinder? {
//        return null
//    }
//
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        var serial = android.os.Build.SERIAL
//        var db = FirebaseFirestore.getInstance()
//        db.collection("Devices")
//                .whereEqualTo("Serial", serial)
//                .addSnapshotListener(object : EventListener<QuerySnapshot> {
//                    override fun onEvent(p0: QuerySnapshot?, p1: FirebaseFirestoreException?) {
//                        for (doc in p0!!.documents) {
//                            var message = doc.toObject(ChildMessage::class.java)
//                            var msg = message.ChildMessage
//                            if (!msg.equals("")){
//                                var intent = Intent(applicationContext, MessageReceiver::class.java)
//                                intent.putExtra("id", doc.id)
//                                intent.putExtra("msg", msg)
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                                startActivity(intent)
//                            }
//                        }
//                    }
//                })
//        return START_STICKY
//    }
//}
