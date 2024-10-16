package com.example.teamgitproj.ui.features.search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.teamgitproj.ui.widgets.SearchContent
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel
import com.example.teamgitproj.util.NO_FILTER

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreenUi() {
    val viewModel = getNavViewModel<SearchViewModel>()
    val navigation = getNavController()
    var showFilterDialog by remember { mutableStateOf(false) }

    val data by viewModel.blogs.collectAsState()
    val categoryList by viewModel.categoryList.collectAsState()
    val authorList by viewModel.authors.collectAsState()
    val searchedQuery by viewModel.searchQuery.collectAsState()
    val filtering by viewModel.filtering.collectAsState()

    var isFilterEnabled by remember { mutableStateOf(filtering != NO_FILTER) }

    Scaffold(
        topBar = {
            // SearchToolbar()
        },
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
             SearchContent(data)

            if (showFilterDialog) {
                // SearchDialog()
            }
        }
    }
}