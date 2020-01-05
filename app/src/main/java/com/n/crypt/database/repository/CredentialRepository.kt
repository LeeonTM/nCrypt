package com.n.crypt.database.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.n.crypt.database.dao.CredentialDao
import com.n.crypt.database.model.Credential
import com.n.crypt.database.room.PasswordRoomDatabase

class CredentialRepository(context: Context) {

    private val credentialDao: CredentialDao

    init {
        val database = PasswordRoomDatabase.getDatabase(context)
        credentialDao = database!!.credentialDao()
    }

    fun getCredentials(): LiveData<List<Credential>> = credentialDao.getAllCredentials()

    suspend fun insertCredential(credential: Credential) = credentialDao.insertCredential(credential)

    suspend fun deleteAllCredentials() = credentialDao.deleteAllCredentials()
}