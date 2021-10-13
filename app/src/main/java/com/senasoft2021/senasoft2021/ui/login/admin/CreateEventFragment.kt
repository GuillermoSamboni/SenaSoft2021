package com.senasoft2021.senasoft2021.ui.login.admin

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.senasoft2021.senasoft2021.R
import com.senasoft2021.senasoft2021.database.RoomDatabaseClient
import com.senasoft2021.senasoft2021.databinding.FragmentCreateEventBinding
import com.senasoft2021.senasoft2021.databinding.FragmentHomeAdminBinding
import com.senasoft2021.senasoft2021.extension_function.showToast
import com.senasoft2021.senasoft2021.models.EventRegister
import com.senasoft2021.senasoft2021.validations.Validations
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*


class CreateEventFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private var _binding: FragmentCreateEventBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentCreateEventBinding.inflate(inflater,container,false)
        return binding.root
    }

    private var uriImage:Uri?=null
    private var startDate:String?=null
    private var endDate:String?=null

    /**
     * para lanzar multiples permisos a la vez
     */
    private val launcherPErmissions=registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
        onMultiplePermissionsResult(it)
    }

    /**
     * para lanzar un startActivity que me obtenga la imagen
     */
    private val launcherImages=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        pickImage(it)
    }

    private fun onMultiplePermissionsResult(permissions: Map<String, Boolean>) {

        for( (key, result) in permissions ){
            when(key){
                Manifest.permission.READ_EXTERNAL_STORAGE->{launchIntentImage()}
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launcherPErmissions.launch(arrayOf(Manifest.permission.READ_CALENDAR))

        binding.idImgCreateEventPickImg.setOnClickListener { launcherPErmissions.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)) }
        binding.idBtnCreateEventStartDate.setOnClickListener { pickDates("start") }
        binding.idBtnCreateEventEndDate.setOnClickListener { pickDates("end") }
        binding.idBtnCreateEventSave.setOnClickListener { saveEvent() }

    }

    private fun saveEvent() {

        if(validarcacmpos()){
            requireContext().showToast("Los campos no pueden ser vacios")
            return
        }

        val title=binding.idTxtCreateEventTitle.text.toString().trim()
        val descrip=binding.idTxtCreateEventDescrip.text.toString().trim()

        if(uriImage==null){
            requireContext().showToast("Ingrese una imagen para el evento")
            return
        }


        if(startDate == null){
            requireContext().showToast("Ingrese una fecha de inicio")
            return
        }
        if(endDate == null){
            requireContext().showToast("Ingrese una fecha de finalización")
            return
        }

        val event=EventRegister(0, title,descrip, uriImage!!.toString(), startDate!!, endDate!!)

        if(RoomDatabaseClient.registerEvent(event,requireContext())){
            findNavController().popBackStack()
        }
        else
            requireContext().showToast("Ha ocurrido un error")


    }

    /**
     * validar los campos de registro
     * @return true si al menos uno de los campos es nulo o está vacio -
     * flse en caso contrario
     */
    private fun validarcacmpos(): Boolean {
        val emptyTitle=Validations.validateEditText(binding.idTxtCreateEventTitle)
        val emptyDescription=Validations.validateEditText(binding.idTxtCreateEventDescrip)

        return emptyTitle || emptyTitle
    }


    /**
     * lanzan un intent que abre la galeria y obtiene una imagen
     */
    private fun launchIntentImage(){
        val i=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        launcherImages.launch(i)
    }

    /**
     * obtener el resultado del intent
     * y sacar la uri de la imagen seleccionada
     */
    private fun pickImage(result:ActivityResult){
        result.data?.data?.let {
            uriImage=it
            Glide.with(requireContext()).load(it).into(binding.idImgCreateEventPickImg)
        }
    }


    /**
     * mostrar un dialogo para que el usuario escoja una fecha y hora
     * @param action accion a realizar start si la fecha va a se la de inicio - end si la fecha va a ser de finalizacion
     */
    private fun pickDates(action:String){

        val datePickert=MaterialDatePicker.Builder.datePicker().build()
        val timePicker=MaterialTimePicker.Builder().build()
        datePickert.show(childFragmentManager,"")
        datePickert.addOnPositiveButtonClickListener {
            val date=Date(it)
            val calendar=Calendar.getInstance()

            calendar.time=date

            timePicker.show(childFragmentManager,"")
            timePicker.addOnPositiveButtonClickListener {
                calendar.set(Calendar.HOUR,timePicker.hour)
                calendar.set(Calendar.MINUTE,timePicker.minute)
                val currentTime=SimpleDateFormat().format(calendar.time).toString()

                when(action){
                    "start"->{
                        startDate = currentTime
                        binding.idTxtCreateEventStartDate.text=currentTime
                    }

                    "end"->{
                        endDate = currentTime
                        binding.idTxtCreateEventEndDate.text=currentTime
                    }
                }


            }

        }


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}