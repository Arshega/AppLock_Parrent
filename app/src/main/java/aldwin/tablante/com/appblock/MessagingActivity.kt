package aldwin.tablante.com.appblock

import aldwin.tablante.com.appblock.Account.Model.OptionMessage
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.github.angads25.toggle.interfaces.OnToggledListener
import com.github.angads25.toggle.model.ToggleableView
import com.github.angads25.toggle.widget.LabeledSwitch
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.sendmessage.*
import java.util.HashMap

class MessagingActivity:AppCompatActivity() {
    var id = ""
    var serial = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sendmessage)

       id = intent.getStringExtra("id")
         serial = intent.getStringExtra("serial")
        var firestore = FirebaseFirestore.getInstance()
        var noterefs = firestore.collection("Devices").document(serial)
        button3.setOnClickListener {
            if(editText3.text.toString().isNotBlank()) {
                sendMsg(editText3.text.toString())
                editText3.text.clear()
            }
            else{
                editText3.error = "Non-Blank Message"
            }
        }
        var labeledSwitch = findViewById<LabeledSwitch>(R.id.swicher)
        var db = FirebaseFirestore.getInstance()
        db.collection("Devices")
                .whereEqualTo("Serial", serial)
                .addSnapshotListener(object : EventListener<QuerySnapshot> {
                    override fun onEvent(p0: QuerySnapshot?, p1: FirebaseFirestoreException?) {
                        for (doc in p0!!.documents) {
                            var option: OptionMessage = doc.toObject(OptionMessage::class.java)
                            var options = option.OptionMessage
                            if (options == "On"){
                                labeledSwitch.isOn = true
                            }else if (options == "Off"){
                                labeledSwitch.isOn = false
                            }
                        }
                    }
                })

        labeledSwitch.setOnToggledListener(object : OnToggledListener{
            override fun onSwitched(toggleableView: ToggleableView?, isOn: Boolean) {
                if (isOn){
                    var mmap: HashMap<String, Any?> = HashMap()
                    mmap.put("OptionMessage","On")
                    noterefs.set(mmap, SetOptions.merge())
                }
                else if(!isOn){
                    var mmap: HashMap<String, Any?> = HashMap()
                    mmap.put("OptionMessage","Off")
                    noterefs.set(mmap, SetOptions.merge())
                }
            }
        })
    }

    fun sendMsg(text:String){

        var db = FirebaseFirestore.getInstance()
        db.collection("Devices").document(serial).update("Messages",text)

        Thread.sleep(1000)

        db.collection("Devices").document(serial).update("Messages","")
    }
}