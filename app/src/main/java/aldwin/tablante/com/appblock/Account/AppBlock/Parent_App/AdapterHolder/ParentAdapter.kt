package aldwin.tablante.com.appblock.Account.AppBlock.Parent_App.AdapterHolder

import aldwin.tablante.com.appblock.Account.AppBlock.Model.MyDevices
import aldwin.tablante.com.appblock.R
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

class ParentAdapter(list:ArrayList<MyDevices>, context:Context) : RecyclerView.Adapter<ParentAdapter.ParentHolder> (){
    val mContext = context
    var v: View? = null
    var data: ArrayList<MyDevices> = list
    override fun onBindViewHolder(holder: ParentHolder?, position: Int ){
        holder!!.devname.text =  data[position].NAME
        holder.devdescription.text =  data[position].ID
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ParentHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.listofparents, parent, false)
        v = view

        return ParentHolder(view) }

    override fun getItemCount(): Int {
return data.size    }
    class ParentHolder(itemView:View) : RecyclerView.ViewHolder(itemView){

        var devname: TextView
        var devdescription: TextView
        var devclickable: LinearLayout


        init {

            devclickable = itemView.findViewById(R.id.clickable)
            devname = itemView.findViewById(R.id.title)
            devdescription = itemView.findViewById(R.id.description)
        }
    }
}