package com.kamo.globalkineticweatherapp.common
import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kamo.globalkineticweatherapp.data.Repository
import com.kamo.globalkineticweatherapp.data.RepositoryImpl
import com.kamo.globalkineticweatherapp.data.local.LocalDataSource
import com.kamo.globalkineticweatherapp.data.local.LocalDataSourceImpl
import com.kamo.globalkineticweatherapp.data.remote.ApiService
import com.kamo.globalkineticweatherapp.data.remote.RemoteDataSource
import com.kamo.globalkineticweatherapp.data.remote.RemoteRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object InjectorUtils{

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource = RemoteRemoteDataSourceImpl(apiService)

    @Singleton
    @Provides
    fun provideRepository(localDataSource: LocalDataSource, remoteDataSource: RemoteDataSource,coroutineDispatcher: CoroutineDispatcher): Repository =
        RepositoryImpl(localDataSource, remoteDataSource,coroutineDispatcher)

    @Singleton
    @Provides
    fun getRetrofit(gson:Gson) : Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(AppConstants.BASE_URL)
       // .addConverterFactory(MoshiConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()


    private val authInterceptor = Interceptor { chain->
        val newUrl = chain.request().url
            .newBuilder()
            .addQueryParameter("api_key", AppConstants.API_KEY)
            .addQueryParameter("count", 50.toString())
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    private val client = OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .build()
}
