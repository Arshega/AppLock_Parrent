package aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.CommandPanel

import aldwin.tablante.com.appblock.*
import aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.FireBase.DeleteFromFireBase
import aldwin.tablante.com.appblock.Account.Model.ChildMessage
import aldwin.tablante.com.appblock.CaptureImages.CaptureImage
import aldwin.tablante.com.appblock.Histories.Search_activity
import aldwin.tablante.com.appblock.R
import aldwin.tablante.com.appblock.ScreenShot.ScreenShot
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.command2.*
import java.util.HashMap
import com.github.angads25.toggle.interfaces.OnToggledListener
import com.github.angads25.toggle.model.ToggleableView
import com.github.angads25.toggle.widget.LabeledSwitch


class Command_Activity : AppCompatActivity() {
    var id = ""
    var serial = ""
    var parentName = ""
    var parentNo = ""
    var parentEmail = ""
    var parentSerialN = ""

    lateinit var listView: ListView
    lateinit var alertDialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
        setContentView(R.layout.command2)


        id = intent.getStringExtra("idpar")
        serial = intent.getStringExtra("serialval")
        parentEmail = intent.getStringExtra("parentEmail")
        parentNo = intent.getStringExtra("parentNo")
        parentSerialN = intent.getStringExtra("parentSerialNo")
        parentName = intent.getStringExtra("parentName")


        // View running Apps
        runningapp.setOnClickListener {
            var builder = AlertDialog.Builder(this@Command_Activity)
            var inflater = layoutInflater
            var dialogView = inflater.inflate(R.layout.running_usage_app, null)
            builder.setIcon(R.drawable.power)
            builder.setTitle("Applications Info")
            builder.setMessage("Please Select from the Following!")
            builder.setView(dialogView)

            var btnRunning = dialogView.findViewById<Button>(R.id.btnRunning)
            var btnUsage = dialogView.findViewById<Button>(R.id.btnUsage)

            btnRunning.setOnClickListener {
                var db = FirebaseFirestore.getInstance()
                db.collection("Devices").document(serial).update("AppPermit", true)
                var intent = Intent(applicationContext, RunningAppList::class.java)
                intent.putExtra("id", id)
                intent.putExtra("serial", serial)
                startActivity(intent)
            }

            btnUsage.setOnClickListener {
                var db = FirebaseFirestore.getInstance()
                db.collection("Devices").document(serial).update("AppPermit", true)
                var intent = Intent(applicationContext, ApplicationUsage::class.java)
                intent.putExtra("id", id)
                intent.putExtra("serial", serial)
                startActivity(intent)
            }
            alertDialog = builder.create()
            alertDialog.show()
        }
        //UnpairDevice
        unpair.setOnClickListener {
            var mmaps: HashMap<String, Any?> = HashMap()
            var firestore = FirebaseFirestore.getInstance()
            var noterefs = firestore.collection("Devices").document(serial)
            //var noterefs = firestore.collection("Devices").document(serial)
            var alertDialog = AlertDialog.Builder(this@Command_Activity)
                    .setTitle("Unpair this Device?")
                    .setIcon(R.drawable.disconnect)
                    .setMessage("Unpair " + serial)
                    .setPositiveButton("Ok", DialogInterface.OnClickListener { dialogInterface, i ->
                        mmaps.put("Paired", "No")
                        noterefs.set(mmaps, SetOptions.merge())

                        DeleteFromFireBase().DeleteThis(id, serial)
                        Toast.makeText(applicationContext, "Successfully Unpaired", Toast.LENGTH_SHORT).show()

                        var intent = Intent(applicationContext, Parent_View::class.java)
                        intent.putExtra("id", id)
                        intent.putExtra("name", parentName)
                        intent.putExtra("noSerial",parentSerialN)
                        intent.putExtra("no",parentNo)
                        intent.putExtra("email", parentEmail)

                        startActivity(intent)

                    })
                    .setNegativeButton("Cancel", null)

                    .create();

            alertDialog.show();

        }

