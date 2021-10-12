package com.senasoft2021.senasoft2021.extension_function

import android.content.Context
import android.widget.Toast


/**
 * imprimir un toast de una manera simplificada
 */
fun Context.showToast(message:String){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}
