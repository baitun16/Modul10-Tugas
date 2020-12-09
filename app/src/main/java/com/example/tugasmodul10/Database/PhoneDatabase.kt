package com.example.tugasmodul10.Database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [phone::class], version = 1)
abstract class PhoneDatabase : RoomDatabase() {
    //membuat dan pengelolaan database
    abstract fun phoneDao(): phoneDao
    companion object {
        private var instance: PhoneDatabase? = null
        fun getInstance(context: Context): PhoneDatabase? {
            if (instance == null) {
                synchronized(PhoneDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PhoneDatabase::class.java, "phone_database"  )
                        .fallbackToDestructiveMigration() .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }
        fun destroyInstance() {
            instance = null
        }//pemanggilan room database
        private val roomCallback = object :
            RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }
    }
//pengelolaan tugas di latar belakang diluar thread UI
    class PopulateDbAsyncTask(db: PhoneDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val noteDao = db?.phoneDao()
//untuk menjalankan komputasi latar belakang, mengembalikan hasil, dan meneruskan hasilnya
        override fun doInBackground(vararg p0: Unit?) {
            noteDao?.insert(phone("Baitun Nadhiroh", "Aku", "083117747446", "baitunnadhiroh1@gmail.com"))

        }
    }
}