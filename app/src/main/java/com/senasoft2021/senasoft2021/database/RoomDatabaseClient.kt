package com.senasoft2021.senasoft2021.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.senasoft2021.senasoft2021.models.AdminRegister
import com.senasoft2021.senasoft2021.models.DenunciaRegister
import com.senasoft2021.senasoft2021.models.EventRegister
import com.senasoft2021.senasoft2021.models.UserRegister
import kotlinx.coroutines.runBlocking

@Database(version = 1, entities = [UserRegister::class, AdminRegister::class, EventRegister::class, DenunciaRegister::class])
abstract class RoomDatabaseClient : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun adminDao(): AdminDao
    abstract fun  eventDao():EventDao
    abstract fun denunciaDao():DenunciaDao

    companion object {

        private const val DATABASE_NAME = "senasoft"

        /**
         * obtener una instancia de la base de datos
         */
        fun getInstance(context: Context) = Room.databaseBuilder(
            context, RoomDatabaseClient::class.java,
            DATABASE_NAME
        ).allowMainThreadQueries().build()


        //Operaciones para los usuarios----------------------------------
        /**
         * registrar un nuevo usuario en al bd
         */
        fun registerUser(context: Context, userRegister: UserRegister): Boolean {
            var retorno = false
            val bd = getInstance(context).userDao()
            runBlocking {
                bd.insertUser(userRegister)
                SharedPreferencesClient.setSessionUser(userRegister, context)
                retorno = true
            }
            return retorno
        }

        /**
         * obtener todos los usuarios registrados en la bd
         */
        fun listAllUsers(context: Context) = getInstance(context).userDao().listAllUsers()


        /**
         * validar si el email ingresado ya esta registrado en otra cuenta
         * @return true si el email ya esta registrado - false en caso contrario
         */
        fun validarEmailUsuario(email: String, context: Context): Boolean {
            var retorno = false
            val bd = getInstance(context).userDao()
            runBlocking {
                val user = bd.selectUserByEmail(email)
                if (user != null)
                    retorno = true
            }

            return retorno
        }

        /**
         * validar si el nombre ingresado ya esta registrado en otra cuenta
         * @return true si el email ya esta registrado - false en caso contrario
         */
        fun validarNombreUsuario(name: String, context: Context): Boolean {
            var retorno = false
            var bd = getInstance(context).userDao()
            runBlocking {
                val user = bd.selectUserByName(name)
                if (user != null)
                    retorno = true
            }

            return retorno
        }

        /**
         * iniciar con una cuenta de usuario perviamente registada
         */
        fun loginUSer(name: String, pass: String, context: Context): Boolean {
            var retorno = false
            val bd = getInstance(context).userDao()
            val list = bd.listAllUsers()
            runBlocking {

                for (user in list)
                    if (user.name == name && user.password == pass)
                        retorno = true
            }
            return retorno
        }


        /**
         * obtener la instancia del ultimo usuario logeado en la app
         */
        fun getCurrentUser(context: Context): UserRegister? {
            val bd = getInstance(context).userDao()
            val shared = SharedPreferencesClient.getSharedPreferences(context)
            var restorno = false

            val id = shared.getInt(SharedPreferencesClient.KEY_ID_USER, -1)
            return bd.selectUserById(id)
        }


        //operaciones para los admin---------------------
        /**
         * loguear un admin con una cuanta previamente registrado
         */
        fun loginAdmin(name:String, pass:String, context: Context): Boolean {
            val bd= getInstance(context).adminDao()
            var retorno=false

            runBlocking {
                val admin=bd.selectAdminByDni(pass)
                admin?.let {
                    if((name == pass) && name == it.dni)
                        retorno = true
                }
            }

            return retorno


        }


        //operaciones para los eventos----------------------------

        /**
         * registrar un nuevo evento en la bd
         */
        fun registerEvent(eventRegister: EventRegister,context: Context):Boolean{
            val bd= getInstance(context).eventDao()
            var retorno = false
            runBlocking {
                bd.insertEvent(eventRegister)
                retorno = true
            }
            return retorno
        }

        /**
         * listar todos los eventos registrados
         */
        fun listAllEvents(context: Context) = getInstance(context).eventDao().listAllEvents()


        //Operaciones para las denuncias---------------------------------------

        /**
         * registrar una nueva denuncia en la base de datos
         *
         */
        fun insertDenuncia(denunciaRegister: DenunciaRegister, context: Context):Boolean{

            var retorno=false
            val bd= getInstance(context).denunciaDao()
            runBlocking {
                bd.insertDenuncia(denunciaRegister)
                retorno = true
            }

            return retorno
        }

        /**
         * listar todas las denuncias almacenadas en la bd
         */
        fun listAllDenuncias(context: Context)= getInstance(context).denunciaDao().listAllDenuncias()



    }//end Comp

}