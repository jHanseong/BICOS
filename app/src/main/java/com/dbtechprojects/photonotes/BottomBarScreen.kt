package com.dbtechprojects.photonotes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    object Memo : BottomBarScreen(
        route = "메모장",
        title = "메모장",
        icon = Icons.Default.DateRange
    )

    object Search : BottomBarScreen(
        route = "검색",
        title = "검색",
        icon = Icons.Default.Search

    )

    object Home : BottomBarScreen(
        route = "캘린더",
        title = "캘린더",
        icon = Icons.Default.Home

    )

    object AddClient : BottomBarScreen(
        route = "고객등록",
        title = "고객등록",
        icon = Icons.Default.Add
    )

    object Profile : BottomBarScreen(
        route = "내 정보",
        title = "내 정보",
        icon = Icons.Default.Person
    )
}
