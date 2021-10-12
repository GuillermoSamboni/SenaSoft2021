package com.senasoft2021.senasoft2021.database

import androidx.room.*
import com.senasoft2021.senasoft2021.models.UserRegister

@Dao
interface UserDao {

    @Insert
    fun insertUser(userRegister: UserRegister)

    @Delete
    fun deleteUser(userRegister: UserRegister)

    @Update
    fun updateUser(userRegister: UserRegister)

    @Query("SELECT * FROM user")
    fun listAllUsers():MutableList<UserRegister>

    @Query("Select * FROM USER WHERE name = :name")
    fun selectUserByName(name:String):UserRegister?

    @Query("Select * FROM USER WHERE email = :email")
    fun selectUserByEmail(email:String):UserRegister?

    @Query("Select * FROM USER WHERE id = :id")
    fun selectUserById(id: Int): UserRegister?

}