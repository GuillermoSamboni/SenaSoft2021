package com.senasoft2021.senasoft2021.database

import android.content.Context
import android.content.SharedPreferences
import com.senasoft2021.senasoft2021.R
import com.senasoft2021.senasoft2021.models.UserRegister

/**
 * Realizar todas las operacioines correspondiantes a las sharedPref
 */
class SharedPreferencesClient {


    companion object {

        const val KEY_ID_USER =
            "ID_USER" //key para guardar y obtener el id del usuario en las sharedPref

        /**
         * key para guardar y obtener el boolean que indica si es la primera vez que se accede a este campo
         * --
         */
        const val KEY_FIRST_TIME_IN_COMPETENCIAS = "FIRST_TIME_COMPE"
        const val KEY_FIRST_TIME_IN_HOME = "FIRST_TIME_HOME"
        const val KEY_FIRST_TIME_IN_HELP = "FIRST_TIME_HELP"
        const val KEY_FIRST_TIME_IN_DASBOARD = "FIRST_TIME_DASHBOARD"
        /**
         * ---
         */


        /**
         * obtener una instancia de las sharedPreferences
         * utilizando el nombre de la app
         */
        fun getSharedPreferences(conntext: Context): SharedPreferences {
            val name = conntext.getString(R.string.app_name)
            return conntext.getSharedPreferences(name, Context.MODE_PRIVATE)
        }

        /**
         * guardar el id del usuario para luego obtenerlo
         * y asi poder mantener su session activa
         */
        fun setSessionUser(userRegister: UserRegister, conntext: Context) {
            val sharedPreferences = getSharedPreferences(conntext)
            sharedPreferences.edit().putInt(KEY_ID_USER, userRegister.id).apply()
        }


        /**
         * hacer una accion cuando sea la primare vez que se ingresa al apartado de competencias
         * @return false si es laprimera vez en acceder - true en caso contrario
         */
        fun firstTimeInCompetencias(context: Context): Boolean {
            val shared = getSharedPreferences(context)
            val firstTime = shared.getBoolean(KEY_FIRST_TIME_IN_COMPETENCIAS, false)
            if (!firstTime)
                shared.edit().putBoolean(KEY_FIRST_TIME_IN_COMPETENCIAS, true).apply()

            return firstTime
        }

        /**
         * hacer una accion cuando sea la primare vez que se ingresa al apartado de home
         * @return false si es laprimera vez en acceder - true en caso contrario
         */
        fun firstTimeInHome(context: Context): Boolean {
            val shared = getSharedPreferences(context)
            val firstTime = shared.getBoolean(KEY_FIRST_TIME_IN_HOME,false)
            if (!firstTime)
                shared.edit().putBoolean(KEY_FIRST_TIME_IN_HOME, true).apply()

            return firstTime
        }


        /**
         * hacer una accion cuando sea la primare vez que se ingresa al apartado de dashboard
         * @return false si es laprimera vez en acceder - true en caso contrario
         */
        fun firstTimeInDashboard(context: Context): Boolean {
            val shared = getSharedPreferences(context)
            val firstTime = shared.getBoolean(KEY_FIRST_TIME_IN_DASBOARD,false)
            if (!firstTime)
                shared.edit().putBoolean(KEY_FIRST_TIME_IN_DASBOARD, true).apply()
            return firstTime
        }

    }//end comp

}