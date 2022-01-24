package com.example.shopcourse.room

import androidx.lifecycle.LiveData

class CatalogRepository(private var useDao: UseDao){

    val readAllDate : LiveData<List<Catalog>> = useDao.readProduct()

    suspend fun addCatalog (catalog: Catalog) {
        useDao.addProduct(catalog)
    }



}