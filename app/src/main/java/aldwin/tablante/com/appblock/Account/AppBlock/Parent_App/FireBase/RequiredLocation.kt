package aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.FireBase

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.FirebaseDatabase

class RequiredLocation {
    private var distanceInMeters = 0.0
    companion object {
        var dis = 0.0
    }

    fun requestLocationUpdates(context: Context,id:String):Double {
        val request = LocationRequest()
        request.setNumUpdates(2)
        request.setExpirationDuration(2000)

        request.numUpdates = 2
        request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val client = LocationServices.getFusedLocationProviderClient(context.applicationContext)
        val permission = ContextCompat.checkSelfPermission(context.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION)


        if (permission == PackageManager.PERMISSION_GRANTED) {

//
            client.requestLocationUpdates(request, object : LocationCallback() {

                override fun onLocationResult(locationResult: LocationResult?) {

                    val location = locationResult!!.lastLocation
                    if (location != null) {
                        var loc1: Location = Location("")
                        loc1.latitude = location.latitude
                        loc1.longitude = location.longitude
                      var hashMap : HashMap<String,Any? > = HashMap()
                        hashMap.put("Lat",loc1.latitude)
                        hashMap.put("Long",loc1.longitude)
                        var db = FirebaseDatabase.getInstance()
                        var dbref = db.getReference("Accounts")
                        dbref.child(id).updateChildren(hashMap)



                        Toast.makeText(context.applicationContext,distanceInMeters.toString(),Toast.LENGTH_SHORT).show()



                    }


                }

            },null)



        }


        return distanceInMeters
    }
}

