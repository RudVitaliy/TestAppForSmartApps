package com.example.tetsappforsmartapps.data.database

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@androidx.room.Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCountryName(country: CountryDbModel)

    @Query("SELECT * FROM counties_list ORDER BY id LIMIT 1")
    fun getFirstCountry(): LiveData<List<CountryDbModel>>

}