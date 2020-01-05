package com.n.crypt.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Credential")
data class Credential (
    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "passwordHash")
    val passwordHash: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
) : Parcelable