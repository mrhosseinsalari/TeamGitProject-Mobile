package com.example.teamgitproj.data.repository

import com.example.teamgitproj.data.model.Blog
import com.example.teamgitproj.data.net.ApiService

class RetrofitBlogRepository(private val apiService: ApiService) :BlogRepository {
    override suspend fun getBlogs(): List<Blog> {
        return apiService.getBlogs().blogs
    }
}