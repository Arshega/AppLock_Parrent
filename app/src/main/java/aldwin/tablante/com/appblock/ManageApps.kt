package aldwin.tablante.com.appblock


import aldwin.tablante.com.appblock.Account.Introduction.Main.Activity.MainActivity
import aldwin.tablante.com.appblock.R.id.BlockApp
import aldwin.tablante.com.appblock.R.id.btnUnblock
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.R.attr.checked
import android.content.Context
import android.R.attr.checked
import android.app.Activity
import android.widget.*

import android.widget.Toast
import java.nio.file.Files.size
import android.graphics.drawable.Drawable
import android.R.array
import android.annotation.SuppressLint
import android.app.Dialog
import android.app.ListActivity
import android.content.DialogInterface
import android.content.SharedPreferences
import android.content.res.TypedArray
import android.graphics.Color
import android.os.Build
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.*
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FieldValue
import kotlinx.android.synthetic.main.activity_manage_apps.*
import kotlinx.android.synthetic.main.custom.*
import java.util.*
import kotlin.collections.ArrayList

import kotlin.collections.HashMap
import kotlin.collections.LinkedHashSet


class ManageApps : ListActivity() {
    var id = ""
    var serial = ""
    var newList : ArrayList<String> = arrayListOf()
    var lis : ArrayList<String> = arrayListOf()
    var selectedApps : ArrayList<String> = arrayListOf()
    var items: Array<CharSequence> = arrayOf()
    var listing = arrayListOf<String>()
    var mmap: HashMap<String, Any?> = HashMap()
    var deletes: HashMap<String, Any?> = HashMap()

