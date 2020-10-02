package com.faouzibidi.albums.data.repository.remote

import android.content.Context
import com.faouzibidi.albums.test.R
import com.faouzibidi.albums.data.model.Album
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okio.Okio

/**
 * this class is used for testing purpose to simulate calling the
 * rest api by reading a local json file stream used as mock
 * instead of calling the real api
 *
 * @see Sequence
 * @see Moshi
 * @see Okio
 *
 * @author faouzi BIDI
 */
class AlbumSequenceParser(val context : Context){

    /*
     * building a moshi adapter for kotlin compatibility
     */
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val FILE_RES_ID = R.raw.test

    private val albumAdapter: JsonAdapter<Album> = moshi.adapter(Album::class.java)


    /**
     * public method for getting Albums from
     * the rest API
     *
     * this method calls API url to get a JsonRedaer
     * then it parse it using our custom parser
     * to return finally the Sequence of Albums
     *
     * @see Sequence
     *
     */
    suspend fun getAlbums(): Sequence<Album>{
        return parse(getJsonReader(FILE_RES_ID))
    }

    /**
     * create a sequence object from a JsonReader
     */
    private suspend fun parse(reader: JsonReader): Sequence<Album> {
        return sequence {
            reader.readArray {
                yield(albumAdapter.fromJson(reader)!!)
            }
        }
    }

    /**
     * create a JSONReader object which we can use for parse method
     */
    private suspend fun getJsonReader(resourceId:Int): JsonReader{
        // create an inputstream from the URL
        val inputStream = context.getResources().openRawResource(resourceId)
        // get a bufferedSource from the inputStream
        val bufferedSource = Okio.buffer(Okio.source(inputStream))
        // create a JsonReader from bufferedSource
        return JsonReader.of(bufferedSource)
    }



}