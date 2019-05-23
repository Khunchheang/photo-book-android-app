package com.khunchheang.photobookmark.di.module

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.khunchheang.photobookmark.R
import com.khunchheang.photobookmark.restclient.PicSumRestApi
import com.khunchheang.photobookmark.utils.NetworkUtil
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class RestClientModule {

    @Provides
    @Singleton
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor(fun(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
            return chain.proceed(request.build())
        })
    }

    @Provides
    @Singleton
    fun provideLoggingHttp(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        return logging
    }

    @Provides
    @Singleton
    fun provideGsonBuilder() = GsonBuilder().setLenient().create()!!

    @Provides
    @Singleton
    fun provideOkhttpClientBuilder(
        context: Context,
        logging: HttpLoggingInterceptor,
        header: Interceptor
    ): OkHttpClient.Builder {
        return OkHttpClient().newBuilder()
            .retryOnConnectionFailure(true)
            .connectionPool(ConnectionPool(NORMAL_TIMEOUT.toInt(), NORMAL_TIMEOUT, TimeUnit.MILLISECONDS))
            .addInterceptor(header)
            .addInterceptor(logging)
            .addInterceptor(RewriteRequestInterceptor(context))
            .addNetworkInterceptor(RewriteResponseCacheControlInterceptor())
            .cache(Cache(context.cacheDir, 10 * 1024 * 1024)) // 10 MB
    }

    @Provides
    @Singleton
    fun provideNormalTimeoutHttp(okHttpClientBuilder: OkHttpClient.Builder): OkHttpClient {
        return okHttpClientBuilder
            .readTimeout(NORMAL_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(NORMAL_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(NORMAL_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @Named(LONG_TIMEOUT_HTTP)
    fun provideLongTimeoutHttp(okHttpClientBuilder: OkHttpClient.Builder): OkHttpClient {
        return okHttpClientBuilder
            .readTimeout(LONG_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(LONG_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(LONG_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideNormalRetrofitBuilder(httpClient: OkHttpClient, gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Provides
    @Singleton
    @Named(LONG_RETROFIT_BUILDER)
    fun provideLongRetrofitBuilder(@Named(LONG_TIMEOUT_HTTP) httpClient: OkHttpClient, gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Provides
    @Singleton
    fun provideNormalPicSumRestApi(
        @Named(PIC_SUM_BASE_ENDPOINT) picSumEndPoint: String,
        retrofitBuilder: Retrofit.Builder
    ): PicSumRestApi {
        return retrofitBuilder.baseUrl(picSumEndPoint).build().create(PicSumRestApi::class.java)
    }

    @Provides
    @Singleton
    @Named(LONG_PIC_SUM_API)
    fun provideLongPicSumRestApi(
        @Named(PIC_SUM_BASE_ENDPOINT) picSumEndPoint: String,
        @Named(LONG_RETROFIT_BUILDER) retrofitBuilder: Retrofit.Builder
    ): PicSumRestApi {
        return retrofitBuilder.baseUrl(picSumEndPoint).build().create(PicSumRestApi::class.java)
    }

    @Provides
    @Singleton
    @Named(PIC_SUM_BASE_ENDPOINT)
    fun provideSkyBookingEndPoint(context: Context): String = context.getString(R.string.pic_sum_base_endpoint)

    private class RewriteResponseCacheControlInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val maxStale = 60 * 60 * 24 * 5
            val originalResponse = chain.proceed(chain.request())
            return originalResponse.newBuilder().header("Cache-Control", "public, max-age=120, max-stale=$maxStale")
                .build()
        }
    }

    private class RewriteRequestInterceptor(private val context: Context) : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val maxStale = 60 * 60 * 24 * 5
            val request: Request
            request = if (NetworkUtil.isNetworkAvailable(context)) {
                chain.request()
            } else {
                chain.request().newBuilder().header("Cache-Control", "max-stale=$maxStale").build()
            }
            return chain.proceed(request)
        }
    }

    companion object {
        private const val NORMAL_TIMEOUT = 10L
        private const val LONG_TIMEOUT = 20L

        private const val LONG_TIMEOUT_HTTP = "long_timeout_http"

        private const val LONG_RETROFIT_BUILDER = "long_retrofit_builder"

        const val LONG_PIC_SUM_API = "long_pic_sum_rest_api"

        private const val PIC_SUM_BASE_ENDPOINT = "pic_sum"
    }

}