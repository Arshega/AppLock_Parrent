package aldwin.tablante.com.appblock.Notifiers.Modules

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class GetCurrentLocation{


    fun requestLocationUpdates(context: Context, id: String) {
        val request = LocationRequest()
        request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        var client = LocationServices.getFusedLocationProviderClient(context)
        var dataFstore = FirebaseFirestore.getInstance()
        var refStore = dataFstore.collection("Accounts").document(id)
        val permission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
        if (permission == PackageManager.PERMISSION_GRANTED) {

            request.setFastestInterval(5000)
                    .setInterval(10000)
                    .setExpirationDuration(10000)


            client.requestLocationUpdates(request, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    val location = locationResult!!.lastLocation

                    if (location != null) {
                        var mmap : HashMap<String,Any?> = HashMap()
                        mmap.put("longitude",location.longitude)
                        mmap.put("latitude",location.latitude)
                        mmap.put("altitude",location.altitude)
                        mmap.put("bearing",location.bearing)
                        mmap.put("time",location.time)
                        mmap.put("accuracy",location.accuracy)
                        mmap.put("provider",location.provider)
                        mmap.put("speed",location.speed)


                        refStore.collection("Locations").document("Current").set(mmap)

                        /* if(bool) {

                             dataref.child("longitude").setValue(location.longitude)

                         }

                         dataref.updateChildren(mmap)*/

                    }
                    else{
                        Toast.makeText(context.applicationContext,"null location",Toast.LENGTH_LONG).show()
                    }


                }
            }

                    , null)
        } else {
            Toast.makeText(context, " Not Found", Toast.LENGTH_LONG).show()

        }



    }


    }