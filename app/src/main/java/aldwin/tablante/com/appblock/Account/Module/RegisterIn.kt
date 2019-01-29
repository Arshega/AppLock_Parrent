package aldwin.tablante.com.appblock.Account.Module

import aldwin.tablante.com.appblock.Account.Fetcher.MyDeviceFetch
import aldwin.tablante.com.appblock.Account.Model.User
import aldwin.tablante.com.appblock.Account.Model.GenerateID
import aldwin.tablante.com.appblock.Account.Model.RegisterCheck
import aldwin.tablante.com.appblock.R
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.register_acc.*

/**
 * Created by Bobby on 03/05/2018.
 */
 class RegisterIn : AppCompatActivity() {

    private var user: EditText? = null
    private var pass: EditText? = null
    private var conpass: EditText? = null
    private var reg: Button? = null
    private var email: EditText? = null
    private var code: EditText? = null
    private var firstname: EditText? = null
    private var lastname: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_acc)


        this.reg = findViewById(R.id.register)
        login.setOnClickListener {
            val intent = Intent(this@RegisterIn, aldwin.tablante.com.appblock.Account.Module.LoggingIn::class.java)
            startActivity(intent)
        }

        this.reg!!.setOnClickListener {


            this.firstname = findViewById(R.id.name)
            this.lastname = findViewById(R.id.lname)
            this.user = findViewById(R.id.username)
            this.pass = findViewById(R.id.password)
            this.conpass = findViewById(R.id.confirmpassword)
            this.email = findViewById(R.id.email)
            this.code = findViewById(R.id.code)
            if (isvalidAcc() && isSame()) {

                if (isvalidPass()) {
                    var newacc = User(GenerateID().getNewId(), this.user!!.text.toString(),
                            this.pass!!.text.toString(),
                            this.email!!.text.toString(),
                            this.code!!.text.toString(), this.firstname!!.text.toString(),
                            this.lastname!!.text.toString()
                    )

                    RegisterCheck().registerNewAccount(newacc,applicationContext)


                }

            }


        }

        login.setOnClickListener {

            val intent = Intent(this@RegisterIn, aldwin.tablante.com.appblock.Account.Module.LoggingIn::class.java)
            startActivity(intent)

        }

    }


    fun isvalidAcc(): Boolean {

        var bool = false

        if (this.user!!.text.toString() != "" &&
                this.pass!!.text.toString() != "" &&
                this.conpass!!.text.toString() != "" &&
                this.email!!.text.toString() != "" &&
                this.firstname!!.text.toString() != "" &&
                this.lastname!!.text.toString() != ""


        ) {
            bool = true
        } else {

            this.user!!.error = "Incomplete Data.."
            this.pass!!.error = "Incomplete Data.."
            this.conpass!!.error = "Incomplete Data.."
            this.email!!.error = "Incomplete Data.."
        }

        if (this.user!!.text.toString().length >= 6 &&
                this.pass!!.text.toString().length >= 6 &&
                this.conpass!!.text.toString().length >= 6 &&
                this.email!!.text.toString().length >= 10) {
            bool = true
        } else {

            this.user!!.error = "Incomplete Data.."
            this.pass!!.error = "Incomplete Data.."
            this.conpass!!.error = "Incomplete Data.."
            this.email!!.error = "Incomplete Data.."
        }
        return bool
    }

    fun isvalidPass(): Boolean {
        var bool = false
        if (this.pass!!.text.toString().toLowerCase()
                == this.conpass!!.text.toString().toLowerCase()) {

            bool = true
        } else {

            this.conpass!!.error = "Mismatched Password"
        }


        return bool
    }


    fun isSame(): Boolean {
        var bool = true

        if (this.user!!.text.toString() == this.pass!!.text.toString()) {

            this.user!!.error = "Password Cant be same with Username"
            this.pass!!.error = "Password Cant be same with Username"
            bool = false
        }
        return bool
    }


}