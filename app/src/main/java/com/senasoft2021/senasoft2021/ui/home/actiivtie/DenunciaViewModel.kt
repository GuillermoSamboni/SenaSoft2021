package com.senasoft2021.senasoft2021.ui.home.actiivtie

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.senasoft2021.senasoft2021.database.RoomDatabaseClient
import com.senasoft2021.senasoft2021.models.DenunciaRegister

/**
 * viewmodel para las denuncias
 */
class DenunciaViewModel: ViewModel() {

    private val _denuncia=MutableLiveData<DenunciaRegister>()

    private val denuncias=MutableLiveData<MutableList<DenunciaRegister>>()

    /**
     * obtener la denuncia seleccionada
     */
    fun getDenuncia()=_denuncia

    /**
     * seleccionar una denuncia
     */
    fun setDenuncia(denunciaRegister: DenunciaRegister){
        _denuncia.value=denunciaRegister
    }

    /**
     * lisater todas las denuncias registradas
     */
    fun getDenuncias(context: Context): MutableLiveData<MutableList<DenunciaRegister>> {
        denuncias.value=RoomDatabaseClient.listAllDenuncias(context)
        return denuncias
    }


}