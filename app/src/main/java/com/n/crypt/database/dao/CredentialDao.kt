package com.n.crypt.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.n.crypt.database.model.Credential

@Dao
interface CredentialDao {
    /**
     * Returns a [List] of [Credential] from local storage
     */
    @Query("SELECT * FROM Credential")
    fun getAllCredentials(): LiveData<List<Credential>>

    /**
     * Insert [Credential] into local storage
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCredential(credential: Credential)

    /**
     * Deletes every single [Credential] from local storage
     */
    @Query("DELETE FROM Credential")
    suspend fun deleteAllCredentials()
}