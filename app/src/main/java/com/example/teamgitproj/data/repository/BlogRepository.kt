package com.example.teamgitproj.data.repository

import com.example.teamgitproj.data.model.Blog

interface BlogRepository {
    suspend fun getBlogs() :List<Blog>
}