package com.example.tetsappforsmartapps.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tetsappforsmartapps.domain.entity.Name

@Entity(tableName = "counties_list")
data class CountryDbModel (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "name")
    val name: Name
)