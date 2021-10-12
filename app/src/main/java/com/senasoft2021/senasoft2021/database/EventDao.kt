package com.senasoft2021.senasoft2021.database

import androidx.room.*
import com.senasoft2021.senasoft2021.models.EventRegister

@Dao
interface EventDao {

    @Insert
    fun insertEvent(eventRegister: EventRegister)

    @Update
    fun updateEvent(eventRegister: EventRegister)

    @Delete
    fun deleteEvent(eventRegister: EventRegister)

    @Query("SELECT * FROM events")
    fun listAllEvents():MutableList<EventRegister>

}