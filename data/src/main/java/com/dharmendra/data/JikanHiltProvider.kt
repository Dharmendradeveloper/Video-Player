package com.dharmendra.data

import com.dharmendra.data.impl.GetJikanDetailsRepoImpl
import com.dharmendra.data.impl.GetJikanListOfDataRepositoryImpl
import com.dharmendra.data.network.JikanApiService
import com.dharmendra.domain.jikandata.GetJikanListOfDataRepository
import com.dharmendra.domain.jikandatadetails.GetJikanDataDetailsRepository
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object JikanHiltProvider {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit{
        /**
         * @client is not required explicitly if already added network_security_config file.
         * */
        val client = OkHttpClient.Builder().certificatePinner(CertificatePinner.Builder()
            .add("api.jikan.moe","sha256/B83dJSU7Dzztwy+HvAHF/ekUhSt+8a3u9GW+2TpRh/0=").build()).build()
        return Retrofit.Builder().baseUrl(APIConstant.BASE_URL).client(client).addConverterFactory(
            GsonConverterFactory.create()).addCallAdapterFactory(CoroutineCallAdapterFactory()).build()
    }

    @Provides
    @Singleton
    fun provideJikanAPIService(retrofit: Retrofit): JikanApiService {
        return retrofit.create(JikanApiService::class.java)
    }

    @Provides
    fun provideJikanDataRepository(jikanApiService: JikanApiService): GetJikanListOfDataRepository{
        return GetJikanListOfDataRepositoryImpl(jikanApiService = jikanApiService)
    }

    @Provides
    fun provideJikanDataDetailsRepository(jikanApiService: JikanApiService): GetJikanDataDetailsRepository{
        return GetJikanDetailsRepoImpl(remoteApiService = jikanApiService)
    }
}