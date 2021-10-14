package com.senasoft2021.senasoft2021.huawei.location

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.IntentSender
import android.location.Location
import android.os.Looper
import android.util.Log
import com.huawei.hmf.tasks.OnSuccessListener
import com.huawei.hms.common.ResolvableApiException
import com.huawei.hms.location.*
import com.senasoft2021.senasoft2021.constantes.Constantes

class LocationService(val context: Context): LocationCallback() {


    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationLasService:Location




    fun starRequest(){
        var settingClient= LocationServices.getSettingsClient(context)
        var buildRequest=LocationSettingsRequest.Builder()

        var myLocationRequest=LocationRequest()
        myLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        myLocationRequest.getExtras()
        myLocationRequest.countryCode
        buildRequest.addLocationRequest(myLocationRequest)
        var locationRequest=buildRequest.build()
        settingClient.checkLocationSettings(locationRequest)
            .addOnSuccessListener (OnSuccessListener{locationSettingsResponse->
                fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(context)
                fusedLocationProviderClient.requestLocationUpdates(myLocationRequest, this@LocationService, Looper.getMainLooper())
            }) .addOnFailureListener { e->
                when((e as ResolvableApiException).statusCode){
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED->try {
                        var raae=e
                        raae.startResolutionForResult(context as Activity, 0)
                    }catch (e:IntentSender.SendIntentException){
                        Log.d("errorLocation", e.message)
                    }
                }
            }
    }

    override fun onLocationResult(result: LocationResult?) {
        super.onLocationResult(result)
        result?.let {
            locationLasService=result.lastLocation

            Constantes.myLocation=locationLasService

            var alerDialog=AlertDialog.Builder(context)
                alerDialog.setCancelable(false)
                    .setTitle("Mi hubicacion")
                    .setMessage("${locationLasService.longitude} :: ${locationLasService.latitude}")
                    .setPositiveButton("Ok"){_,_->
                        alerDialog.setCancelable(true)
                    }.create().show()
        }

    }
}