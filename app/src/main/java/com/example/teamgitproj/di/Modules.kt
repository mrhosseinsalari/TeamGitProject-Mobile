package com.example.teamgitproj.di

import com.example.teamgitproj.data.net.createApiService
import com.example.teamgitproj.data.repository.BlogRepository
import com.example.teamgitproj.data.repository.RetrofitBlogRepository
import com.example.teamgitproj.ui.features.home.HomeViewModel
import com.example.teamgitproj.ui.features.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModules = module {
    single { createApiService() }
    single<BlogRepository> { RetrofitBlogRepository(get()) }

    viewModel { HomeViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}