package com.example.tugasmodul10

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.tugasmodul10.Database.phone
import com.example.tugasmodul10.Database.phoneRepository

class phoneViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: phoneRepository = phoneRepository(application)
    private var allphones: LiveData<List<phone>> = repository.getAllphones()
    fun insert(phone: phone) {//komunikasi data penyimpanan antara repository dengan UI
        repository.insert(phone)
    }
    fun update(phone: phone) {//komunikasi pembaharuan data antara repository dengan UI
        repository.update(phone)
    }
    fun delete(phone: phone) {//komunikasi data penghapusan antara repository dengan UI
        repository.delete(phone)
    }
    fun deleteAllphones() {//komunikasi data penghapusan global antara repository dengan UI
        repository.deleteAllphones()
    }
    fun getAllphones(): LiveData<List<phone>> {//pengelolaan atau pemanggilan data yang dimiliki
        return allphones
    }
}