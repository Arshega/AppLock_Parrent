package aldwin.tablante.com.appblock.Notifiers

import aldwin.tablante.com.appblock.Account.Model.ChildMessage
import aldwin.tablante.com.appblock.MessageReceiver
import aldwin.tablante.com.appblock.Notifiers.Modules.GetCurrentLocation
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot

class GpsService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        try {
            var id = intent!!.extras.getString("id")
            GetCurrentLocation().requestLocationUpdates(applicationContext,id)

        } catch (e: Exception) {
            Toast.makeText(applicationContext,"ERROR GPS SERVICE",Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
        return START_STICKY
    }

}