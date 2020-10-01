package com.faouzibidi.albums.performance

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.faouzibidi.albums.repository.remote.AlbumRemoteRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith

/**
* this class is used for testing api calls performances
 *
 * for this tests we use the real repository to
 * get real data and calculate the real behaviour
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class RemoteRepositoryPerformanceTest {

    private lateinit var repository : AlbumRemoteRepository

    @Before
    fun initialize(){
        repository = AlbumRemoteRepository()
        // force the call of gc to freeup memory
        System.gc()
    }
    /**
     * in this test we will call the api and calculate
     * used memory and elapsed time
     *
     * Results :
        I/System.out: Elapsed time : 10209
        I/System.out: Elapsed memory : 8455456
        I/System.out: Items : 5000
     *
     * this results is for calling the entire json file
     * we try in next tests to download the file as a stream
     *
     * @Deprecated it was replaced by Sequence call
     * but we keep just info of measurements
     */
    @Test
    fun computeRetrofitCallPerformance() = runBlocking {
        // donothing
    }


    /**
     * in this test we call first the new API using
     * AlbumSequenceParser and we convert Sequence to a list
     * so we get all data
     *
     *
     *  Results :
        I/System.out: Elapsed time : 8664
        I/System.out: Elapsed memory : 8012512
        I/System.out: Items : 5000

        we gained : 1545 ms in execution time
                    442944 Byte in execution memory
        this values can change but after few tests we can say that this solution
        is more efficient than the first one using retrofit API

        but the most interesting thing about this solution is
        get a sequence object so we can fetch data partially
        store them in db and continue storing process while we can show
        first data to the user instead for waiting to get all Albums
     */
    @Test
    fun computeParserCallPerformance() = runBlocking {

        val startTime = System.currentTimeMillis()
        val startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
        // call api
        val albums = repository.getAlbums().toList()

        //
        System.gc()
        val finishTime = System.currentTimeMillis()
        val finishMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
        //
        val elapsedMemory = finishMemory - startMemory
        val elapsedTime = finishTime - startTime
        //
        println("Elapsed time : $elapsedTime")
        println("Elapsed memory : $elapsedMemory")
        println("Items : ${albums.size}")

    }

    /**
     * in this test we will test a sequential parsing of data
     * instead of using Sequence#toList method that we have already used
     * in previous test
     *
     *
        I/System.out: Elapsed time : 7900
        I/System.out: Elapsed memory : 3736288

            we gained : 2309 ms in execution time
                        4719168 Byte in execution memory which more than 50% of memory execution

            even if this test is not realistic because we take
            elements one by one but in our application results
            will be more efficient because we will take
            only subsequence to save it in db and show first results
            to user then we complete insertion process

     *
     */
    @Test
    fun computeSequenceParsingPerformance() = runBlocking {
        val repository = AlbumRemoteRepository()
        val startTime = System.currentTimeMillis()
        val startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
        // call api
        val albums = repository.getAlbums()

        launch {
            albums.forEach {
                println("Items : ${it.title}")
            }
        }
        //
        System.gc()
        val finishTime = System.currentTimeMillis()
        val finishMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
        //
        val elapsedMemory = finishMemory - startMemory
        val elapsedTime = finishTime - startTime
        //
        println("Elapsed time : $elapsedTime")
        println("Elapsed memory : $elapsedMemory")
        //println("Items : ${albums.count()}")
    }


    /**
     * test Sequence.chunked method intead of Sequence.foreach
     *
     * Sequence.chunked allows us to take first sublist and show it to user
     * then proceed to storing all data partially
     *
     * since we aim to run this methos in asynchronous execution stack
     * this will take less time
     *
     * Results :
        I/System.out: Elapsed time : 6194
        I/System.out: Elapsed memory : 3736264

            we gained : 4015 ms in execution time
                        4719192 Byte in execution memory which more than 50% of memory execution
                                consumed by the first api call
     */
    @Test
    fun chunkedSequencePerformance() = runBlocking {
        val repository = AlbumRemoteRepository()
        // force the call of gc to freeup memory
        System.gc()
        val startTime = System.currentTimeMillis()
        val startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
        // call api
        val albums = repository.getAlbums()
        val chunkedLists = albums.chunked(100)
        launch {
            chunkedLists.forEach {
                println("Items : ${it.size}")
            }
        }
        //
        System.gc()
        val finishTime = System.currentTimeMillis()
        val finishMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
        //
        val elapsedMemory = finishMemory - startMemory
        val elapsedTime = finishTime - startTime
        //
        println("Elapsed time : $elapsedTime")
        println("Elapsed memory : $elapsedMemory")

    }


}