package com.senasoft2021.senasoft2021.ui.home.actiivtie

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.senasoft2021.senasoft2021.R
import com.senasoft2021.senasoft2021.databinding.FragmentInfoDenunciaBinding
import com.senasoft2021.senasoft2021.models.DenunciaRegister


class InfoDenunciaFragment: DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL,R.style.dialog_full_screen)

    }

    private var _bining:FragmentInfoDenunciaBinding?=null
    private val binding get() = _bining!!
    private lateinit var denunciasViewModel: DenunciaViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _bining = FragmentInfoDenunciaBinding.inflate(LayoutInflater.from(context))
        val dialog= super.onCreateDialog(savedInstanceState)
        denunciasViewModel=ViewModelProvider(requireActivity()).get(DenunciaViewModel::class.java)
        dialog.setContentView(binding.root)
        dialog.window?.setWindowAnimations(R.style.dialog_anim)
        showInfo()
        return dialog
    }

    /**
     * mostrar la info del evento seleccionado
     */
    private fun showInfo() {

        denunciasViewModel.getDenuncia().observe(this){

            it?.let {
                binding.idTxtInfoDenunciaTitle.text=it.title
                binding.idTxtInfoDenunciaDate.text="Registrada el: ${it.date}"
                binding.idTxtInfoDenunciaDescrip.text=it.description
                Glide.with(binding.idImgInfoDenuncia).load(it.img).into(binding.idImgInfoDenuncia)
            }


        }



    }


}