package com.example.tetsappforsmartapps.domain.entity


data class Country(
    var id: Int = 0,
    val name: Name
    )

data class Name(
    val common: String
)
