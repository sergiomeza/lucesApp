package com.lucesapp.model

import java.io.Serializable

data class Sale(var id: String, val productId: String,
                val quantity: Int, val created: String) : Serializable
