package cjw.pe.naversearchimage

import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RetroFitInterface {
    @GET("search/image")
    fun  searchImage(
        @Header("X-Naver-Client-Id") id:String,
        @Header("X-Naver-Client-Secret") secret:String,
        @Query("query")query:String ): Single<JsonObject>
}