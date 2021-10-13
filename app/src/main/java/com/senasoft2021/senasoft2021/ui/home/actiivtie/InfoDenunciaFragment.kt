package com.senasoft2021.senasoft2021.ui.home.actiivtie

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.senasoft2021.senasoft2021.R
import com.senasoft2021.senasoft2021.databinding.FragmentInfoDenunciaBinding
import com.senasoft2021.senasoft2021.models.DenunciaRegister


class InfoDenunciaFragment(private val denuncia:DenunciaRegister) : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL,R.style.dialog_full_screen)

    }

    private var _bining:FragmentInfoDenunciaBinding?=null
    private val binding get() = _bining!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _bining = FragmentInfoDenunciaBinding.inflate(LayoutInflater.from(context))
        val dialog= super.onCreateDialog(savedInstanceState)

        dialog.setContentView(binding.root)
        dialog.window?.setWindowAnimations(R.style.dialog_anim)
        showInfo()
        return dialog
    }

    private fun showInfo() {
        binding.idTxtInfoDenunciaTitle.text=denuncia.title
        binding.idTxtInfoDenunciaDate.text="Registrada el: ${denuncia.date}"
        binding.idTxtInfoDenunciaDescrip.text=denuncia.description
        Glide.with(binding.idImgInfoDenuncia).load(denuncia.img).into(binding.idImgInfoDenuncia)

    }


}