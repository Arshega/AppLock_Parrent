package aldwin.tablante.com.appblock

import aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.AdapterHolder.DeviceAdapter
import aldwin.tablante.com.appblock.Account.Fetcher.MyDeviceFetch
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.appblock_intro.*

class OtherParentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.appblock_intro)
        var id: String = ""
        var text = ""
        var serial: String = ""

        id = intent.getStringExtra("id")
        serial = intent.getStringExtra("serial")


        if (!id.equals("")) {

            var data = FirebaseDatabase.getInstance()
            var ref = data.getReference("Devices").child(serial)
            ref.addValueEventListener(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError?) {

                }

                override fun onDataChange(p0: DataSnapshot) {

                    Toast.makeText(applicationContext, "Fetching Data", Toast.LENGTH_SHORT).show()




                    for (h in p0.children) {
                        /*   var value = h.getValue(MyDevices::class.java)
                           value!!.parentId = id
                           acclist.add(value!!)*/

                        if (h.child(id).exists()) {

                            MyDeviceFetch().getParentDevices(id,serial,applicationContext, recycle)

                        }


                    }
                }
            })
        }
    }
}
