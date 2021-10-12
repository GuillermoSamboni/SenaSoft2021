package com.senasoft2021.senasoft2021.database

import android.content.Context
import android.content.SharedPreferences
import com.senasoft2021.senasoft2021.R
import com.senasoft2021.senasoft2021.models.UserRegister

/**
 * Realizar todas las operacioines correspondiantes a las sharedPref
 */
class SharedPreferencesClient {


    companion object{

        const val KEY_ID_USER="ID_USER" //key para guardar y obtener el id del usuario en las sharedPref

        /**
         * obtener una instancia de las sharedPreferences
         * utilizando el nombre de la app
         */
        fun getSharedPreferences(conntext:Context): SharedPreferences {
            val name=conntext.getString(R.string.app_name)
            return conntext.getSharedPreferences(name,Context.MODE_PRIVATE)
        }

        /**
         * guardar el id del usuario para luego obtenerlo
         * y asi poder mantener su session activa
         */
        fun setSessionUser(userRegister: UserRegister,conntext: Context){
            val sharedPreferences= getSharedPreferences(conntext)
            sharedPreferences.edit().putInt(KEY_ID_USER,userRegister.id).apply()
        }

    }//end comp

}