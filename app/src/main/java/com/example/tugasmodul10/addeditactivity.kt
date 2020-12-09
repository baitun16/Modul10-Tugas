package com.example.tugasmodul10

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add.*


class addeditactivity : AppCompatActivity() {
    companion object{
        const val EXTRA_ID = "com.piusanggoro.notesapp.EXTRA_ID"
        const val EXTRA_JUDUL = "com.piusanggoro.notesapp.EXTRA_NAMA"
        const val EXTRA_DESKRIPSI = "com.piusanggoro.notesapp.EXTRA_DESKRIPSI"
        const val EXTRA_TELP = "com.piusanggoro.notesapp.EXTRA_TELP"
        const val EXTRA_GMAIL = "com.piusanggoro.notesapp.EXTRA_GMAIL"
    }//pengelolaan edit data
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp)
        if(intent.hasExtra(EXTRA_ID))
        {
            title = "Edit Phone"
            edit_text_nama.setText(intent.getStringExtra(EXTRA_JUDUL))
            edit_text_description.setText(intent.getStringExtra(EXTRA_DESKRIPSI))
            edit_text_notelp.setText(intent.getStringExtra(EXTRA_TELP))
            edit_text_email.setText(intent.getStringExtra(EXTRA_GMAIL))
        }
        else {
            title = "Tambah Phone"
        }
    }//pengelolaan menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
        return true
    }//pengelolaan ikon save
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId){
            R.id.save_phone -> {
                savephone()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }//untuk pengelolaan penyimpanan data
    private fun savephone() {
        if (edit_text_nama.text.toString().trim().isBlank() || edit_text_notelp.text.toString().trim().isBlank()) {
            Toast.makeText(this, "Tambahan kosong!", Toast.LENGTH_SHORT).show()
            return
        }
        val data = Intent().apply {
            putExtra(EXTRA_JUDUL, edit_text_nama.text.toString())
            putExtra(EXTRA_DESKRIPSI, edit_text_description.text.toString())
            putExtra(EXTRA_GMAIL, edit_text_email.text.toString())
            putExtra(EXTRA_TELP, edit_text_notelp.text.toString())

            if (intent.getIntExtra(EXTRA_ID, -1) != -1) {
                putExtra(EXTRA_ID, intent.getIntExtra(EXTRA_ID, -1))
            }
        }
        setResult(Activity.RESULT_OK, data)
        finish()
    }
}