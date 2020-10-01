package com.faouzibidi.albums

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faouzibidi.albums.ui.AlbumViewModel
import com.faouzibidi.albums.ui.adapters.AlbumPagedListAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var loader: ProgressBar
    private lateinit var adapter: AlbumPagedListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize views
        initViews()

        // init and observe on viewmodel
        val albumViewModel = ViewModelProvider(this).get(AlbumViewModel::class.java)
        val start = System.currentTimeMillis()
        var i = 0
        albumViewModel.getAlbumsPagedList().observe(this, Observer { albums ->
            if(albums != null){
                // hide loader
                loader.visibility = View.GONE
                adapter.submitList(albums)
                val end = System.currentTimeMillis()
                Log.d("perf", "elapsedTime $i : ${end-start}")
            }else{
                val end = System.currentTimeMillis()
                Log.d("perf", "first call elapsedTime : ${end-start}")
            }
            i++

        })

        // call loading method
        albumViewModel.loadAlbums()
    }

    /**
     * internal method for creating views
     */
    private fun initViews(){
        // set recycler view adapter
        adapter = AlbumPagedListAdapter(this)
        val recyclerView = findViewById<RecyclerView>(R.id.album_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        // ProgressBar loader
        loader = findViewById(R.id.loader)
    }
}