package com.lucesapp.model

import java.io.Serializable

data class Sale(var id: String? = null, val product: Product? = null,
                val quantity: Int? = null, val created: String? = null) : Serializable
