package aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.AdapterHolder

import aldwin.tablante.com.appblock.Account.AppBlock.Model.OtherDevice
import aldwin.tablante.com.appblock.R

import aldwin.tablante.com.appblock.GpsLocations
import android.app.AlertDialog

import android.content.Context
import android.content.DialogInterface
import android.support.v7.widget.CardView

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager

import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

import com.google.firebase.database.*
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException


class OtherDeviceAdapter(list: ArrayList<OtherDevice>, context: Context) : RecyclerView.Adapter<OtherDeviceAdapter.OtherDeviceholder>() {
    var data: ArrayList<OtherDevice> = list
    var mContext = context
    var gps = GpsLocations()
    var value = ""
    var idser = ""
    var datalong = 0.0
    var datalat = 0.0
    var v: View? = null

    override fun onBindViewHolder(holder: OtherDeviceholder?, position: Int) {



        idser = data[position].Serial
        holder!!.Nameholder.text = data[position].Serial
        holder.MetersLoc.text =  String.format("%.2f",data[position].longitude) + " .ft away from you"



        holder.cView.setOnClickListener {
            val alert: AlertDialog.Builder = AlertDialog.Builder(v!!.rootView.context)
              alert.setIcon(R.drawable.applock)

              alert.setTitle("Pairing")
            alert.setMessage("Would you like to pair to" + " " + data[position].Serial + "? ")
            alert.setPositiveButton("Send Request", DialogInterface.OnClickListener { dialog, whichButton ->
                requestPairing(data[position].Serial, data[position].myName, data[position].myId)
                deleteItem(position)

            })
            alert.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, whichButton ->

                null

            })

                    alert.create()
            alert.show()
        }

        var id = data[position].Serial
        var data = FirebaseDatabase.getInstance()
        var ref = data.getReference("Devices").child(id).child("Locations")

        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(p0: DataSnapshot?) {
                if (p0!!.hasChild("longitude") && p0!!.hasChild("latitude")) {
                    datalong = p0!!.child("longitude").value!!.toString().toDouble()
                    datalat = p0!!.child("latitude").value!!.toString().toDouble()
                    // requestLocationUpdates(id,position)
                }

            }

            override fun onCancelled(p0: DatabaseError?) {
                Log.d("BISY", "BISY")
            }


        })


    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): OtherDeviceholder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.list_otherdevices, parent, false)
        v = view
        return OtherDeviceholder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class OtherDeviceholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //  var addButton: ImageView
        var cView:LinearLayout
        var Nameholder: TextView
        var MetersLoc: TextView


        init {
            cView = itemView.findViewById(R.id.cView)
            // addButton = itemView.findViewById(R.id.ibutton)
            Nameholder = itemView.findViewById(R.id.name)
            MetersLoc = itemView.findViewById(R.id.meter)

        }

    }



    private fun requestPairing(accID: String, Name: String, MyID: String) {
        var db = FirebaseFirestore.getInstance()

        var mmap: HashMap<String, Any?> = HashMap()
        mmap.put("ID", accID)
        mmap.put("Name", Name)
        mmap.put("RequestID", MyID)

        db.collection("Requests").document(MyID + "+" + accID).set(mmap)
                .addOnCompleteListener { Toast.makeText(mContext.applicationContext, "Requesting...", Toast.LENGTH_SHORT).show() }
                .addOnFailureListener { Toast.makeText(mContext.applicationContext, "Failed", Toast.LENGTH_SHORT).show() }
        mmap.clear()
        mmap.put("RequestedID", accID)
        db.collection("Parent").document(MyID).set(mmap)


    }


    fun deleteItem(pos: Int) {
        data.removeAt(pos)
        notifyItemRemoved(pos)

    }


}