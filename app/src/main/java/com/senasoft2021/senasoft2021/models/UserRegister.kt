package com.senasoft2021.senasoft2021.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserRegister(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int =0,

    @ColumnInfo(name = "name")
    var name:String="",

    @ColumnInfo(name ="phone")
    var phone:String="",

    @ColumnInfo(name ="email")
    var email:String="",

    @ColumnInfo(name ="password")
    var password:String="",

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var qrCode:ByteArray

)