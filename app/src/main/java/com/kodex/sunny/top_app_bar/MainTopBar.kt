package com.kodex.sunny.top_app_bar

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kodex.sunny.R
import com.kodex.sunny.ui.theme.DarkBlue
import com.kodex.sunny.ui.theme.TopBatColorWiete
import com.kodex.sunny.utils.Categories


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    titleIndex: Int,
    onSearch: (String)-> Unit,
    onTab: () -> Unit,
    onClickTopMenu: () -> Unit,
    onTopMenu: ()-> Unit,

) {
    var targetState by remember {
        mutableStateOf(false)
    }
    var expandedState by remember {
        mutableStateOf(false)
    }
    var queryText by remember {
        mutableStateOf("")
    }

    Crossfade(targetState = targetState) { target ->
        if (target) {
            SearchBar(
                modifier = Modifier.fillMaxWidth()
                    //.padding(horizontal = 5.dp)
                        ,
                inputField = {

                    SearchBarDefaults.InputField(
                       /* colors = TextFieldDefaults.colors(
                            focusedContainerColor = DarkWhite,
                            unfocusedContainerColor = DarkBlue*/
                       // )
                   // ,
                        query = queryText,
                        placeholder = {
                            Text(text = "Search..."
                            )
                        },
                        onQueryChange = { text ->
                            queryText = text
                        },
                        onSearch = { text->
                           onSearch (text)
                        },
                        expanded = expandedState,
                        onExpandedChange = {//exp ->
                               // expandedState = exp
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    expandedState = false
                                    targetState = false
                                    queryText = ""
                                    onSearch("")

                                }
                            ) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = ""

                                )
                            }
                        }
                    )
                },
                expanded = expandedState,
                onExpandedChange = { expo ->
                    expandedState = expo
                },
                content = {

                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                    }
                }
            )
        } else {
            TopAppBar(modifier = Modifier.padding(start = 40.dp),
                title = {

                    Text(fontSize = 25.sp,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Medium,
                        text = when (titleIndex) {
                            Categories.FAVORITES -> stringResource(id = R.string.faves)
                            Categories.ALL -> stringResource(id = R.string.all)
                            else -> stringArrayResource(id = R.array.category_arrays)[titleIndex]
                        }
                    )
                },
                actions = {
                    IconButton(onClick = {
                        targetState = true
                        //onTopMenu()
                    }) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    }

                    IconButton(onClick = {
                      onTab()
                    }) {
                        Icon(
                            Icons.AutoMirrored.Default.List,
                            contentDescription = "Filter"
                        )
                    }
                  /*  IconButton(onClick = {
                            onFilter()
                    }) {
                        Icon(
                            Icons.AutoMirrored.Default.List,
                            contentDescription = "Filter"
                        )
                    }*/
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TopBatColorWiete,
                    titleContentColor = DarkBlue,
                    actionIconContentColor = DarkBlue
                )
            )
        }
    }
}