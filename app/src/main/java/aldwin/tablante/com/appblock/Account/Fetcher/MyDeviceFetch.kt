package aldwin.tablante.com.appblock.Account.Fetcher

import aldwin.tablante.com.appblock.Account.AppBlock.Model.MyDevices
import aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.AdapterHolder.DeviceAdapter
import aldwin.tablante.com.appblock.Account.Model.User
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by Bobby on 03/05/2018.
 */
class MyDeviceFetch() {


    var device: ArrayList<MyDevices> = ArrayList()
    var adapter: DeviceAdapter? = null
    fun getAccounts(): ArrayList<User> {
        var acclist: ArrayList<User> = ArrayList()
        var data = FirebaseDatabase.getInstance()
        var ref = data.getReference("Accounts")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (h in p0.children) {
                    var value = h.getValue(User::class.java)


                    acclist.add(value!!)

                }
            }
        }
        )
        return acclist
    }
    fun getAccountDevices(name:String,no:String ,email:String,noSerial:String,id: String, context: Context, recycle: RecyclerView , v:ImageView
    ,t : TextView): ArrayList<MyDevices> {
        t.visibility = View.INVISIBLE
        v.visibility = View.INVISIBLE
        var acclist: ArrayList<MyDevices> = ArrayList()

        var firebaseFirestore = FirebaseFirestore.getInstance()
        var rbase = firebaseFirestore.collection("Accounts").document(id).collection("Devices")

        rbase.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            acclist.clear()
            if(!querySnapshot.isEmpty) {
                for (h in querySnapshot) {

                    var value = h.toObject(MyDevices::class.java)
                    value!!.parentId = id
                    value!!.parentemail = email
                    value!!.parentno = no
                    value!!.parentname = name
                    value!!.parentsno = noSerial
                    acclist.add(value!!)
                }

                if (acclist.isNotEmpty()) {

                    adapter = DeviceAdapter(acclist, context.applicationContext)
                    var layout_manager = LinearLayoutManager(context.applicationContext)

                    recycle.layoutManager = layout_manager
                    recycle.setHasFixedSize(true)
                    recycle.adapter = adapter

                    t.visibility = View.INVISIBLE
                    v.visibility = View.INVISIBLE
                } else {

                    t.visibility = View.VISIBLE
                    v.visibility = View.VISIBLE
                }
            }

            else{
                t.visibility = View.VISIBLE
                v.visibility = View.VISIBLE

            }
        }



      /*  var data = FirebaseDatabase.getInstance()
        var ref = data.getReference("Accounts").child(id).child("Devices")

        ref.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                acclist.clear()
                for (h in p0.children) {
                    var value = h.getValue(MyDevices::class.java)
                    value!!.parentId = id
                    value!!.parentemail = email
                    value!!.parentno = no
                    value!!.parentname = name
                    value!!.parentsno = noSerial
                    acclist.add(value!!)

                }
                if (acclist.isNotEmpty()) {


                    adapter = DeviceAdapter(acclist, context.applicationContext)
                    var layout_manager = LinearLayoutManager(context.applicationContext)

                    recycle.layoutManager = layout_manager
                    recycle.setHasFixedSize(true)
                    recycle.adapter = adapter

                    t.visibility = View.INVISIBLE
                    v.visibility = View.INVISIBLE

                }

                else{
                    t.visibility = View.VISIBLE
                    v.visibility = View.VISIBLE

                }


            }


        }

        )*/

        return acclist
    }
    fun getParentDevices(id: String,serial : String, context: Context, recycle: RecyclerView): ArrayList<MyDevices> {

        var acclist: ArrayList<MyDevices> = ArrayList()

        var data = FirebaseDatabase.getInstance()
        var ref = data.getReference("Accounts").child(id)


        ref.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                acclist.clear()
                Toast.makeText(context.applicationContext, "Fetching Data", Toast.LENGTH_SHORT).show()
                for (h in p0.children) {
                 /*   var value = h.getValue(MyDevices::class.java)
                    value!!.parentId = id
                    acclist.add(value!!)*/


                        var name  = h.child("firstname").value.toString() + h.child("lastname").value.toString()

                        var value = MyDevices()
                    value.NAME = name
                    value.ID = "Paired"
                    value.parentId =id


                }
                if (acclist.isNotEmpty()) {


                    adapter = DeviceAdapter(acclist, context.applicationContext)
                    var layout_manager = LinearLayoutManager(context.applicationContext)

                    recycle.layoutManager = layout_manager
                    recycle.setHasFixedSize(true)
                    recycle.adapter = adapter


                }


            }


        }

        )

        return acclist
    }


}