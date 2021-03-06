package com.faouzibidi.albums.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.faouzibidi.albums.R
import com.faouzibidi.albums.data.model.Album
import com.squareup.picasso.Picasso

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
class AlbumPagedListAdapter internal constructor(
    val context: Context
) : PagedListAdapter<Album, AlbumPagedListAdapter.AlbumViewHolder>(DIFF_CALLBACK) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

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
        val current = getItem(position)
        if(current!=null){
            holder.idTextView.text = current.id.toString()
            holder.titleTextView.text = current.title
            holder.urlTextView.text = current.url
            //
            loadImage(holder.image, current.imageUrl)
        }

    }

    /**
     * use Glide library to load image from Album Image url
     * to the list item image view
     */
    private fun loadImage(imageView: ImageView, url:String){
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.ic_launcher_background)
            .resize( 150, 150)
            //.centerCrop()
            .into(imageView)
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<Album>() {

            override fun areItemsTheSame(oldAlbum: Album,
                                         newAlbum: Album) = oldAlbum.id == newAlbum.id

            override fun areContentsTheSame(oldAlbum: Album,
                                            newAlbum: Album) = oldAlbum == newAlbum
        }
    }

}