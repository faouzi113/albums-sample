package com.faouzibidi.albums.mock.repository.remote

import com.faouzibidi.albums.model.Album
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import okio.Okio
import java.net.URL

/**
 * this class we are using to improve cal api performance
 * this is a constom JSON Parser which allow us
 * to read JSON response as a stream then return Sequence Object
 * that we can iterate through once we start parsing
 *
 * @see Sequence
 * @see Moshi
 * @see Okio
 *
 * @author faouzi BIDI
 */
class AlbumSequenceParser(moshi: Moshi){

    private val API_URL = "https://static.leboncoin.fr/img/shared/technical-test.json"

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
        return parse(getJsonReader(API_URL))
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
    private suspend fun getJsonReader(url:String): JsonReader{
        // create an inputstream from the URL
        val inputStream = URL(url).openStream()
        // get a bufferedSource from the inputStream
        val bufferedSource = Okio.buffer(Okio.source(inputStream))
        // create a JsonReader from bufferedSource
        return JsonReader.of(bufferedSource)
    }



}

/**
 * use an extension from JsonReader so we can use it later
 * for parsin purpose
 *
 *
 */
inline fun JsonReader.readArray(body: () -> Unit) {
    beginArray()
    while (hasNext()) {
        body()
    }
    endArray()
}