package com.example.teamgitproj.data.repository

import com.example.teamgitproj.data.model.Blog
import com.example.teamgitproj.data.model.Category

interface BlogRepository {
    suspend fun getBlogs() :List<Blog>
    suspend fun getCategoryList() :List<Category>
}