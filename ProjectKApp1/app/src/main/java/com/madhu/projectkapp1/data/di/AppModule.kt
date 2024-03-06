package com.madhu.projectkapp1.data.di

import android.content.Context
import com.madhu.projectkapp1.data.api.ApiService
import com.madhu.projectkapp1.data.datasource.CustomerDataSource
import com.madhu.projectkapp1.data.datasource.CustomerDataSourceImpl
import com.madhu.projectkapp1.data.datasource.Login
import com.madhu.projectkapp1.data.datasource.ProductDataSource
import com.madhu.projectkapp1.data.datasource.ProductDataSourceImpl
import com.madhu.projectkapp1.data.datasource.RecordDataSource
import com.madhu.projectkapp1.data.datasource.RecordDataSourceImpl
import com.madhu.projectkapp1.data.datasource.TransactionDataSource
import com.madhu.projectkapp1.data.datasource.TransactionDataSourceImpl
import com.madhu.projectkapp1.data.datasource.UploadImageDataSource
import com.madhu.projectkapp1.data.datasource.UploadImageDataSourceImpl
import com.madhu.projectkapp1.data.datasource.UserDataSource
import com.madhu.projectkapp1.data.datasource.UserDataSourceImpl
import com.madhu.projectkapp1.data.datasource.VillageDataSource
import com.madhu.projectkapp1.data.datasource.VillageDataSourceImpl
import com.madhu.projectkapp1.ui.repository.CustomerRepository
import com.madhu.projectkapp1.ui.repository.LoginRepository
import com.madhu.projectkapp1.ui.repository.ProductRepository
import com.madhu.projectkapp1.ui.repository.RecordRepository
import com.madhu.projectkapp1.ui.repository.TransactionRepository
import com.madhu.projectkapp1.ui.repository.UploadImageRepository
import com.madhu.projectkapp1.ui.repository.UserRepository
import com.madhu.projectkapp1.ui.repository.VillageRepository
import com.madhu.projectkapp1.utility.TokenManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.CallAdapter.Factory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun providesTokenManager(context: Context): TokenManager {
        return TokenManager(context)
    }


    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {

        val httpLoggInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val httpClient = OkHttpClient().newBuilder().apply {
            addInterceptor(httpLoggInterceptor)
        }

        httpClient.apply {
            readTimeout(60, TimeUnit.SECONDS)
        }

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory()).build()

        return Retrofit.Builder()
            .baseUrl("http://3.7.2.129:8080/")
            .client(httpClient.build())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesCustomerDataSource(apiService: ApiService): CustomerDataSource {
        return CustomerDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun providesCustomerRepository(
        customerDataSource: CustomerDataSource,
        tokenManager: TokenManager
    ): CustomerRepository {
        return CustomerRepository(customerDataSource, tokenManager)
    }

    @Provides
    @Singleton
    fun providesProductDataSource(apiService: ApiService): ProductDataSource {
        return ProductDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun providesProductRepository(
        productDataSource: ProductDataSource,
        tokenManager: TokenManager
    ): ProductRepository {
        return ProductRepository(productDataSource, tokenManager)
    }

    @Provides
    @Singleton
    fun providesRecordDataSource(apiService: ApiService): RecordDataSource {
        return RecordDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun providesRecordRepository(
        recordDataSource: RecordDataSource,
        tokenManager: TokenManager
    ): RecordRepository {
        return RecordRepository(recordDataSource, tokenManager)
    }

    @Provides
    @Singleton
    fun providesVillageDataSource(apiService: ApiService, login: Login): VillageDataSource {
        return VillageDataSourceImpl(apiService, login)
    }

    @Provides
    @Singleton
    fun providesVillageRepository(
        villageDataSource: VillageDataSource,
        tokenManager: TokenManager
    ): VillageRepository {
        return VillageRepository(villageDataSource, tokenManager)
    }


    @Provides
    @Singleton
    fun providesTransactionSource(apiService: ApiService, login: Login): TransactionDataSource {
        return TransactionDataSourceImpl(apiService, login)
    }

    @Provides
    @Singleton
    fun providesTransactionRepository(
        transactionDataSource: TransactionDataSource,
        tokenManager: TokenManager
    ): TransactionRepository {
        return TransactionRepository(transactionDataSource, tokenManager)
    }


    @Provides
    @Singleton
    fun providesUploadImageSource(apiService: ApiService): UploadImageDataSource {
        return UploadImageDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun providesUploadImageRepository(uploadImageDataSource: UploadImageDataSource): UploadImageRepository {
        return UploadImageRepository(uploadImageDataSource)
    }

    @Provides
    @Singleton
    fun providesLoginDataSource(apiService: ApiService): Login {
        return Login(apiService)
    }

    @Provides
    @Singleton
    fun providesLoginRepository(loginDataSource: Login): LoginRepository {
        return LoginRepository(loginDataSource)
    }


    @Provides
    @Singleton
    fun providesUserDataSource(apiService: ApiService): UserDataSource {
        return UserDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun providesUserRepository(userDataSource: UserDataSource): UserRepository {
        return UserRepository(userDataSource)
    }


}
