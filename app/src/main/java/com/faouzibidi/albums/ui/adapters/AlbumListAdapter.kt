package com.faouzibidi.albums.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.faouzibidi.albums.R
import com.faouzibidi.albums.model.Album

/**
 *
 * Adapter class to hold and handle Album data
 *
 *
 * @see RecyclerView.Adapter
 * @see RecyclerView.ViewHolder
 *
 *
 * @author faouzi BIDI
 */
class AlbumListAdapter internal constructor(
    val context: Context
) : RecyclerView.Adapter<AlbumListAdapter.AlbumViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var albums = emptyList<Album>()

    inner class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView: TextView = itemView.findViewById(R.id.album_id)
        val titleTextView: TextView = itemView.findViewById(R.id.album_title)
        val urlTextView: TextView = itemView.findViewById(R.id.album_url)
        val image: AppCompatImageView = itemView.findViewById(R.id.album_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val itemView = inflater.inflate(R.layout.album_list_item, parent, false)
        return AlbumViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val current = albums[position]
        holder.idTextView.text = current.albumId.toString()
        holder.titleTextView.text = current.title
        holder.urlTextView.text = current.url
        //
        loadImage(holder.image, current.imageUrl)
    }

    /**
     * use Glide library to load image from Album Image url
     * to the list item image view
     */
    private fun loadImage(imageView: ImageView, url:String){
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.ic_launcher_background)
            //TODO("check which size to use")
            //.override(200, 200)
            .centerCrop()
            .into(imageView)


    }

    internal fun setAlbums(albumsList: List<Album>) {
        this.albums = albumsList
        notifyDataSetChanged()
    }

    override fun getItemCount() = albums.size
}