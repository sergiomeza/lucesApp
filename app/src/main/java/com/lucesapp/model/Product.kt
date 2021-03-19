package com.lucesapp.model

import java.io.Serializable

data class Product(var id: String? = null, val name: String? = null,
                   val retailPrice: Long? = null, val price: Long? = null,
                   val image: String? = "https://firebasestorage.googleapis.com/v0/b/sergiomeza-e5bfa.appspot.com/o/LucesVerdes%2Fsoap_1.jpg?alt=media&token=a28af1c3-13ec-4aa6-b19f-80ab2606b5e3",
                   val description: String? = null) : Serializable
