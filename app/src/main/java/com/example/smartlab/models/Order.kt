package com.example.smartlab.models

data class Order(
    val id: Int,
    val address: String,
    val dataTimeOder: String,
    val phone: String,
    val comment: String,
    val catalogs: List<Catalog>
)