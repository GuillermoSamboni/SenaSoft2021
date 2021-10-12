package com.senasoft2021.senasoft2021.ui.login.admin

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.senasoft2021.senasoft2021.database.RoomDatabaseClient
import com.senasoft2021.senasoft2021.models.EventRegister

class EventsViewModel: ViewModel() {

    private val _event=MutableLiveData<EventRegister>()
    private val _events=MutableLiveData<MutableList<EventRegister>>()


    /**
     * setear el evento al campo _event
     * para notificarle a los que escuchan a este
     */
    fun setEvent(eventRegister: EventRegister){
        _event.value=eventRegister
    }

    /**
     * obtener un liveData de un evento,
     * para asi escuchar cuando se cambie de evento
     */
    fun getEvent()=_event

    /**
     * obtener un livedata con la lista de todos los eventos,
     * para escuchar sus cambios
     */
    fun getEvents(context: Context): MutableLiveData<MutableList<EventRegister>> {
        _events.value=RoomDatabaseClient.listAllEvents(context)
        return _events
    }


}