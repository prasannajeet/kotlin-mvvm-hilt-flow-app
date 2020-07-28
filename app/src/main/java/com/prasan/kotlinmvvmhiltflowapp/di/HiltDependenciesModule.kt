package com.prasan.kotlinmvvmhiltflowapp.di

import com.prasan.kotlinmvvmhiltflowapp.BuildConfig
import com.prasan.kotlinmvvmhiltflowapp.domain.FHPRepository
import com.prasan.kotlinmvvmhiltflowapp.domain.GetPopularPhotosUseCase
import com.prasan.kotlinmvvmhiltflowapp.domain.IRepository
import com.prasan.kotlinmvvmhiltflowapp.model.network.FiveHundredPixelsAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Hilt Module class that builds our dependency graph
 * @author Prasan
 * @since 1.0
 */
@InstallIn(ActivityComponent::class)
@Module
object HiltDependenciesModule {

    @Provides
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun provideOKHttpClient(loggingInterceptor: HttpLoggingInterceptor) = OkHttpClient().apply {
        OkHttpClient.Builder().run {
            addInterceptor(loggingInterceptor)
            build()
        }
    }

    @Provides
    fun provideMoshiConverterFactory(): MoshiConverterFactory = MoshiConverterFactory.create()

    /**
     * Returns an instance of the [FiveHundredPixelsAPI] interface for the retrofit class
     * @return [FiveHundredPixelsAPI] impl
     */
    @Provides
    fun provideRetrofitInstance(
        client: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): FiveHundredPixelsAPI =
        Retrofit.Builder().run {
            baseUrl(BuildConfig.BASE_URL)
            addConverterFactory(moshiConverterFactory)
            client(client)
            build()
        }.run {
            create(FiveHundredPixelsAPI::class.java)
        }

    /**
     * Returns a singleton [FHPRepository] instance
     * @param retrofit [FiveHundredPixelsAPI] instance
     * @since 1.0.0
     */
    @Provides
    fun provideRepository(retrofit: FiveHundredPixelsAPI): IRepository = FHPRepository(retrofit)

    /**
     * Returns a [GetPopularPhotosUseCase] instance
     * @param repository [IRepository] impl
     * @since 1.0
     */
    @Provides
    fun provideUseCase(repository: IRepository): GetPopularPhotosUseCase =
        GetPopularPhotosUseCase(repository)
}