package aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.AdapterHolder

import aldwin.tablante.com.appblock.Account.AppBlock.Model.MyDevices
import aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.CommandPanel.Command_Activity
import aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.FireBase.UpdateFromFireBase
import aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.FragmentHolder.Fragment_First
import aldwin.tablante.com.appblock.R
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.content.DialogInterface
import android.content.res.Configuration
import android.text.InputType
import android.widget.EditText



class DeviceAdapter(list: ArrayList<MyDevices>, context: Context) : RecyclerView.Adapter<DeviceAdapter.DeviceHolder>() {
    val mContext = context
    var v: View? = null
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DeviceHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.listofparents, parent, false)
        v = view

        return DeviceHolder(view)
    }



    var data: ArrayList<MyDevices> = list
    override fun onBindViewHolder(holder: DeviceHolder?, position: Int) {
        holder!!.devname.text =  data[position].NAME
        holder.devdescription.text =  data[position].ID


        var id = data[position].ID
        var parentid = data[position].parentId

        holder.devclickable.setOnClickListener {
            val intent = Intent(mContext, Command_Activity::class.java)
            intent.putExtra("idpar", parentid)
            intent.putExtra("serialval",id)
            intent.putExtra("parentName",data[position].parentname)
            intent.putExtra("parentEmail",data[position].parentemail)
            intent.putExtra("parentNo",data[position].parentno)
            intent.putExtra("parentSerialNo",data[position].parentsno)
            v!!.context.startActivity(intent)


        }

       holder.devclickable.setOnLongClickListener(object :View.OnLongClickListener{
           override fun onLongClick(p0: View?): Boolean {
          updateItem(id,parentid)
               return false
           }
       })

    }

    override fun getItemCount(): Int {
        return data.size
    }

    class DeviceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var devname: TextView
        var devdescription: TextView
        var devclickable: LinearLayout


        init {

            devclickable = itemView.findViewById(R.id.clickable)
            devname = itemView.findViewById(R.id.title)
            devdescription = itemView.findViewById(R.id.description)
        }


    }



    fun updateItem(serial:String,id:String){

        val alert:AlertDialog.Builder = AlertDialog.Builder(v!!.rootView.context)

        val edittext = EditText(v!!.rootView.context)

        alert.setTitle("Update")
                alert.setMessage("Update Name")
        alert.setView(edittext)




        alert.setPositiveButton("Update", DialogInterface.OnClickListener { dialog, whichButton ->
            if(edittext.text.toString() != "") {
                UpdateFromFireBase().UpdateThis(id, serial, edittext.text.toString())
            }

            else{
                updateItem(serial,id)
            }

        })

        alert.setNegativeButton("Not Now", DialogInterface.OnClickListener { dialog, whichButton ->

            null

        })

      alert.show()

    }



}