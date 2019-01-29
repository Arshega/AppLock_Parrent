package aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.FireBase

import aldwin.tablante.com.appblock.Account.AppBlock.Model.MyDevices
import com.google.firebase.database.FirebaseDatabase

class UpdateFromFireBase (){
    private val database = FirebaseDatabase.getInstance()
    private val dataref = database.getReference("Accounts")
    private val datarefdev = database.getReference("Devices")

    fun UpdateThis(userId:String,serial:String,name:String){
   var mmap:HashMap<String,Any?> = HashMap()
           mmap.put("name",name)

        dataref.child(userId).child("Devices").child(serial).updateChildren(mmap)

    }
}