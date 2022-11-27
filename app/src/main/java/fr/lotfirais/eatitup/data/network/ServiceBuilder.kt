package fr.lotfirais.eatitup.data.network

import fr.lotfirais.eatitup.common.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private val rxAdapter = RxJava3CallAdapterFactory.create()

    private val client = OkHttpClient
        .Builder()
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(rxAdapter)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(RetrofitService::class.java)

    fun buildService(): RetrofitService {
        return retrofit
    }
}