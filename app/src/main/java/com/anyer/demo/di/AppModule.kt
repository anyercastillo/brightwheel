package com.anyer.demo.di

import com.anyer.demo.feature_starred.data.Repository
import com.anyer.demo.feature_starred.data.RepositoryImpl
import com.anyer.demo.feature_starred.data.data_source.GitHubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @DispatcherIO
    fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideGitHubService(retrofit: Retrofit): GitHubService =
        retrofit.create(GitHubService::class.java)

    @Provides
    @Singleton
    fun provideRepository(
        gitHubService: GitHubService,
        @DispatcherIO dispatcherIO: CoroutineDispatcher
    ): Repository = RepositoryImpl(service = gitHubService, dispatcherIO = dispatcherIO)
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DispatcherIO