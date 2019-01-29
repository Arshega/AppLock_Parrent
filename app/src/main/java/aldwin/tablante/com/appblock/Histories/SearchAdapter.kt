package aldwin.tablante.com.appblock.Histories

import aldwin.tablante.com.appblock.R
import aldwin.tablante.com.appblock.SearchText
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class SearchAdapter(list: ArrayList<SearchText>, context: Context) : RecyclerView.Adapter<SearchAdapter.SearchAdapterHolder>() {
    private var data = list
    private var v: View?=null
    override fun onBindViewHolder(holder: SearchAdapterHolder?, position: Int) {
        holder!!.searchInputText.setText(data[position].SearchInput)


       if(        DateUtils.isToday(data[position].TimeStamp.time)){
           val dateToFormat = data[position].TimeStamp.time
           val dateFormatExpression = SimpleDateFormat("hh:mm:ss a")
           val formattedDate = dateFormatExpression.format(dateToFormat)
           holder!!.timerstamper.setText(formattedDate.toString())

       }
        else{

           val dateToFormat = data[position].TimeStamp
           val dateFormatExpression = SimpleDateFormat("dd/mm/yyyy")
           val formattedDate = dateFormatExpression.format(dateToFormat)
           holder!!.timerstamper.setText(formattedDate.toString())

       }



        holder!!.ccard.setOnLongClickListener(object :View.OnLongClickListener{
            override fun onLongClick(p0: View?): Boolean {

// TODO
                return false
            }
        })


    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SearchAdapterHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.list_search, parent, false)
        v = view

        return SearchAdapterHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class SearchAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var searchInputText: TextView
        var timerstamper: TextView
        var ccard: CardView

        init {
            searchInputText = itemView.findViewById(R.id.searchinputted)
            timerstamper = itemView.findViewById(R.id.timeS)
            ccard = itemView.findViewById(R.id.clickablecard)
        }


    }
    fun deleteItem(pos: Int) {
        data.removeAt(pos)
        notifyItemRemoved(pos)

    }
}