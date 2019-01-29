package aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.FragmentHolder

import aldwin.tablante.com.appblock.Account.AppBlock.Model.OtherDevice
import aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.AdapterHolder.OtherDeviceAdapter
import aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.FireBase.RequiredLocation
import aldwin.tablante.com.appblock.Notifiers.GpsService
import aldwin.tablante.com.appblock.R
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.design.internal.NavigationMenu
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.*
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import io.github.yavski.fabspeeddial.FabSpeedDial
import kotlinx.android.synthetic.main.fragment_otherdevices.*
import kotlinx.android.synthetic.main.list_otherdevices.*


// Nearby Devicess...
class Fragment_Devices : android.support.v4.app.Fragment() {
    var adapter: OtherDeviceAdapter? = null
    var data = FirebaseDatabase.getInstance()
    var dataref: DatabaseReference = data.getReference("Devices")
    var id = ""
    var name = ""
    var arrofDevices: ArrayList<OtherDevice> = ArrayList()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        id = this.arguments.getString("value")
        name = this.arguments.getString("name")
        textVnearby.visibility = View.INVISIBLE
        imageView9.visibility= View.INVISIBLE


        try {
            var intent = Intent(context.applicationContext, GpsService::class.java)
            intent.putExtra("id", id)
            context.startService(intent)
            devicesdb()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_otherdevices, container, false)
        return view
    }


    fun devicesdb() {
        var firststore = FirebaseFirestore.getInstance()
        var fst = firststore.collection("Devices")
        var fStore = FirebaseFirestore.getInstance()
        var rBase = fStore.collection("Accounts").document(id).collection("Locations")
        var f2Store = FirebaseFirestore.getInstance()
        var r2Base = f2Store.collection("Devices")
        arrofDevices!!.clear()
        var dat = dataref

     fst.get().addOnSuccessListener { querySnapshot ->
         if(!querySnapshot.isEmpty){
             for (snap in querySnapshot){



                     var otherdev = snap.toObject(OtherDevice::class.java)
                     otherdev!!.myId = id
                     otherdev!!.myName = name


                     try {

                         /*   for (h in p0!!.children) {
                        var otherdev = h.getValue(OtherDevice::class.java)
                        otherdev!!.myId = id
                        otherdev!!.myName = name*/
                         var lat = 0.0
                         var long = 0.0

                         fst.document(otherdev.Serial).collection("Parents").whereEqualTo("ParentID",id).get().addOnSuccessListener { parent ->


                             if (parent.isEmpty) {

                                 ////////////////////////////////////////////////////////////////////////////////
                                 r2Base.document(otherdev.Serial).collection("Locations").document("Current")
                                         .get().addOnSuccessListener { documentSnapshot ->
                                             if (documentSnapshot.exists()) {
                                                 otherdev.longitude = documentSnapshot.get("longitude").toString().toDouble()
                                                 otherdev.latitude = documentSnapshot.get("latitude").toString().toDouble()
                                                 rBase.document("Current").get().addOnSuccessListener { v ->
                                                     try {
                                                         if (v.exists()) {
                                                             lat = v.getDouble("latitude")
                                                             long = v.getDouble("longitude")


                                                         }
                                                         //////////////////////////
                                                         var latLng1 = LatLng(lat, long)
                                                         var latLng2 = LatLng(otherdev.latitude, otherdev.longitude)
                                                         var locationA = Location("My Loc")
                                                         locationA.latitude = latLng1.latitude
                                                         locationA.longitude = latLng1.longitude
                                                         var locationB = Location("Child Loc")
                                                         locationB.latitude = latLng2.latitude
                                                         locationB.longitude = latLng2.longitude
                                                         //convertion to Feet
                                                         var result = locationA.distanceTo(locationB) * 3.281
                                                         //If less than  32 Feet It will Display
                                                         if (result <= 32000000000) {
                                                             otherdev.longitude = result

                                                             arrofDevices!!.add(otherdev!!)


                                                             // Recursive method  -- If Exception Caught --
                                                             try {
                                                                 adapter = OtherDeviceAdapter(arrofDevices, activity.applicationContext)
                                                                 var layout_manager = LinearLayoutManager(activity.applicationContext)
                                                                 if (otherdevice != null) {
                                                                     otherdevice.layoutManager = layout_manager
                                                                     otherdevice.setHasFixedSize(true)
                                                                     otherdevice.adapter = adapter
                                                                     otherdevice.adapter!!.notifyDataSetChanged()
                                                                 }
                                                             } catch (e: IllegalStateException) {
                                                                 e.printStackTrace()
                                                                 Toast.makeText(context, "ERRO 1012", Toast.LENGTH_LONG).show()

                                                                 devicesdb()
                                                             }
                                                         } else {


                                                             try {
                                                                 if (otherdevice.adapter.itemCount != 0) {
                                                                     Toast.makeText(context,otherdevice.adapter.itemCount.toString(),Toast.LENGTH_LONG).show()
                                                                     textVnearby.visibility = View.VISIBLE
                                                                     imageView9.visibility = View.VISIBLE
                                                                 } else {
                                                                     textVnearby.visibility = View.INVISIBLE
                                                                     imageView9.visibility = View.INVISIBLE

                                                                 }
                                                             } catch (e: IllegalStateException) {
                                                                 e.printStackTrace()
                                                             }
                                                         }
                                                     } catch (e: NullPointerException) {

                                                         e.printStackTrace()
                                                     }
                                                 }

                                             }


                                             else{
                                                 try {
                                                     if (otherdevice.adapter.itemCount != 0) {
                                                         Toast.makeText(context,otherdevice.adapter.itemCount.toString(),Toast.LENGTH_LONG).show()
                                                         textVnearby.visibility = View.VISIBLE
                                                         imageView9.visibility = View.VISIBLE
                                                     } else {
                                                         textVnearby.visibility = View.INVISIBLE
                                                         imageView9.visibility = View.INVISIBLE

                                                     }
                                                 } catch (e: IllegalStateException) {
                                                     e.printStackTrace()
                                                 }

                                             }
                                         }
                                 ////////////////////////////////////////////////////////////////////////////////

                                 // }


                             }
                         }



                     } catch (e: Exception) {
                         e.printStackTrace()
                     }







             }

         }

     }



    }


}