package aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.FireBase


import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class DeleteFromFireBase() {


    var firestore = FirebaseFirestore.getInstance()
    var restore = firestore.collection("Accounts")
    var chstore = firestore.collection("Devices")

    fun DeleteThis(userId:String,serial:String){



        restore.document(userId).collection("Devices").document(serial).delete().addOnSuccessListener {

        }

        chstore.document(serial).collection("Parents").document(userId).delete()
    }
}