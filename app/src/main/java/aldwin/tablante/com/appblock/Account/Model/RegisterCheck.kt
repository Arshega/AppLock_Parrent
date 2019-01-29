package aldwin.tablante.com.appblock.Account.Model


import aldwin.tablante.com.appblock.Account.Module.LoggingIn
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by Bobby on 03/05/2018.
 */
class RegisterCheck {

    private lateinit var database: FirebaseDatabase
    private lateinit var dataref: DatabaseReference


    // Creating a New Account
    fun registerNewAccount(a: User,context: Context) {
        var firestore = FirebaseFirestore.getInstance()
        var rbase = firestore
        var id =  rbase.collection("Accounts").id



      //  this.database = FirebaseDatabase.getInstance()
        //this.dataref = this.database.getReference("Accounts")


        val acc = User(a.accID, a.username.toLowerCase(),
                a.password.toLowerCase(),
                a.email.toLowerCase(), a.codd.toLowerCase(), a.Firstname, a.Lastname)

        rbase.collection("Accounts").whereEqualTo("username",acc.username).whereEqualTo("password",acc.password).get().addOnCompleteListener{
            v ->
            if(v.result.isEmpty){
            rbase.collection("Accounts").document(acc.accID).set(acc).addOnSuccessListener {
                Toast.makeText(context.applicationContext,"Account success",Toast.LENGTH_LONG).show()

                var intent = Intent(context.applicationContext,LoggingIn::class.java)
                context.startActivity(intent)
            }

            }else{

                Toast.makeText(context.applicationContext,"Account already Exists",Toast.LENGTH_LONG).show()
            }

        }


     //   rbase.collection("Accounts").document(a.accID)
     //   dataref.child(a.accID).setValue(acc)


        var mmap: HashMap<String, Any?> = HashMap()
        mmap.put("ParentID", a.accID)
        var db = FirebaseFirestore.getInstance().collection("Parent")
        db.add(mmap)

    }




}