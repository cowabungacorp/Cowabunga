package br.com.brozoski.cowabunga

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager

import android.location.Location
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.TabLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityCompat.checkSelfPermission
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Toast

import br.com.brozoski.cowabunga.adapters.FragmentViewPagerAdapter
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng


class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    private lateinit var viewPager : ViewPager
    private lateinit var adapter : FragmentViewPagerAdapter
    private lateinit var tabLayout : TabLayout

    private var mApiGoogle: GoogleApiClient? = null
    private var mLocation: Location? = null
    private var mLocationManager: LocationManager? = null
    private var mLocationRequest: LocationRequest? = null

    private val listener: com.google.android.gms.location.LocationListener? = null
    private val UPDATE_INTERVAL = (2 * 1000).toLong() //10 sec
    private val FASTEST_INTERVAL = 2000.toLong() // 2 SEC

    private var locationManager: LocationManager? = null
    private val isLocationEnabled: Boolean
        get() {
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        }




    var LATITUDE = ""
    var LONGITUDE = ""
    val REQUEST_CODE = 1000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mApiGoogle = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener {this}
            .addApi(LocationServices.API)
            .build()

        mLocationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        Log.d("gggg", "uooo")

        viewPager = findViewById(R.id.viewPager)
        adapter = FragmentViewPagerAdapter(this, supportFragmentManager)

        viewPager.adapter = adapter

        tabLayout = findViewById(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)

        tabLayout.getTabAt(0)!!.setIcon(android.R.drawable.ic_btn_speak_now)
        tabLayout.getTabAt(1)!!.setIcon(android.R.drawable.ic_btn_speak_now)
        tabLayout.getTabAt(2)!!.setIcon(android.R.drawable.ic_btn_speak_now)



    }

    @SuppressLint("MissingPermission")
    override fun onConnected(p0: Bundle?) {
        if (checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        startLocationUpdates()

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mApiGoogle)

        if (mLocation == null) {
            startLocationUpdates()
        }
        if (mLocation != null) {

            // mLatitudeTextView.setText(String.valueOf(mLocation.getLatitude()));
            //mLongitudeTextView.setText(String.valueOf(mLocation.getLongitude()));
        } else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onConnectionSuspended(i: Int) {
        Log.i(TAG, "Connection Suspended")
        mApiGoogle!!.connect()
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode())
    }

    override fun onStart() {
        super.onStart()
        if (mApiGoogle != null) {
            mApiGoogle!!.connect()
        }
    }

    override fun onStop() {
        super.onStop()
        if (mApiGoogle!!.isConnected()) {
            mApiGoogle!!.disconnect()
        }
    }
     override fun onLocationChanged(location: Location) {

        val msg = "Updated Location: " +
                java.lang.Double.toString(location.latitude) + "," +
                java.lang.Double.toString(location.longitude)

       // location.latitude.toString()
     //   location.longitude.toString()
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

        // You can now create a LatLng Object for use with maps
        val latLng = LatLng(location.latitude, location.longitude)
    }

    @SuppressLint("MissingPermission")
    protected fun startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL)
            .setFastestInterval(FASTEST_INTERVAL)
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mApiGoogle,
            mLocationRequest, this)
        Log.d("reque", "--->>>>")
    }

    private fun checkLocation(): Boolean {
        if (!isLocationEnabled)
            showAlert()
        return isLocationEnabled
    }

    private fun showAlert() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Enable Location")
            .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " + "use this app")
            .setPositiveButton("Location Settings") { paramDialogInterface, paramInt ->
                val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(myIntent)
            }
            .setNegativeButton("Cancel") { paramDialogInterface, paramInt -> }
        dialog.show()
    }










    companion object {
        private val TAG = "MainActivity"

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            return intent
        }
    }
}