        // Lock Phone with Timer
        phoneLock.setOnClickListener {
            var builder = AlertDialog.Builder(this@Command_Activity)
            var inflater = layoutInflater

            var dialogView = inflater.inflate(R.layout.set_schedule, null)
            builder.setIcon(R.drawable.locking)
            builder.setTitle("Lock Device")
            builder.setMessage("Please Select from the Following!")
            builder.setView(dialogView)
            var btnSchedule = dialogView.findViewById<Button>(R.id.btnSetSchedule)
            var btnSetTime = dialogView.findViewById<Button>(R.id.btnSetTime)
            var btnLockDevice = dialogView.findViewById<Button>(R.id.btnLockNow)
            btnSchedule.setOnClickListener {
                var intent = Intent(applicationContext, SetSchedule::class.java)
                intent.putExtra("id", id)
                intent.putExtra("serial", serial)
                startActivity(intent)
            }
            btnSetTime.setOnClickListener {
                var item: Array<CharSequence> = arrayOf("1 Minute", "5 Minutes", "10 Minutes", "20 Minutes", "30 Minutes")
                var builder = AlertDialog.Builder(this)
                builder.setTitle("Select a Time to Lock the device")
                builder.setIcon(R.drawable.hourglass)
                builder.setSingleChoiceItems(item, -1, object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        var timeFire = FirebaseFirestore.getInstance()
                        var noted = timeFire.collection("Devices").document(serial)
                        var mmap: HashMap<String, Any?> = HashMap()
                        if (which == 0) {
                            mmap.clear()
                            mmap.put("LockScreen", "one")
                            noted.set(mmap, SetOptions.merge()).addOnCompleteListener {
                                mmap.clear()
                            }
                        }
                        if (which == 1) {
                            mmap.clear()
                            mmap.put("LockScreen", "five")
                            noted.set(mmap, SetOptions.merge()).addOnCompleteListener {
                                mmap.clear()
                            }
                        }
                        if (which == 2) {
                            mmap.clear()
                            mmap.put("LockScreen", "ten")
                            noted.set(mmap, SetOptions.merge()).addOnCompleteListener {
                                mmap.clear()
                            }
                        }
                        if (which == 3) {
                            mmap.clear()
                            mmap.put("LockScreen", "twenty")
                            noted.set(mmap, SetOptions.merge()).addOnCompleteListener {
                                mmap.clear()
                            }
                        }
                        if (which == 4) {
                            mmap.clear()
                            mmap.put("LockScreen", "thirty")
                            noted.set(mmap, SetOptions.merge()).addOnCompleteListener {
                                mmap.clear()
                            }
                        }
                        alertDialog.dismiss()
                    }
                })
                alertDialog.dismiss()
                alertDialog = builder.create()
                alertDialog.show()
            }
            btnLockDevice.setOnClickListener {
                var builder = AlertDialog.Builder(this@Command_Activity)
                var inflater = layoutInflater
                var dialogView = inflater.inflate(R.layout.activity_toggle, null)
                builder.setIcon(R.drawable.calens)
                builder.setTitle("Lock Screen")
                builder.setMessage("Enable Toggle button to Lock Child Device")
                builder.setView(dialogView)
                var labeledSwitch = dialogView.findViewById<LabeledSwitch>(R.id.swicher2)
                var firestore = FirebaseFirestore.getInstance()
                var noterefs = firestore.collection("Devices").document(serial)
                var db = FirebaseFirestore.getInstance()
                db.collection("Devices")
                        .whereEqualTo("Serial", serial)
                        .addSnapshotListener(object : EventListener<QuerySnapshot> {
                            override fun onEvent(p0: QuerySnapshot?, p1: FirebaseFirestoreException?) {
                                for (doc in p0!!.documents) {
                                    var option: LockDevice = doc.toObject(LockDevice::class.java)
                                    var options = option.LockDevice
                                    if (options == "On"){
                                        labeledSwitch.isOn = true
                                    }else if (options == "Off"){
                                        labeledSwitch.isOn = false
                                    }
                                }
                            }
                        })
                labeledSwitch.setOnToggledListener(object : OnToggledListener {
                    override fun onSwitched(toggleableView: ToggleableView?, isOn: Boolean) {
                        if (isOn){
                            var mmap: HashMap<String, Any?> = HashMap()
                            mmap.put("LockDevice","On")
                            noterefs.set(mmap, SetOptions.merge())
                        }
                        else if(!isOn){
                            var mmap: HashMap<String, Any?> = HashMap()
                            mmap.put("LockDevice","Off")
                            noterefs.set(mmap, SetOptions.merge())
                        }
                    }
                })
                builder.setPositiveButton("OKAY", null)
                alertDialog = builder.create()
                alertDialog.show()
            }
            alertDialog = builder.create()
            alertDialog.show()
        }

        //History Activity
        yHistory.setOnClickListener {
                var intent = Intent(applicationContext, Search_activity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("serial", serial)
            startActivity(intent)

        }
        // Screenshot
        captureScreen.setOnClickListener {

            var intent = Intent(applicationContext, ScreenShot::class.java)
            intent.putExtra("id", id)
            intent.putExtra("serial", serial)
            startActivity(intent)

        }
        // Map Activity
        gps.setOnClickListener {
            var intent = Intent(applicationContext, MapsActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("serial", serial)
            startActivity(intent)

        }
        // Alarm Activity
        alarm.setOnClickListener {

            /*  var db = FirebaseFirestore.getInstance()
              db.collection("Devices").document(serial).update("TriggerAlarm", true)
              Thread.sleep(2000)
              db.collection("Devices").document(serial).update("TriggerAlarm", false)*/
            var intent = Intent(applicationContext, TimerSet::class.java)
            intent.putExtra("id", id)
            intent.putExtra("serial", serial)
            startActivity(intent)
        }

        // Lock Activity
        lock.setOnClickListener {
            var intent = Intent(applicationContext, ManageApps::class.java)
            intent.putExtra("id", id)
            intent.putExtra("serial", serial)
            startActivity(intent)

        }

        // Capture Image Activity
        captureImage.setOnClickListener {
            var intent = Intent(applicationContext, CaptureImage::class.java)
            intent.putExtra("id", id)
            intent.putExtra("serial", serial)
            startActivity(intent)
        }

        // Alert Messaging Activity
        msg.setOnClickListener {
            var intent = Intent(applicationContext, MessagingActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("serial", serial)
            startActivity(intent)
        }

        ViewMessages.setOnClickListener {
            var db = FirebaseFirestore.getInstance()
            var msg = arrayListOf<String>()
            db.collection("Devices")
                    .whereEqualTo("Serial", serial)
                    .addSnapshotListener(object : EventListener<QuerySnapshot> {
                        override fun onEvent(p0: QuerySnapshot?, p1: FirebaseFirestoreException?) {
                            for (doc in p0!!.documents) {
                                var option: ChildMessage = doc.toObject(ChildMessage::class.java)
                                var options = option.ChildMessage
                                for(i in 0 until options.size){
                                    msg.add(options[i])
                                }
                            }
                        }
                    })
            listView = ListView(this)
            var adapter = ArrayAdapter<String>(this, R.layout.list_item, R.id.txtMsg, msg)
            listView.adapter = adapter
            listView.onItemClickListener = object : AdapterView.OnItemClickListener{
                override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                }
            }
            var builder = AlertDialog.Builder(this)
            builder.setCancelable(true)
            builder.setTitle("Messages")
            builder.setIcon(R.drawable.mess)
            builder.setPositiveButton("OKAY", null)
            builder.setView(listView)
            var dialog = builder.create()
            dialog.show()
        }

        /* otherpar.setOnClickListener {

             var intent = Intent(applicationContext, OtherParentActivity::class.java)
             intent.putExtra("id", id)
             intent.putExtra("serial", serial)
             startActivity(intent)


         }*/

        // Unpairing Device Activity
        /*   unpair.setOnClickListener {

               var alertDialog = AlertDialog.Builder(this@Command_Activity)
                       .setTitle("Unpair this Device?")
                       .setMessage("Unpair " + serial)
                       .setPositiveButton("Ok", DialogInterface.OnClickListener { dialogInterface, i ->
                           DeleteFromFireBase().DeleteThis(id, serial)
                           Toast.makeText(applicationContext, "Successfully Unpaired", Toast.LENGTH_SHORT).show()

                       })
                       .setNegativeButton("Cancel", null)

                       .create();

               alertDialog.show();


           }
          // View Running Apps Activity
           viewrunning.setOnClickListener {
               var db = FirebaseFirestore.getInstance()
               db.collection("Devices").document(serial).update("AppPermit", true)
               var intent = Intent(applicationContext, RunningAppList::class.java)
               intent.putExtra("id", id)
               intent.putExtra("serial",serial)
               startActivity(intent)

           }*/


    }

    override fun onBackPressed() {
        super.onBackPressed()

    }

    fun showDialog(view: View){

    }

}