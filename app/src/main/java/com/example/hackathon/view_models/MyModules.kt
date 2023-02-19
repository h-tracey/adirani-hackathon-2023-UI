package com.example.hackathon.view_models

import com.example.hackathon.api.RetrofitFormatHelper
import com.example.hackathon.api.RetrofitConnectionHelper
import com.example.hackathon.utility.Constant
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import org.koin.dsl.module

val viewModules = module {
    viewModel { TableViewModel(get()) }
}

val repoModules = module {
    factory { TableRepository() }
}

val apiModules = module {
    single { RetrofitFormatHelper() }
    factory {
        Retrofit.Builder()
            .baseUrl(Constant.EXAMPLE_URL)
            .client(getOkHttpClient())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
    single { getOkHttpClient() }
    factory { RetrofitConnectionHelper() }
}


private fun getOkHttpClient(): OkHttpClient {
    return try {
        val trustAllCerts = arrayOf<TrustManager>(
            object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
        )
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        val sslSocketFactory = sslContext.socketFactory
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(Constant.TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .readTimeout(Constant.TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .writeTimeout(Constant.TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
        builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        builder.hostnameVerifier { hostname: String?, session: SSLSession? -> true }
//        builder.addNetworkInterceptor(NetExceptionHandlerInterceptor())
//        builder.addInterceptor(DecryptInterceptor())
        builder.build()
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}
