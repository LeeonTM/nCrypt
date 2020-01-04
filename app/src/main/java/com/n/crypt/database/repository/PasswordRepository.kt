package com.n.crypt.database.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.n.crypt.database.dao.PasswordDao
import com.n.crypt.database.model.Password
import com.n.crypt.database.room.PasswordRoomDatabase

class PasswordRepository(context: Context) {

    private val passwordDao: PasswordDao

    init {
        val database = PasswordRoomDatabase.getDatabase(context)
        passwordDao = database!!.passwordDao()
    }

    fun getPasswords(): LiveData<List<Password>> = passwordDao.getAllPasswords()

    suspend fun insertPassword(password: Password) = passwordDao.insertPassword(password)

    suspend fun updatePassword(password: Password) = passwordDao.updatePassword(password)

    suspend fun deletePassword(password: Password) = passwordDao.deletePassword(password)

    suspend fun deleteAllPasswords() = passwordDao.deleteAllPasswords()
}
