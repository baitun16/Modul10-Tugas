package com.example.tugasmodul10

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugasmodul10.Database.phone
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var phoneViewModel: phoneViewModel
    companion object {
        const val ADD_REQUEST = 1
        const val EDIT_REQUEST = 2
    }
//pengelolaan tampilan awal aplikasi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonAdd.setOnClickListener{
            startActivityForResult(
                Intent(this, addeditactivity::class.java),
                ADD_REQUEST
            )
        }
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        val adapter = phoneAdapter()
        recycler_view.adapter = adapter

        phoneViewModel = ViewModelProviders.of(this).get(com.example.tugasmodul10.phoneViewModel::class.java)
        phoneViewModel.getAllphones().observe(this, Observer<List<phone>> {
            adapter.submitList(it)
        })

        ItemTouchHelper(
            object :ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT).or(ItemTouchHelper.DOWN)) {

                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    //  cardview.setCardBackgroundColor(Color.parseColor("Red"))
                    AlertDialog.Builder(viewHolder.itemView.getContext())
                        // Judul

                        .setTitle("Warning")
                        // Pesan yang di tampilkan
                        .setMessage("Ingin Dihapus ?")
                        .setPositiveButton("Ya", DialogInterface.OnClickListener(){ dialogInterface, i ->
                            phoneViewModel.delete(adapter.getphoneAt(viewHolder.adapterPosition))
                            Toast.makeText(baseContext, "Phone dihapus!", Toast.LENGTH_SHORT).show()
                        })
                        .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialogInterface, i ->
                            Toast.makeText(baseContext, "Phone Tidak Terhapus", Toast.LENGTH_LONG).show()
                            adapter.notifyItemChanged(viewHolder.adapterPosition)
                        })
                        .show()
                }
            }
        ).attachToRecyclerView(recycler_view)
        adapter.setOnItemClickListener(object : phoneAdapter.OnItemClickListener {
            override fun onItemClick(phone: phone) {//pengelolaan click item
                val intent = Intent(baseContext, addeditactivity::class.java)
                intent.putExtra(addeditactivity.EXTRA_ID, phone.id)
                intent.putExtra(addeditactivity.EXTRA_JUDUL, phone.Nama)
                intent.putExtra(addeditactivity.EXTRA_DESKRIPSI, phone.description)
                intent.putExtra(addeditactivity.EXTRA_GMAIL, phone.email)
                intent.putExtra(addeditactivity.EXTRA_TELP, phone.Notelp)

                startActivityForResult(intent, EDIT_REQUEST)
            }
        })
    }//pengelolaan menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true     }
    //pengelolaan menu item delete all
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            R.id.delete_all_notes -> {
                phoneViewModel.deleteAllphones()
                Toast.makeText(this, "Semua sudah dihapus!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }//untuk pengelolaan hasil aktivitas addphone
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_REQUEST && resultCode == Activity.RESULT_OK) {
            val newphone = phone(
                data!!.getStringExtra(addeditactivity.EXTRA_JUDUL),data.getStringExtra(addeditactivity.EXTRA_DESKRIPSI),
                data.getStringExtra(addeditactivity.EXTRA_TELP), data.getStringExtra(addeditactivity.EXTRA_GMAIL))
            phoneViewModel.insert(newphone)
            Toast.makeText(this, "Phone Book disimpan!", Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(addeditactivity.EXTRA_ID, -1)

            if (id == -1) {
                Toast.makeText(this, "Pembaharuan gagal!", Toast.LENGTH_SHORT).show()
            }

            val updatephone = phone(
                data!!.getStringExtra(addeditactivity.EXTRA_JUDUL),
                data.getStringExtra(addeditactivity.EXTRA_DESKRIPSI),
                data.getStringExtra(addeditactivity.EXTRA_TELP),
                data.getStringExtra(addeditactivity.EXTRA_GMAIL)
            )
            updatephone.id = data.getIntExtra(addeditactivity.EXTRA_ID, -1)
            phoneViewModel.update(updatephone)
        } else {
            Toast.makeText(this, "Phone Book tidak disimpan!", Toast.LENGTH_SHORT).show()
        }
    }

}