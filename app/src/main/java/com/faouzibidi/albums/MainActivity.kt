package com.faouzibidi.albums

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faouzibidi.albums.ui.AlbumViewModel
import com.faouzibidi.albums.ui.adapters.AlbumListAdapter
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // set recycler view adapter
        val recyclerView = findViewById<RecyclerView>(R.id.album_recycler_view)
        val adapter = AlbumListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // set and observe Album ViewModel
        val albumViewModel = ViewModelProvider(this).get(AlbumViewModel::class.java)
        albumViewModel.getAlbums().observe(this, Observer { albums ->
            // set album list
            albums?.let {
                adapter.setAlbums(it)
            }
        })

        // load albums
        albumViewModel.loadAlbums()
    }
}