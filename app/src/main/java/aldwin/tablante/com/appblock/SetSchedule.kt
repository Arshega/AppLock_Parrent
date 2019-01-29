package aldwin.tablante.com.appblock

import android.app.TimePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ca.antonious.materialdaypicker.MaterialDayPicker
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_set_schedule.*
import java.util.*
import kotlin.collections.ArrayList

class SetSchedule : AppCompatActivity() {
    var id = ""
    var serial = ""
    var selectDays : ArrayList<String> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_schedule)
        val list: ArrayList<MaterialDayPicker.Weekday> = arrayListOf()
        val materialDayPicker = findViewById<MaterialDayPicker>(R.id.dayPicker)
        var days = arrayListOf<String>()
        var mmap: HashMap<String, Any?> = HashMap()
        id = intent.getStringExtra("id")
        serial = intent.getStringExtra("serial")
        var d = FirebaseFirestore.getInstance()
        var noteRef = d.collection("Devices").document(serial)
        var db = FirebaseFirestore.getInstance()
        db.collection("Devices")
                .whereEqualTo("Serial", serial)
                .addSnapshotListener(object : EventListener<QuerySnapshot> {
                    override fun onEvent(p0: QuerySnapshot?, p1: FirebaseFirestoreException?) {
                        for (doc in p0!!.documents) {
                            var settings = doc.toObject(SettingSchedules::class.java)
                            if(settings.Days.isNotEmpty()){
                                for(i in 0 until settings.Days.size){
                                    when {
                                        settings.Days[i]=="SUNDAY" -> list.add(MaterialDayPicker.Weekday.SUNDAY)
                                        settings.Days[i]=="MONDAY" -> list.add(MaterialDayPicker.Weekday.MONDAY)
                                        settings.Days[i]=="TUESDAY" -> list.add(MaterialDayPicker.Weekday.TUESDAY)
                                        settings.Days[i]=="WEDNESDAY" -> list.add(MaterialDayPicker.Weekday.WEDNESDAY)
                                        settings.Days[i]=="THURSDAY" -> list.add(MaterialDayPicker.Weekday.THURSDAY)
                                        settings.Days[i]=="FRIDAY" -> list.add(MaterialDayPicker.Weekday.FRIDAY)
                                        settings.Days[i]=="SATURDAY" -> list.add(MaterialDayPicker.Weekday.SATURDAY)
                                    }
                                }
                                materialDayPicker.setSelectedDays(list)
                            }
                            if(settings.From != ""){
                                txtFrom.text = settings.From
                            }
                            if (settings.To != ""){
                                txtTo.text = settings.To
                            }
                        }
                    }
                })



        btnFrom.setOnClickListener {
            showFromPicker()
        }
        btnTo.setOnClickListener {
            showToPicker()
        }



        materialDayPicker?.setDayPressedListener(object : MaterialDayPicker.DayPressedListener {
            override fun onDayPressed(weekday: MaterialDayPicker.Weekday, isSelected: Boolean) {

                val message = String.format(weekday.toString())
                days.add(message)
            }
        })

        btnDone.setOnClickListener {
            var from = txtFrom.text.toString()
            var to = txtTo.text.toString()
            val hashSet = HashSet<String>()
            hashSet.addAll(days)
            days.clear()
            days.addAll(hashSet)
            mmap.put("From", from)
            mmap.put("To", to)
            mmap.put("Days", days)
            noteRef.set(mmap, SetOptions.merge()).addOnCompleteListener {
                mmap.clear()
                finish()
            }
        }
    }

    fun showFromPicker() {
        val myCalender = Calendar.getInstance()
        val hour = myCalender.get(Calendar.HOUR_OF_DAY)
        val minute = myCalender.get(Calendar.MINUTE)

        val myTimeListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            if (view.isShown) {
                myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay)
                myCalender.set(Calendar.MINUTE, minute)
                txtFrom.text = "$hourOfDay:$minute"
            }
        }
        val timePickerDialog = TimePickerDialog(this@SetSchedule, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true)
        timePickerDialog.setTitle("Choose hour:")
        timePickerDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        timePickerDialog.show()
    }

    fun showToPicker() {
        val myCalender = Calendar.getInstance()
        val hour = myCalender.get(Calendar.HOUR_OF_DAY)
        val minute = myCalender.get(Calendar.MINUTE)

        val myTimeListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            if (view.isShown) {
                myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay)
                myCalender.set(Calendar.MINUTE, minute)
                txtTo.text = "$hourOfDay:$minute"
            }
        }
        val timePickerDialog = TimePickerDialog(this@SetSchedule, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true)
        timePickerDialog.setTitle("Choose hour:")
        timePickerDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        timePickerDialog.show()
    }
}
