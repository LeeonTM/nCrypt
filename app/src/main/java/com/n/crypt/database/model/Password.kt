package com.n.crypt.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Password")
data class Password (
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "passwordHash")
    val passwordHash: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
) : Parcelable