package com.project.currencyapp.data.di

import android.app.Application
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.project.currencyapp.data.source.network.CurrencyAPIs
import com.project.currencyapp.data.source.repository.CurrencyRepositoryImp
import com.project.currencyapp.data.source.repository.DetailsRepositoryImp
import com.project.currencyapp.domain.repository.CurrencyRepository
import com.project.currencyapp.domain.repository.DetailsRepository
import com.project.currencyapp.presentation.CurrencyApplication
import com.project.currencyapp.presentation.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMyApi(client: OkHttpClient): CurrencyAPIs {

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build().create(CurrencyAPIs::class.java);

    }


    @Provides
    @Singleton
    fun provideHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {

        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.connectTimeout(15, TimeUnit.SECONDS) // request connect timeout
        clientBuilder.readTimeout(15, TimeUnit.SECONDS)  // socket timeout

        // add logging interceptor to client
        clientBuilder.addInterceptor(interceptor)

        // add api key to header using interceptor with client
        clientBuilder.addInterceptor { chain ->
            val request: Request =
                chain.request().newBuilder().addHeader("apikey", Constants.API_KEY).build()
            chain.proceed(request)
        }

        return clientBuilder.build();

    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {

        val interceptor = HttpLoggingInterceptor()

        // tell logging interceptor to log apis body
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return interceptor

    }

    @Provides
    fun provideBaseApplication(application: Application): CurrencyApplication {
        return application as CurrencyApplication
    }

    @Provides
    @Singleton
    fun provideCurrencyRepository( api: CurrencyAPIs,app: CurrencyApplication): CurrencyRepository {
        return CurrencyRepositoryImp(api,app);
    }
    @Provides
    @Singleton
    fun provideDetailsRepository( api: CurrencyAPIs,app: CurrencyApplication): DetailsRepository {
        return DetailsRepositoryImp(api,app);
    }
}