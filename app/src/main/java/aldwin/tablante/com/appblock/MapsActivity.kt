package aldwin.tablante.com.appblock

import aldwin.tablante.com.appblock.Notifiers.GpsService
import android.app.ActivityManager
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.internal.NavigationMenu
import android.util.Log
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.*
import io.github.yavski.fabspeeddial.FabSpeedDial
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private val TAG = MapsActivity::class.java.simpleName
    private val mMarkers = HashMap<String, Marker>()

    private lateinit var userList: MutableList<GpsLocations>

    private var id = ""
    private var parentLoc : LatLng? = null
    private var childLoc : LatLng? = null
     var serial = ""
    var bool = true
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        id = intent.getStringExtra("id")
        serial = intent.getStringExtra("serial")
        userList = mutableListOf()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)



        var fireStorage = FirebaseFirestore.getInstance()
        var fireRef = fireStorage.collection("Devices").document(serial).collection("Locations")

        fireRef.document("Current").addSnapshotListener(object:EventListener<DocumentSnapshot>{

            override fun onEvent(p0: DocumentSnapshot?, p1: FirebaseFirestoreException?) {
                try {
                    if (p0!!.exists()) {
                        val lat = p0!!.get("latitude").toString()
                        val long = p0!!.get("longitude").toString()
                        val location = LatLng(lat.toDouble(), long.toDouble())
                        var key = serial
                          childLoc = location
                        if (!mMarkers.containsKey(key)) {
                            mMarkers.put(key, mMap.addMarker(MarkerOptions().title(key).position(location)))
                        } else {
                            mMarkers.get(key)!!.setPosition(location)
                        }
                        val builder: LatLngBounds.Builder = LatLngBounds.Builder()
                        for (marker: Marker in mMarkers.values) {
                            builder.include(marker.position)
                        }

                        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 200))
                    }
                }
                catch (e:Exception){
                    e.printStackTrace()


                }
            }

        })



        locMenu.setMenuListener(object: FabSpeedDial.MenuListener{
            override fun onMenuClosed() {
                null
            }

            override fun onMenuItemSelected(menuItem: MenuItem?): Boolean {
                if(menuItem!!.title.toString() == "Refresh Location"){

                   Toast.makeText(applicationContext,menuItem.title.toString(),Toast.LENGTH_LONG).show()
                    var fStore = FirebaseFirestore.getInstance()
                    var rStore = fStore.collection("Devices").document(serial).update("Location",true)
                    var intservice = Intent(this@MapsActivity, GpsService::class.java)
                    intservice.putExtra("id",id)
                    startService(intservice)
                }

                if(menuItem!!.title.toString() == "Current Location"){

                    Toast.makeText(applicationContext,menuItem.title.toString(),Toast.LENGTH_LONG).show()

                    var myFire = fireStorage.collection("Parent").document(id).collection("Locations")
                    myFire.document("Current").addSnapshotListener(object:EventListener<DocumentSnapshot>{

                        override fun onEvent(p0: DocumentSnapshot?, p1: FirebaseFirestoreException?) {
                            try{
                                if (p0!!.exists()) {
                                    val lat = p0!!.get("latitude").toString()
                                    val long = p0!!.get("longitude").toString()
                                    val location = LatLng(lat.toDouble(), long.toDouble())
                                    parentLoc = location
                                    var key = "Me"

                                    if (!mMarkers.containsKey(key)) {
                                        mMarkers.put(key, mMap.addMarker(MarkerOptions().title(key).position(location)))
                                    } else {
                                        mMarkers.get(key)!!.setPosition(location)
                                    }
                                    val builder: LatLngBounds.Builder = LatLngBounds.Builder()
                                    for (marker: Marker in mMarkers.values) {
                                        builder.include(marker.position)
                                    }
                                    addPoly()
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 200))



                                }
                            }
                            catch (e:Exception){e.printStackTrace()}
                        }
                    })

                }
                return true
            }

            override fun onPrepareMenu(navigationMenu: NavigationMenu?): Boolean {
                return true

            }

        })
    }



    fun addPoly(){


        val poly  = PolylineOptions()
        poly.width(5F)
                .add(childLoc)
                .add(parentLoc)
                .geodesic(true)
                .color(Color.YELLOW)

 mMap.addPolyline(poly)



    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!;
        mMap.setMaxZoomPreference(20F);
        GoogleMap.MAP_TYPE_SATELLITE

         //subscribeToUpdates()
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


}


