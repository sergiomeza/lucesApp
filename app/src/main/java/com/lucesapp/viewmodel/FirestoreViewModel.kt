package com.lucesapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.lucesapp.model.Product
import com.lucesapp.model.Sale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

class FirestoreViewModel(app: Application) : AndroidViewModel(app), CoroutineScope {
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val PRODUCT_COLLECTION = "lucesProducts"
    private val SALES_COLLECTION = "lucesSales"

    private var _products = MutableLiveData(ArrayList<Product>())
    val products: LiveData<ArrayList<Product>> = _products

    private var _sales = MutableLiveData(ArrayList<Sale>())
    val sales: LiveData<ArrayList<Sale>> = _sales

    private var _showLoading = MutableLiveData(false)
    val showLoading: LiveData<Boolean> = _showLoading

    private var _error = MutableLiveData("")
    val error: LiveData<String> = _error

    private var _dataAdded = MutableLiveData("")
    val dataAdded: LiveData<String> = _dataAdded

    private val db = FirebaseFirestore.getInstance()
    private val source = Source.DEFAULT

    fun getProducts(){
        _showLoading.postValue(true)
        val docRef = db.collection(PRODUCT_COLLECTION)
        docRef.addSnapshotListener { snapshot, e ->
            if (snapshot != null){
                val itemList = ArrayList<Product>()
                for (document in snapshot) {
                    val product = document.toObject(Product::class.java)
                    product.id = document.id
                    itemList.add(product)
                }
                _showLoading.postValue(false)
                _products.postValue(itemList)
            }
        }
    }

    fun getSales() {
        _showLoading.postValue(true)
        val docRef = db.collection(SALES_COLLECTION)
        val itemList = ArrayList<Sale>()
        docRef.get(source).addOnSuccessListener { result ->
            for (document in result) {
                val sale = document.toObject(Sale::class.java)
                sale.id = document.id
                itemList.add(sale)
            }
            _showLoading.postValue(false)
            _sales.postValue(itemList)
        }
    }

    fun addProduct(product: Product) {
        _showLoading.postValue(true)
        val docRef = db.collection(PRODUCT_COLLECTION)
        docRef
            .add(product)
            .addOnSuccessListener { documentReference ->
                _error.postValue("")
                _dataAdded.postValue(documentReference.id)
                _showLoading.postValue(false)
            }
            .addOnFailureListener { e ->
                _error.postValue(e.localizedMessage)
                _showLoading.postValue(false)
                _dataAdded.postValue("")
            }
    }

    fun addSale(sale: Sale) {
        _showLoading.postValue(true)
        val docRef = db.collection(SALES_COLLECTION)
        docRef
            .add(sale)
            .addOnSuccessListener { documentReference ->
                _error.postValue("")
                _dataAdded.postValue(documentReference.id)
                _showLoading.postValue(false)
            }
            .addOnFailureListener { e ->
                _dataAdded.postValue("")
                _error.postValue(e.localizedMessage)
                _showLoading.postValue(false)
            }
    }

    fun clearDataAdded(){
        _dataAdded.postValue("")
    }
}