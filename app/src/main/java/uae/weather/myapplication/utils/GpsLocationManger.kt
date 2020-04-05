package uae.weather.myapplication.utils

import android.content.ContentValues
import android.content.Context
import android.content.IntentSender.SendIntentException
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*

class GpsLocationManger(private var fragment: Fragment?, private val GPS_REQUEST: Int) {
    private val mSettingsClient: SettingsClient
    private val mLocationSettingsRequest: LocationSettingsRequest
    private val locationManager: LocationManager
    private val locationRequest: LocationRequest
    private var onGpsListener: GPSListener? = null
    // method for turn on GPS
    fun turnGPSOn() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            onGpsListener?.gpsStatus(true)
        } else {
            mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener {
                    //  GPS is already enable, callback GPS status through listener
                    onGpsListener?.gpsStatus(true)
                }
                .addOnFailureListener { e ->
                    val statusCode = (e as ApiException).statusCode
                    when (statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try { // Show the dialog by calling startResolutionForResult(), and check the
// result in onActivityResult().
                            val rae = e as ResolvableApiException
                            fragment?.startIntentSenderForResult(
                                rae.resolution.intentSender,
                                GPS_REQUEST,
                                null,
                                0,
                                0,
                                0,
                                null
                            );
                        } catch (sie: SendIntentException) {
                            Log.i(
                                ContentValues.TAG,
                                "PendingIntent unable to execute request."
                            )
                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                            val errorMessage =
                                "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings."
                            Log.e(ContentValues.TAG, errorMessage)
                            Toast.makeText(
                                fragment!!.context,
                                errorMessage,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
        }
    }

    fun setUpLocationListener(context: Context?) {
        if (context == null) return
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    for (location in locationResult.locations) {
                        onGpsListener?.updateLocation(location.latitude, location.longitude)
                        fusedLocationProviderClient.removeLocationUpdates(this)
                    }

                }
            },
            Looper.myLooper()
        )
    }

    fun setGPSListener(gpsListener: GPSListener) {
        this.onGpsListener = gpsListener
    }

    interface GPSListener {
        fun gpsStatus(isGPSEnable: Boolean)
        fun updateLocation(lat: Double, lng: Double)
    }

    fun clear() {
        if (fragment != null) fragment = null

    }

    init {
        locationManager =
            fragment!!.context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        mSettingsClient = LocationServices.getSettingsClient(fragment!!.context!!)
        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10 * 1000.toLong()
        locationRequest.fastestInterval = 2 * 1000.toLong()
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        mLocationSettingsRequest = builder.build()
        builder.setAlwaysShow(true) //this is the key ingredient
    }
}