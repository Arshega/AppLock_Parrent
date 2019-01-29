package aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.FireBase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class GetRunningApps {

    fun fetch(id: String, serial: String): ArrayList<String> {
        var runningApps: ArrayList<String> = ArrayList()
        val database = FirebaseDatabase.getInstance()
        var dataref = database.getReference("Accounts").child(id).child("Devices").child(serial)

        dataref.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {

                for (h in p0!!.children) {
                    var app = h.value.toString()
                    runningApps!!.add(app)


                }

            }


        })

        return runningApps
    }

}