package com.senasoft2021.senasoft2021.ui.home.actiivtie

import android.Manifest
import android.app.Dialog
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
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.senasoft2021.senasoft2021.R
import com.senasoft2021.senasoft2021.database.RoomDatabaseClient
import com.senasoft2021.senasoft2021.databinding.FragmentCreateDenunciaBinding
import com.senasoft2021.senasoft2021.databinding.FragmentCreateEventBinding
import com.senasoft2021.senasoft2021.extension_function.showToast
import com.senasoft2021.senasoft2021.models.DenunciaRegister
import com.senasoft2021.senasoft2021.validations.Validations
import java.text.SimpleDateFormat
import java.util.*

class CreateDenunciaFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL,R.style.dialog_full_screen)
    }

    private var _binding:FragmentCreateDenunciaBinding?=null
    private val binding get() = _binding!!

    private var uriImage: Uri?=null

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
                Manifest.permission.READ_EXTERNAL_STORAGE->{if(result)launchIntentImage()}
            }
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding= FragmentCreateDenunciaBinding.inflate(LayoutInflater.from(context))
        val dialog= super.onCreateDialog(savedInstanceState)
        dialog.setContentView(binding.root)
        dialog.window?.setWindowAnimations(R.style.dialog_anim)

        binding.idBtnCreateDenuncia.setOnClickListener { createDenuncia() }
        binding.idImgCreateDenunciaPickImg.setOnClickListener { launcherPErmissions.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)) }

        return dialog
    }

    /**
     * Crear una denuncia a partir de los campos requeridos
     */
    private fun createDenuncia() {

        if(validarCampos()){
            requireContext().showToast("Faltan campos por llenar")
            return
        }


        if(uriImage==null){
            requireContext().showToast("Ingrese una imagen")
            binding.idImgCreateDenunciaPickImg.playAnimation()
            return
        }

        val title=binding.idTxtCreateDenunciaTitle.text.toString().trim()
        val descrip=binding.idTxtCreateDenunciaDescrip.text.toString().trim()

        val time=SimpleDateFormat("yyyy/MM/dd - hh:mm").format(Calendar.getInstance().time).toString()

        val denuncia=DenunciaRegister(0,title,descrip,uriImage.toString(),time)

        if(RoomDatabaseClient.insertDenuncia(denuncia,requireContext())){
            startActivity(Intent(requireActivity(), DenunciaActivity::class.java))
            requireActivity().finish()
        }
        else
            requireContext().showToast("Ha ocurrido un error")



    }


    /**
     * validar todos los campos de texto
     * @return true si al menos uno de los campos es vacio o nulo
     *  - false en caso contrario
     */
    private fun validarCampos(): Boolean {
        val emptyTitle=Validations.validateEditText(binding.idTxtCreateDenunciaTitle)
        val emptyDescrip=Validations.validateEditText(binding.idTxtCreateDenunciaDescrip)
        return emptyTitle || emptyDescrip
    }


    /**
     * lanzan un intent que abre la galeria y obtiene una imagen
     */
    private fun launchIntentImage(){
        val i= Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        i.addCategory(Intent.CATEGORY_OPENABLE)
        launcherImages.launch(i)
    }

    /**
     * obtener el resultado del intent
     * y sacar la uri de la imagen seleccionada
     */
    private fun pickImage(result: ActivityResult){
        result.data?.data?.let {
            uriImage=it
            Glide.with(requireContext()).load(it).into(binding.idImgCreateDenunciaPickImg)
        }
    }


}