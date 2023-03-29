package com.example.tetsappforsmartapps.data.database

import androidx.room.TypeConverter
import com.example.tetsappforsmartapps.domain.entity.Name


class TypeConverter {
    @TypeConverter
    fun fromNameToString(name: Name): String {
        return name.common
    }

    @TypeConverter
    fun fromStringToName(name: String): Name {
        return Name(common = name)
    }
}