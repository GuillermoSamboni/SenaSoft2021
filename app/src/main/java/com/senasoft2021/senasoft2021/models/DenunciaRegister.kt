package com.senasoft2021.senasoft2021.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "denuncias")
data class DenunciaRegister(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "title")
    var title:String="",

    @ColumnInfo(name = "description")
    var description:String="",

    @ColumnInfo(name = "image")
    var img:String="",

    @ColumnInfo(name = "date")
    var date:String=""

    )