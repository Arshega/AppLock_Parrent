package aldwin.tablante.com.appblock.CaptureImages

import aldwin.tablante.com.appblock.R
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.StorageTask
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.capture_image.*
import java.io.File
import java.io.InputStream
import java.net.URL

class CaptureImage : AppCompatActivity() {
    var id = ""
    var serial = ""
    var img = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.capture_image)
        imageView7.visibility = View.INVISIBLE
        imageView12.visibility = View.INVISIBLE
        id = intent.getStringExtra("id")
        serial = intent.getStringExtra("serial")




        captureNow.setOnClickListener {
            imageView7.visibility = View.INVISIBLE
            imageView12.visibility = View.VISIBLE
            imageView12.setBackgroundResource(R.drawable.loading)
            captureNow.isEnabled = false
            sendRequest(id, serial)
        }


    }


    fun sendRequest(id: String, serial: String) {
        var anim = imageView12.background as AnimationDrawable
        anim!!.start()
        //refresh()
        var fbase = FirebaseFirestore.getInstance()
        var rbase = fbase.collection("Devices").document(serial).collection("Images").document("FrontCamera")
        var mmap: HashMap<String, Any?> = HashMap()


        var tbase = fbase.collection("Devices").document(serial).update("CaptureCam", true)

     rbase.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->

         if(documentSnapshot.exists()){

             documentSnapshot.get("url")
             var imageurl = documentSnapshot.get("url").toString()
             imageView7.visibility = View.VISIBLE
             imageView12.visibility = View.INVISIBLE
             anim.stop()
             Picasso.with(applicationContext).load(imageurl).fit().into(imageView7)
         }

         else{
             val alert: AlertDialog.Builder = AlertDialog.Builder(this@CaptureImage)
             alert.setTitle("Request picture Error:")
             alert.setMessage("No Available Image")
             alert.setNegativeButton("OK", DialogInterface.OnClickListener { dialog, whichButton ->
                 dialog.dismiss()

             })
             alert.show()

         }

     }




    }


    fun refresh() {
    try {
        var c = alertD()
        c.execute()
    }catch (e:InterruptedException){
        e.printStackTrace()
    }


    }

    inner class alertD : AsyncTask<Void,Void,Void>(){


        override fun doInBackground(vararg p0: Void?): Void? {
            try {
                Thread.sleep(15000)
                publishProgress()
            }catch (e:InterruptedException){

                e.printStackTrace()
            }
            return null
        }

        override fun onProgressUpdate(vararg values: Void?) {
            super.onProgressUpdate(*values)
            var alert : AlertDialog.Builder = AlertDialog.Builder(this@CaptureImage)
            alert.setTitle("Retry?")
            alert.setMessage("Slow Connection or Child Phone is down...")
            alert.setIcon(R.drawable.applock)
            alert.setNegativeButton("Wait", DialogInterface.OnClickListener { dialogInterface, i ->

                refresh()
              null

            })


        alert.setPositiveButton("Try Later",DialogInterface.OnClickListener{dialogInterface, i ->

            onBackPressed()

        })

            alert.create()
            alert.show()
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
