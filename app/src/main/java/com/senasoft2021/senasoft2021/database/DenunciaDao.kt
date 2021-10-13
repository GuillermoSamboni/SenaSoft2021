package com.senasoft2021.senasoft2021.database

import androidx.room.*
import com.senasoft2021.senasoft2021.models.DenunciaRegister

@Dao
interface DenunciaDao {

    @Insert
    fun insertDenuncia(denunciaRegister: DenunciaRegister)

    @Update
    fun updateDenuncia(denunciaRegister: DenunciaRegister)

    @Delete
    fun deleteDenuncia(denunciaRegister: DenunciaRegister)

    @Query("SELECT * FROM denuncias")
    fun listAllDenuncias():MutableList<DenunciaRegister>

}

