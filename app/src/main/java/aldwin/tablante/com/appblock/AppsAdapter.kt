package aldwin.tablante.com.appblock

import aldwin.tablante.com.appblock.Account.Model.Apps
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class AppsAdapter(val appList: ArrayList<Apps>) : RecyclerView.Adapter<AppsAdapter.ViewHolder>(){


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AppsAdapter.ViewHolder{
        val v = LayoutInflater.from(p0.context).inflate(R.layout.get_app_usage, p0, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return appList.size

    }

    override fun onBindViewHolder(p0:ViewHolder, p1: Int) {
        p0.bindItems(appList[p1])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(user: Apps) {
            val txtAppName = itemView.findViewById(R.id.appName) as TextView
            val txtAppLogo  = itemView.findViewById(R.id.appLogo) as ImageView
            txtAppName.text = user.name
            txtAppLogo.setImageResource(user.image)
        }
    }

}