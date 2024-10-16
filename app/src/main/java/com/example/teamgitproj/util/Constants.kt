package com.example.teamgitproj.util

import com.example.teamgitproj.data.model.Blog
import com.example.teamgitproj.data.model.Filtering

const val SITE_BASE_URL = "https://team-git.iran.liara.run/"
const val CACHE_TIME = 120      // minute
const val KEY_BLOG = "keyBlogScreen"

val NO_FILTER = Filtering(emptyList() , emptyList())
val mockArticle = Blog(
    "-1" ,
    "" ,
    "" ,
    "" ,
    "" ,
    "" ,
    "" ,
    "" ,
    0
)