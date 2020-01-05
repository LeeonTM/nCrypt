package com.n.crypt.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.n.crypt.database.dao.CredentialDao
import com.n.crypt.database.dao.PasswordDao
import com.n.crypt.database.model.Credential
import com.n.crypt.database.model.Password

@Database(entities = [Password::class, Credential::class], version = 2, exportSchema = false)
abstract class PasswordRoomDatabase : RoomDatabase() {

    abstract fun passwordDao(): PasswordDao
    abstract fun credentialDao(): CredentialDao

    companion object {
        private const val DATABASE_NAME = "PASSWORD_DATABASE"

        @Volatile
        private var instance: PasswordRoomDatabase? = null

        fun getDatabase(context: Context): PasswordRoomDatabase? {
            if (instance == null) {
                synchronized(PasswordRoomDatabase::class.java) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            PasswordRoomDatabase::class.java,
                            DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return instance
        }
    }
}

