package com.senasoft2021.senasoft2021.ui.login.admin

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.senasoft2021.senasoft2021.R
import com.senasoft2021.senasoft2021.databinding.FragmentInfoEventBinding
import com.senasoft2021.senasoft2021.extension_function.showToast
import com.senasoft2021.senasoft2021.models.EventRegister


class InfoEventFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL,R.style.dialog_full_screen)

    }

    private var _binding:FragmentInfoEventBinding?=null
    private val binding get() = _binding!!
    private lateinit var eventViewModel:EventsViewModel


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding= FragmentInfoEventBinding.inflate(LayoutInflater.from(context))
        val dialog= super.onCreateDialog(savedInstanceState)
        eventViewModel=ViewModelProvider(requireActivity()).get(EventsViewModel::class.java)
        dialog.setContentView(binding.root)
        dialog.window?.setWindowAnimations(R.style.dialog_anim)
        eventViewModel.getEvent().observe(this){
           it?.let { loadData(it) }
        }

        binding.idBtnInfoEventSubscribeMe.setOnClickListener { requireContext().showToast("Suscripción exitosa")}
        //addScrollChange()

        return dialog;
    }

    /**
     * aniadir un escucha para el scrollVew, y asi
     * poder mostrar u ocultar los items
     */
/*    private fun addScrollChange() {

        binding.idScrollViewInfoEvent.setOnScrollChangeListener(object: View.OnScrollChangeListener{
            override fun onScrollChange(p0: View?, p1: Int, p2: Int, p3: Int, p4: Int) {

                if(p2<p4){
                   showSubsCribeButton(false)
                }else
                    showSubsCribeButton(true)

            }

        })

    }*/


    /**
     * mostrar u ocultar el boton de subscripcion para
     * que no moleste el texto es muy largo
     */
/*    private fun showSubsCribeButton(show:Boolean){

        binding.idLinearInfoEvent.animate().apply {
            alpha(0f)
            duration=200
            withEndAction{
                if(show)
                    binding.idLinearInfoEvent.visibility=View.VISIBLE
                else
                    binding.idLinearInfoEvent.visibility=View.GONE
            }
        }


    }*/


    /**
     * mostar toda la info del evento seleccionado
     */
    private fun loadData(event: EventRegister) {

        Glide.with(requireContext()).load(event.image).into(binding.idImgInfoEvent)
        binding.idTxtInfoEventTitle.text=event.title
        binding.idTxtInfoEventDescrip.text=event.description
        binding.idTxtInfoEventStartDate.text="Fecha de inicio: ${event.startDate}"
        binding.idTxtInfoEventEndDate.text="Fecha de finalización: ${event.endDate}"

    }


}