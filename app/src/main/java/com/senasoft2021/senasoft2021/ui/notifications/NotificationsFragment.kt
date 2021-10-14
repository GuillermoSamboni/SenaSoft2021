package com.senasoft2021.senasoft2021.ui.notifications

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.huawei.hms.maps.CameraUpdateFactory
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.model.LatLng
import com.huawei.hms.maps.model.PointOfInterest
import com.huawei.hms.site.api.SearchResultListener
import com.huawei.hms.site.api.SearchServiceFactory
import com.huawei.hms.site.api.model.DetailSearchRequest
import com.huawei.hms.site.api.model.DetailSearchResponse
import com.huawei.hms.site.api.model.SearchStatus
import com.huawei.hms.site.api.model.Site
import com.senasoft2021.senasoft2021.databinding.FragmentNotificationsBinding
import java.net.URLEncoder

class NotificationsFragment : Fragment(), HuaweiMap.OnPoiClickListener {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentNotificationsBinding? = null
    lateinit var huaweiMap:HuaweiMap

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        permisoMapaService()

        binding.idMapView.onCreate(null)
        binding.idMapView.getMapAsync{map->onMapReady(map)}

        permisoGps()

        return root
    }
    /**
     *
     */

    fun permisoGps(){
        startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }

    private fun permisoMapaService(){
        val permisoMap=ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
        if (permisoMap==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(requireContext() as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION) ,1)
        }else{
            ActivityCompat.requestPermissions(requireContext() as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
    }

    /**
     * craamoss el mapa
     */
    private fun onMapReady(map: HuaweiMap?) {
        if (map != null) {
            huaweiMap=map
            huaweiMap.apply {
                huaweiMap.isMyLocationEnabled=true
                huaweiMap.isIndoorEnabled=true
                var cameraUbdate=CameraUpdateFactory.newLatLngZoom(LatLng(0.99, -9.00), 10f)
                huaweiMap.animateCamera(cameraUbdate)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.idMapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.idMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.idMapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.idMapView.onLowMemory()
    }

    override fun onStop() {
        super.onStop()
        binding.idMapView.onStop()
    }

    override fun onPoiClick(poi: PointOfInterest?) {
        val resultLisener:SearchResultListener<DetailSearchResponse> = object : SearchResultListener<DetailSearchResponse>{
            override fun onSearchResult(resultSuccess: DetailSearchResponse?) {
                var site: Site?=null
                if (resultSuccess==null || resultSuccess.site.also { site=it }==null){
                    return
                }
                site?.let {  displayInfoSite(it)}
            }

            private fun displayInfoSite(it: Site) {
                var dialogSite=AlertDialog.Builder(requireContext())
                dialogSite.setTitle("${it.name}")
                dialogSite.setMessage("${it.formatAddress} ::  ${it.siteId} :: ${it.poi.phone}")
                dialogSite.setCancelable(false)
                    .setPositiveButton("Ver"){_,_->
                        dialogSite.setCancelable(true)
                    }.create().show()
            }

            override fun onSearchError(p0: SearchStatus?) {
                Log.d("nadaPoi", "mdada")
                Toast.makeText(requireContext(), "no se pudo ver este luagr revisa el intenet", Toast.LENGTH_SHORT).show()
            }
        }

        poi.let {
            val appKey=URLEncoder.encode("CwEAAAAAhyXk7a+Aj8V7NwMWAdYEvgKyxmy29DdF9Sd1yYfiR1KONfrpFBgrt9gdHmdJA2+ESTGuQyoV/5NFqPYutlki6dasoE8=", "UTF-8")
            val searchService=SearchServiceFactory.create(requireContext(), appKey)
            var reques=DetailSearchRequest().apply {
                siteId=it?.placeId
            }
            searchService.detailSearch(reques, resultLisener)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}