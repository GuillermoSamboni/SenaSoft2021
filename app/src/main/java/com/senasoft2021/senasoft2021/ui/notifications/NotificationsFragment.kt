package com.senasoft2021.senasoft2021.ui.notifications

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.huawei.hms.maps.CameraUpdateFactory
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.model.LatLng
import com.huawei.hms.maps.model.PointOfInterest
import com.senasoft2021.senasoft2021.databinding.FragmentNotificationsBinding

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



        return root
    }
    /**
     *
     */
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

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}