//package aldwin.tablante.com.appblock
//
//import aldwin.tablante.com.appblock.Account.Model.ChildMessage
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.support.v4.content.ContextCompat.startActivity
//import android.widget.Toast
//import com.google.firebase.firestore.EventListener
//import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.firestore.FirebaseFirestoreException
//import com.google.firebase.firestore.QuerySnapshot
//
//class MyBroadcastReceiver: BroadcastReceiver() {
//    override fun onReceive(context: Context?, intent: Intent?) {
//        try {
//            var serial = android.os.Build.SERIAL
//        var db = FirebaseFirestore.getInstance()
//        db.collection("Devices")
//                .whereEqualTo("Serial", serial)
//                .addSnapshotListener(object : EventListener<QuerySnapshot> {
//                    override fun onEvent(p0: QuerySnapshot?, p1: FirebaseFirestoreException?) {
//                        for (doc in p0!!.documents) {
//                            var message = doc.toObject(ChildMessage::class.java)
//                            var msg = message.ChildMessage
//                            if (!msg.equals("")){
//                                var intent = Intent(context, MessageReceiver::class.java)
//                                intent.putExtra("id", doc.id)
//                                intent.putExtra("msg", msg)
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                                context?.startActivity(intent)
//                            }
//                        }
//                    }
//                })
//        }catch (t: Throwable){
//
//        }
//    }
//}