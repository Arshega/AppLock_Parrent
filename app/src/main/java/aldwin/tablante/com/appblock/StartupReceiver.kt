//package aldwin.tablante.com.appblock
//
//import android.app.AlarmManager
//import android.app.PendingIntent
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.os.SystemClock
//import android.util.Log
//
//class StartupReceiver : BroadcastReceiver() {
//
//    internal val startupID = 1111111
//
//
//    override fun onReceive(context: Context, intent: Intent) {
//
//        // Create AlarmManager from System Services
//        val alarmManager = context
//                .getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        try {
//            // Create pending intent for CheckRunningApplicationReceiver.class
//            // it will call after each 5 seconds
//
//            val i7 = Intent(context, MyBroadcastReceiver::class.java)
//            val ServiceManagementIntent = PendingIntent.getBroadcast(context,
//                    startupID, i7, 0)
//            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
//                    SystemClock.elapsedRealtime(),
//                    5000, ServiceManagementIntent)
//
//
//        } catch (e: Exception) {
//            Log.i(TAG, "Exception : $e")
//        }
//
//    }
//
//    companion object {
//
//        internal val TAG = "SR"
//    }
//
//}