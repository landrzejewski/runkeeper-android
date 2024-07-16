package pl.training.runkeeper.commons

import okhttp3.Interceptor
import okhttp3.Response

class AppIdInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (chain.request().url.host != HOST) {
            return chain.proceed(request)
        }
        val url = request.url.newBuilder()
            .addQueryParameter(PARAM_NAME, APP_ID)
            .build()
        val modifiedRequest = request.newBuilder()
            .url(url)
            .build()
        return chain.proceed(modifiedRequest)
    }

    companion object {

        const val PARAM_NAME = "appid"
        const val APP_ID = "b933866e6489f58987b2898c89f542b8"
        const val HOST = "api.openweathermap.org"

    }

}
