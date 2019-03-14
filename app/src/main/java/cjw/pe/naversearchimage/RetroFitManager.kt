package cjw.pe.naversearchimage

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object RetroFitManager {

    private val retroFit = Retrofit.Builder()
        .baseUrl("https://openapi.naver.com/v1/")
        .addConverterFactory(GsonConverterFactory.create()) // 파싱등록
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()


    val naverService = retroFit.create<RetroFitInterface>(RetroFitInterface::class.java)

//    companion object {
//        val instance = RetroFitManager()
//    }
}