    var blockApps : ArrayList<String> = arrayListOf()
    lateinit var alertDialog: AlertDialog
    var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_apps)

        id = intent.getStringExtra("id")
        serial = intent.getStringExtra("serial")

        var database = FirebaseFirestore.getInstance()
        database.collection("Devices")
                .whereEqualTo("Serial", serial)
                .addSnapshotListener { p0, p1 ->
                    if (p0!!.isEmpty) {
                        Toast.makeText(applicationContext, "Device is Restarting the Connector", Toast.LENGTH_SHORT).show()
                    } else {
                        for (doc in p0!!.documents) {

                            var BlockApplications = doc.toObject(BlockApplications::class.java)
                            var packageList = BlockApplications.BlockApplications

                            var InstalledAppLabel = doc.toObject(InstalledAppLabel::class.java)
                            var applist = InstalledAppLabel.InstalledAppLabel

                            for (i in 0 until applist.size) {
                                lis.add(applist[i])
                            }

                            for (e in 0 until packageList.size) {
                                blockApps.add(packageList[e])
                            }
                            lis.removeAll(packageList)
                        }
                    }
                }

        val listview = listView
        lis = ArrayList(LinkedHashSet<String>(lis))
        listview.choiceMode = AbsListView.CHOICE_MODE_MULTIPLE
        listview.isTextFilterEnabled = true
        listAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_checked, lis)



        BlockApp.setOnClickListener {
            var newBlockList: ArrayList<String> = arrayListOf()
            var hs: Set<String> = HashSet()
            for (e in 0 until blockApps.size) {
                newBlockList.add(blockApps[e])
            }
            for (i in 0 until newBlockList.size) {
                newList.add(blockApps[i])
            }

            var d = FirebaseFirestore.getInstance()
            var noteRef = d.collection("Devices").document(serial)
            mmap.put("BlockApplications", newList)
            noteRef.set(mmap, SetOptions.merge()).addOnCompleteListener {
                lis = ArrayList(LinkedHashSet<String>(lis))
                listAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_checked, lis)
                newList.clear()
                newBlockList.clear()
                mmap.clear()
                fab.close(true)
            }

        }

        ShowApp.setOnClickListener {
            var list: ArrayList<String> = arrayListOf()
            var unblock: ArrayList<String> = arrayListOf()
            var oneList: ArrayList<String> = arrayListOf()
            var fiveList: ArrayList<String> = arrayListOf()
            var tenList: ArrayList<String> = arrayListOf()
            var twenty: ArrayList<String> = arrayListOf()
            var thirty: ArrayList<String> = arrayListOf()
            blockApps = ArrayList(LinkedHashSet<String>(blockApps))
            items = blockApps.toArray(arrayOfNulls<CharSequence>(blockApps.size))
            var dialog = AlertDialog.Builder(this)
                    .setTitle("Blocked Applications")
                    .setIcon(R.drawable.mobile)
                    .setItems(items, object : DialogInterface.OnClickListener{
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            var builder = AlertDialog.Builder(this@ManageApps)
                            var inflater = layoutInflater;
                            var dialogView  = inflater.inflate(R.layout.custom,null)
                            builder.setTitle("Block Applications")
                            builder.setIcon(R.drawable.mobile)
                            builder.setMessage("Please Select from the following!")
                            builder.setView(dialogView)

                            var one = dialogView.findViewById<Button>(R.id.btnUnblock)
                            var two = dialogView.findViewById<Button>(R.id.btnSet)
                            val  dialogs = builder.create()


                            one.setOnClickListener {
                                var firestore = FirebaseFirestore.getInstance()
                                var  noterefs = firestore.collection("Devices").document(serial)
                                for (i in 0 until blockApps.size) {
                                    list.add(blockApps[i])
                                }
                                unblock.add(blockApps[which])
                                list.removeAll(unblock)
                                deletes.put("BlockApplications", list)
                                noterefs.set(deletes, SetOptions.merge()).addOnCompleteListener {
                                    dialogs.dismiss()
                                }

                            }

                            two.setOnClickListener { it ->
                                var item : Array<CharSequence> = arrayOf("1 Minute", "5 Minutes", "10 Minutes", "20 Minutes", "30 Minutes")
                                var builder = AlertDialog.Builder(this@ManageApps)
                                builder.setTitle("Select a Time to Lock the device")
                                builder.setIcon(R.drawable.hourglass)
                                builder.setSingleChoiceItems(item, -1, object : DialogInterface.OnClickListener{
                                    override fun onClick(dialog: DialogInterface?, pos: Int) {
                                        if (pos == 0){
                                            oneList.add(blockApps[which])
                                            var timeFire = FirebaseFirestore.getInstance()
                                            var  noted = timeFire.collection("Devices").document(serial)
                                            blockApps.removeAll(oneList)
                                            mmap.put("BlockApplications", blockApps)
                                            noted.set(mmap, SetOptions.merge()).addOnCompleteListener {
                                                mmap.clear()
                                                mmap.put("OneMinute", oneList)
                                                noted.set(mmap, SetOptions.merge()).addOnCompleteListener {
                                                    mmap.clear()
                                                    oneList.clear()
                                                }
                                            }

                                        }
                                        if (pos == 1){
                                            fiveList.add(blockApps[which])
                                            var timeFire = FirebaseFirestore.getInstance()
                                            var  noted = timeFire.collection("Devices").document(serial)
                                            blockApps.removeAll(fiveList)
                                            mmap.put("BlockApplications", blockApps)
                                            noted.set(mmap, SetOptions.merge()).addOnCompleteListener {
                                                mmap.clear()
                                                mmap.put("FiveMinutes", fiveList)
                                                noted.set(mmap, SetOptions.merge()).addOnCompleteListener {
                                                    mmap.clear()
                                                    fiveList.clear()
                                                }
                                            }
                                        }
                                        if (pos == 2){
                                            tenList.add(blockApps[which])
                                            var timeFire = FirebaseFirestore.getInstance()
                                            var  noted = timeFire.collection("Devices").document(serial)
                                            blockApps.removeAll(tenList)
                                            mmap.put("BlockApplications", blockApps)
                                            noted.set(mmap, SetOptions.merge()).addOnCompleteListener {
                                                mmap.clear()
                                                mmap.put("TenMinutes", tenList)
                                                noted.set(mmap, SetOptions.merge()).addOnCompleteListener {
                                                    mmap.clear()
                                                    tenList.clear()
                                                }
                                            }

                                        }
                                        if (pos == 3){
                                            twenty.add(blockApps[which])
                                            var timeFire = FirebaseFirestore.getInstance()
                                            var  noted = timeFire.collection("Devices").document(serial)
                                            blockApps.removeAll(twenty)
                                            mmap.put("BlockApplications", blockApps)
                                            noted.set(mmap, SetOptions.merge()).addOnCompleteListener {
                                                mmap.clear()
                                                mmap.put("TwentyMinutes", twenty)
                                                noted.set(mmap, SetOptions.merge()).addOnCompleteListener {
                                                    mmap.clear()
                                                    twenty.clear()
                                                }
                                            }

                                        }
                                        if (pos == 4){
                                            thirty.add(blockApps[which])
                                            var timeFire = FirebaseFirestore.getInstance()
                                            var  noted = timeFire.collection("Devices").document(serial)
                                            blockApps.removeAll(thirty)
                                            mmap.put("BlockApplications", blockApps)
                                            noted.set(mmap, SetOptions.merge()).addOnCompleteListener {
                                                mmap.clear()
                                                mmap.put("ThirtyMinutes", thirty)
                                                noted.set(mmap, SetOptions.merge()).addOnCompleteListener {
                                                    mmap.clear()
                                                    thirty.clear()
                                                }
                                            }

                                        }
                                        alertDialog.dismiss()
                                    }
                                })
                                alertDialog = builder.create()
                                alertDialog.show()
                                dialogs.dismiss()
                            }
                            dialogs.show()
                        }
                    }).create()
            dialog.show()
         fab.close(true)
        }
    }

    override fun onListItemClick(parent: ListView, v: View, position: Int, id: Long) {
        val item = v as CheckedTextView
        if (item.isChecked){
            newList.add(lis[position])
        }else{
            newList.remove(lis[position])
        }
    }
}