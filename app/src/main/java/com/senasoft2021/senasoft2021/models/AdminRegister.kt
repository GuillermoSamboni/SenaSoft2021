package com.senasoft2021.senasoft2021.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "admin")
data class AdminRegister(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name:String="",

    @ColumnInfo(name = "dni")
    var dni:String=""

    )
