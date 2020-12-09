package com.example.tugasmodul10.Database

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData


class phoneRepository(application: Application) {
    private var phoneDao: phoneDao
    private var allphones: LiveData<List<phone>>

    init {
        val database: PhoneDatabase = PhoneDatabase.getInstance(
            application.applicationContext
        )!!
        phoneDao = database.phoneDao()
        allphones = phoneDao.getAllNotes()
    }//pengelolaan operasi insert
    fun insert(phone: phone) {
        val insertNoteAsyncTask = InsertNoteAsyncTask(phoneDao).execute(phone)
    }//pengelolaan operasi update
    fun update(phone: phone) {
        val updateNoteAsyncTask = UpdateNoteAsyncTask(phoneDao).execute(phone)
    }//pengelolaan operasi delete
    fun delete(phone: phone) {
        val deleteNoteAsyncTask = DeletephoneAsyncTask(phoneDao).execute(phone)
    }// pengelolaan operasi delete all
    fun deleteAllphones() {
        val deleteAllphonesAsyncTask = deleteAllphonesAsyncTask(
            phoneDao         ).execute()
    }// pengelolaan pengambilan data
    fun getAllphones(): LiveData<List<phone>> {
        return allphones
    }
    companion object {//untuk proses operasi pengambilan yang dilakukan secara background
        private class InsertNoteAsyncTask(phoneDao: phoneDao) : AsyncTask<phone, Unit, Unit>() {
            val phoneDao = phoneDao
            override fun doInBackground(vararg p0: phone?) {
                phoneDao.insert(p0[0]!!)
            }
        }
        private class UpdateNoteAsyncTask(phoneDao: phoneDao) : AsyncTask<phone, Unit, Unit>() {
            val phoneDao = phoneDao

            override fun doInBackground(vararg p0: phone?) {
                phoneDao.update(p0[0]!!)
            }
        }
        private class DeletephoneAsyncTask(phoneDao: phoneDao) : AsyncTask<phone, Unit, Unit>() {
            val phoneDao = phoneDao
            override fun doInBackground(vararg p0: phone?) {
                phoneDao.delete(p0[0]!!)
            }
        }
        private class deleteAllphonesAsyncTask(phoneDao: phoneDao) : AsyncTask<Unit, Unit, Unit>() {
            val phoneDao = phoneDao

            override fun doInBackground(vararg p0: Unit?) {
                phoneDao.deleteAllNotes()
            }
        }
    }
}