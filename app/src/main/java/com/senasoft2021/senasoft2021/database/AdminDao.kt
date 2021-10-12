package com.senasoft2021.senasoft2021.database

import androidx.room.*
import com.senasoft2021.senasoft2021.models.AdminRegister

@Dao
interface AdminDao {

    @Insert
    fun insertAdmin(adminRegister: AdminRegister)

    @Update
    fun updateAdmin(adminRegister: AdminRegister)

    @Delete
    fun deleteAdmin(adminRegister: AdminRegister)

    @Query("SELECT * FROM admin")
    fun listAllAdmin():MutableList<AdminRegister>

    @Query("SELECT * FROM admin where dni = :dni")
    fun listAllAdmin(dni:String):AdminRegister?

}