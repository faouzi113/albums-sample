package com.faouzibidi.albums.repository.remote

import com.faouzibidi.albums.model.Album
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://static.leboncoin.fr/"

/*
 * building a moshi adapter for kotlin compatibility
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/*
 * building a retrofit object using the moshi adapter
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()
/**
 * interface for exposing Albums webservice calls
 */
interface AlbumApiService {

    /**
     * get albums from the https://static.leboncoin.fr/img/shared/technical-test.json webservice
     *
     * @return a call of a list of albums : Call<List<Album>>
     *
     * @see Call
     *
     */
    @GET("img/shared/technical-test.json")
    suspend fun getAlbums() : List<Album>
}

/**
 * public object retrofitService
 * which will be used outside to get Albums from a network request
 */
object AlbumApi {
    // TODO("delete retrofitService")
    val retrofitService : AlbumApiService by lazy {
        retrofit.create(AlbumApiService::class.java) }

    val albumParser : AlbumSequenceParser by lazy {
        AlbumSequenceParser(moshi) }
}