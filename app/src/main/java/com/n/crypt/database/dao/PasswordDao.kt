package com.n.crypt.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.ABORT
import com.n.crypt.database.model.Password

@Dao
interface PasswordDao {

    /**
     * Returns a [List] of [Password] from local storage
     */
    @Query("SELECT * FROM Password")
    fun getAllPasswords(): LiveData<List<Password>>

    /**
     * Insert [Password] into local storage
     */
    @Insert(onConflict = ABORT)
    suspend fun insertPassword(password: Password)

    /**
     * Update [Password] in local storage. Only changes to it's name are permitted
     */
    @Update
    suspend fun updatePassword(password: Password)

    /**
     * Delete given [Password] from local storage
     */
    @Delete
    suspend fun deletePassword(password: Password)

    /**
     * Deletes every single [Password] from local storage
     */
    @Query("DELETE FROM Password")
    suspend fun deleteAllPasswords()
}