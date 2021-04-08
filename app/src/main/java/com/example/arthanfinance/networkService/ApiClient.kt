package com.example.arthanfinance.networkService

import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


class ApiClient {

    companion object {
        private const val SERVER_URL = "https://arthanfin.com/customer-app/customer/"
        private const val PAYMENT_URL = "https://arthanfin.com/customer-app/paytm/"
    }

        private lateinit var apiService: ApiService
        fun getApiService(context: Context): ApiService {

            val gson = GsonBuilder()
                .setLenient()
                .create()
            // Initialize ApiService if not initialized yet
            if (!::apiService.isInitialized) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okhttpClient(context)) // Add our Okhttp client
                    .build()

                apiService = retrofit.create(ApiService::class.java)
            }
           return apiService
        }

    fun getAuthApiService(context: Context): ApiService {
        // Initialize ApiService if not initialized yet
        if (!::apiService.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://arthanfin.com/artlos/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClient(context)) // Add our Okhttp client
                .build()

            apiService = retrofit.create(ApiService::class.java)
        }
        return apiService
    }

    fun getPaytmApiService(context: Context): ApiService {
        // Initialize ApiService if not initialized yet
        if (!::apiService.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl(PAYMENT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClient(context)) // Add our Okhttp client
                .build()

            apiService = retrofit.create(ApiService::class.java)
        }
        return apiService
    }

    fun getUploadApiService(context: Context): ApiService {
        // Initialize ApiService if not initialized yet
        if (!::apiService.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://arthanfin.com/JerseyDemos/rest/file/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClient(context)) // Add our Okhttp client
                .build()

            apiService = retrofit.create(ApiService::class.java)
        }
        return apiService
    }

        /**
         * Initialize OkhttpClient with our interceptor
         */
        private fun okhttpClient(context: Context): OkHttpClient {
            val okHttpClient: OkHttpClient = UnsafeOkHttpClient.unsafeOkHttpClient
            val builder = okHttpClient.newBuilder()
            builder.readTimeout(30, TimeUnit.SECONDS)
            builder.connectTimeout(30, TimeUnit.SECONDS)
            return builder.build()
            //return builder.addInterceptor(AuthInterceptor(context)).build()
        }

    object UnsafeOkHttpClient {
        // Create a trust manager that does not validate certificate chains
        val unsafeOkHttpClient: OkHttpClient

        // Install the all-trusting trust manager

            // Create an ssl socket factory with our all-trusting manager
            get() = try {
                // Create a trust manager that does not validate certificate chains
                val trustAllCerts =
                    arrayOf<TrustManager>(
                        object : X509TrustManager {
                            override fun checkClientTrusted(
                                chain: Array<X509Certificate>,
                                authType: String
                            ) {
                            }

                            override fun checkServerTrusted(
                                chain: Array<X509Certificate>,
                                authType: String
                            ) {
                            }

                            override fun getAcceptedIssuers(): Array<X509Certificate> {
                                return arrayOf()
                            }
                        }
                    )

                // Install the all-trusting trust manager
                val sslContext =
                    SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, SecureRandom())

                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory = sslContext.socketFactory
                val builder: OkHttpClient.Builder = OkHttpClient.Builder()
                builder.sslSocketFactory(
                    sslSocketFactory,
                    trustAllCerts[0] as X509TrustManager
                )
                builder.hostnameVerifier(HostnameVerifier { hostname, session -> true })
                builder.build()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
    }

    }
