package aldwin.tablante.com.appblock

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast


class Service_Notifier : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        Toast.makeText(applicationContext, "Bind", Toast.LENGTH_SHORT).show()
        return null
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        return START_STICKY
    }
}