package aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.FragmentHolder

import aldwin.tablante.com.appblock.Account.Introduction.Main.Activity.MainActivity
import aldwin.tablante.com.appblock.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.*

//Fragment For Account
class Fragment_Intro: Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_home, container, false)
        return view
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    /*    name.setText(this.arguments.get("name").toString())
        no.setText(this.arguments.get("noSerial").toString())
        serialy.setText(this.arguments.get("no").toString())
        email.setText(this.arguments.get("email").toString())
*/
        name.setText(this.arguments.get("name").toString())
        serialy.setText(this.arguments.get("email").toString())
        var fBase= FirebaseDatabase.getInstance()
        var rBase = fBase.getReference("Accounts")
        home1.setText(this.arguments.get("no").toString())

        /*rBase.child(this.arguments.getString("value")).child("Devices")
                .addValueEventListener(object:ValueEventListener{
                    override fun onCancelled(p0: DatabaseError?) {

                         null
                    }

                    override fun onDataChange(p0: DataSnapshot?) {
                        try{
                            if(p0!!.exists()){

                               numdev.setText( p0!!.childrenCount.toString())


                            }
                            else {

                                numdev.setText("0")

                            }
                        }
                        catch (e:Exception){
                            e.printStackTrace()
                          //  Toast.makeText(activity.applicationContext,"Error Fragment_Intro",Toast.LENGTH_LONG).show()
                        }
                    }
        })*/

       floatingActionButton.setOnClickListener {


            var intent = Intent(activity.applicationContext, MainActivity::class.java)
            startActivity(intent)


        }


    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }
}