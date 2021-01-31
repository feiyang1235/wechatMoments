package com.person.ermao.thoughtworkshomework.api

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.person.ermao.thoughtworkshomework.App
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.ArrayList
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

class RetrofitService(
    private val context: Context,
    private val domainUrl: String,
    private val cache: Cache?,
    private val networkInterceptors: MutableList<Interceptor>? = null,
    private val interceptors: MutableList<Interceptor>? = null,
    private val cookieJar: CookieJar? = null
) {
    companion object {
        private const val BASE_URL = "http://thoughtworks-ios.herokuapp.com"
        fun <T> createDefaultApi(clazz: Class<T>):T{
            return Builder()
                .withContext(App.getApp())
                .domainUrl(BASE_URL)
                .setCache(Cache(File(App.getApp().cacheDir,"HttpCache"),1024*1024*4))
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build().create(clazz)
        }
    }

    private var serviceCache = ConcurrentHashMap<String, Any>()

    @Suppress("UNCHECKED_CAST")
    private fun <T> create(clazz: Class<T>): T {
        val httpClient = OkHttpClient.Builder()
            .cache(Cache(File(context.applicationContext.cacheDir, "HttpCache"), 1024 * 1024 * 4))
            .apply {
                networkInterceptors?.forEach {
                    addNetworkInterceptor(it)
                }
            }.retryOnConnectionFailure(true).connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS).apply {
                interceptors?.forEach {
                    addInterceptor(it)
                }
            }.build()
        Retrofit.Builder()
            .baseUrl(domainUrl)
            .client(httpClient)
            .validateEagerly(false)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
        val key = "$domainUrl - ${clazz.name}"
        val cachedService = serviceCache[key]
        return if (cachedService != null) {
            cachedService as T
        } else {

            val retrofit = Retrofit.Builder()
                .baseUrl(domainUrl)
                .client(httpClient)
                .validateEagerly(false)
                .addConverterFactory(
                    GsonConverterFactory.create(
//                        GsonBuilder().registerTypeAdapterFactory(
//                            CustomerGsonTypeAdapterFactory()
//                        ).create()
                    )
                )
                .build()
            val service = retrofit.create(clazz)
            serviceCache[key] =  service as Any
            service
        }
    }

    class Builder {
        private var context: Context? = null
        private var cache: Cache? = null
        private var networkInterceptors: MutableList<Interceptor>? = null
        private var interceptors: MutableList<Interceptor>? = null
        private var cookieJar: CookieJar? = null
        private var domainUrl: String? = null
        fun withContext(context: Context): Builder {
            this.context = context
            return this
        }

        fun setCache(cache: Cache?): Builder {
            this.cache = cache
            return this
        }

        fun addInterceptor(interceptor: Interceptor): Builder {
            if (interceptors == null) {
                interceptors = ArrayList()
            }
            interceptors!!.add(interceptor)
            return this
        }

        fun addInterceptor(interceptors: List<Interceptor>?): Builder {
            if (this.interceptors == null) {
                this.interceptors = ArrayList()
            }
            this.interceptors!!.addAll(interceptors!!)
            return this
        }

        fun addNetworkInterceptor(networkinterceptor: Interceptor): Builder {
            if (networkInterceptors == null) {
                networkInterceptors = ArrayList()
            }
            networkInterceptors!!.add(networkinterceptor)
            return this
        }

        fun addNetworkInterceptor(networkinterceptors: List<Interceptor>?): Builder {
            if (networkInterceptors == null) {
                networkInterceptors = ArrayList()
            }
            networkInterceptors!!.addAll(networkinterceptors!!)
            return this
        }

        fun setCookieJar(cookieJar: CookieJar?): Builder {
            this.cookieJar = cookieJar
            return this
        }

        fun domainUrl(domainUrl: String): Builder {
            this.domainUrl = domainUrl
            return this
        }

        fun build(): RetrofitService {
            return RetrofitService(
                context!!,
                domainUrl!!,
                cache,
                networkInterceptors,
                interceptors,
                cookieJar
            )
        }
    }
}