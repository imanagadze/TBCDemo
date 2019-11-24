package ge.tbc.tbcdemo.app

import android.app.Application
import android.content.Context
import com.google.gson.*
import dagger.Module
import dagger.Provides
import ge.tbc.tbcdemo.BuildConfig
import ge.tbc.tbcdemo.R
import ge.tbc.tbcdemo.data.data_providers.RemoteDataProvider
import ge.tbc.tbcdemo.data.models.*
import ge.tbc.tbcdemo.data.repositories.MatchRepositoryImpl
import ge.tbc.tbcdemo.domain.repositories.MatchRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    @Named(BASE_URL)
    fun provideBaseUrl(context: Context): String {
        return context.getString(R.string.base_url)
    }

    @Singleton
    @Provides
    fun provideRemoteDataProvider(
        @Named(BASE_URL) baseUrl: String
    ): RemoteDataProvider {


        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(
                chain: Array<java.security.cert.X509Certificate>,
                authType: String
            ) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(
                chain: Array<java.security.cert.X509Certificate>,
                authType: String
            ) {
            }

            override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                return arrayOf()
            }
        })

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())
        val sslSocketFactory = sslContext.socketFactory

        val builder = OkHttpClient()
            .newBuilder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        builder.hostnameVerifier { _, _ -> true }

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }

        val gson = GsonBuilder()
            .registerTypeAdapter(Action::class.java, object : JsonDeserializer<Action> {
                override fun deserialize(
                    json: JsonElement?,
                    typeOfT: Type?,
                    context: JsonDeserializationContext?
                ): Action {
                    val jsonObject = json!!.asJsonObject

                    return when {
                        jsonObject.has("goalType") -> {
                            val type =
                                GoalType.values()[jsonObject["goalType"].asInt - 1]

                            val player: Player = Gson().fromJson<Player>(
                                jsonObject.get("player"),
                                Player::class.java
                            )
                            GoalAction(player, type)
                        }
                        jsonObject.has("player") -> {
                            val player: Player = Gson().fromJson<Player>(
                                jsonObject.get("player"),
                                Player::class.java
                            )
                            CardAction(player)
                        }
                        else -> {
                            val player1: Player = Gson().fromJson<Player>(
                                jsonObject.get("player1"),
                                Player::class.java
                            )
                            val player2: Player = Gson().fromJson<Player>(
                                jsonObject.get("player2"),
                                Player::class.java
                            )
                            SubstitutionAction(player1, player2)
                        }
                    }
                }

            })
            .create()
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(builder.build())
            .baseUrl(baseUrl)
            .build()
        return retrofit.create(RemoteDataProvider::class.java)
    }

    @Singleton
    @Provides
    fun provideMatchRepository(matchRepository: MatchRepositoryImpl): MatchRepository {
        return matchRepository
    }
}