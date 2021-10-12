package com.senasoft2021.senasoft2021.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventRegister (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int=0,

    @ColumnInfo(name = "title")
    var title:String="",

    @ColumnInfo(name = "description")
    var description:String="",

    @ColumnInfo(name = "image")
    var image:String="",

    @ColumnInfo(name = "starDate")
    var startDate:String="",

    @ColumnInfo(name = "endDate")
    var endDate:String=""


        )