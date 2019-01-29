package aldwin.tablante.com.appblock.Account.Module


import aldwin.tablante.com.appblock.Account.Fetcher.MyDeviceFetch
import aldwin.tablante.com.appblock.Account.Model.User
import aldwin.tablante.com.appblock.Account.Model.LoginCheck
import aldwin.tablante.com.appblock.R
import aldwin.tablante.com.appblock.Parent_View
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.login_acc.*

/**
 * Created by Bobby on 03/05/2018.
 */
class LoggingIn : AppCompatActivity() {
    private var id: String = ""
    private var name = ""
    private var lastname = ""
    private var submit: Button? = null
    private var username: EditText? = null
    private var password: EditText? = null
    private var loginacc: User? = null
    private var number : String? = null
    private var numberSerial : String? = null
    private var email : String?= null
    private var acclist: ArrayList<User> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
            setContentView(R.layout.login_acc)

        var firebaseFirestore = FirebaseFirestore.getInstance()
        var rbase = firebaseFirestore.collection("Accounts")

       // this.acclist = MyDeviceFetch().getAccounts()
        username = findViewById(R.id.editText)
        password = findViewById(R.id.editText2)
        submit = findViewById(R.id.login)



        submit!!.setOnClickListener {


            if (LoginCheck().isFilled(this.username!!.text.toString(), this.password!!.text.toString())) {

              rbase.whereEqualTo("username",this.username!!.text.toString())
                      .whereEqualTo("password",this.password!!.text.toString())
                      .get()
                      .addOnSuccessListener { v ->

                          if(!v.isEmpty) {

                              Toast.makeText(applicationContext,"Log in Success",Toast.LENGTH_LONG).show()
                              var h = v.documents.get(0) as DocumentSnapshot
                              val intent = Intent(this, Parent_View::class.java)
                              intent.putExtra("no", h.get("codd").toString())
                              intent.putExtra("noSerial", h.get("deviceModel").toString())
                              intent.putExtra("id", h.get("accID").toString())
                              intent.putExtra("name", h.get("firstname").toString() + " " + h.get("lastname").toString())
                              intent.putExtra("email", h.get("email").toString())
                              startActivity(intent)
                          }
                          else{
                              Toast.makeText(applicationContext,"Incorrect Password and Username",Toast.LENGTH_LONG).show()

                          }
                      }





            } else {


                this.username!!.error = " Fill up the Required Data"
                this.password!!.error = " Fill up the Required Data"
            }

        }

        button2.setOnClickListener {
            val intent = Intent(this, RegisterIn::class.java)
            startActivity(intent)

        }


    }

    fun isExist(): Boolean {
        var bool = false
        var count = 0
        while (this.acclist.size > count) {

            if (this.username!!.text!!.toString() == this.acclist[count].username &&
                    this.password!!.text!!.toString() == this.acclist[count].password
            ) {

                this.id = this.acclist[count].accID
                this.name = this.acclist[count].Firstname
                this.lastname = this.acclist[count].Lastname
                this.number = this.acclist[count].codd
                this.numberSerial = this.acclist[count].DeviceModel
                this.email = this.acclist[count].email
                this.loginacc = this.acclist[count]

                bool = true
            }



            count++
        }

        return bool
    }


}