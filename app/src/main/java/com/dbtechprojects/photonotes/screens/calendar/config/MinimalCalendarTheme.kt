package com.project.bicos_project.screens.calendar.config

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.project.bicos_project.screens.calendar.config.MinimalCalendarColors

object MinimalCalendarTheme {
    @Composable
    fun colors(
        defaultColor: Color = MaterialTheme.colors.primary,
        backgroundColor: Color = MaterialTheme.colors.background,
        headerBackgroundColor: Color = backgroundColor,
        headerDateTextColor: Color = MaterialTheme.colors.onBackground,
        headerTodayIconColor: Color = MaterialTheme.colors.onBackground,
        headerArrowIconColor: Color = MaterialTheme.colors.onBackground,
        headerSelectIconColor: Color = defaultColor,
        selectionBackgroundColor: Color = backgroundColor,
        selectionItemTextColor: Color = MaterialTheme.colors.onBackground,
        selectionItemSelectedBackgroundColor: Color = defaultColor.copy(.1f),
        selectionItemSelectedTextColor: Color = MaterialTheme.colors.onBackground,
        selectionButtonBackgroundColor: Color = defaultColor.copy(.5f),
        selectionButtonTextColor: Color = MaterialTheme.colors.onPrimary,
        weekBackgroundColor: Color = backgroundColor,
        weekTextColor: Color = MaterialTheme.colors.onBackground.copy(.3f),
        dateTextColor: Color = MaterialTheme.colors.onBackground,
        dateSelectedBackgroundColor: Color = defaultColor.copy(.5f),
        dateSelectedTextColor: Color = MaterialTheme.colors.onPrimary,
        dateTodayBackgroundColor: Color = defaultColor.copy(.1f),
        dateTodayTextColor: Color = MaterialTheme.colors.onSurface
    ): MinimalCalendarColors {
        return MinimalCalendarColors(
            defaultColor = defaultColor,
            backgroundColor = backgroundColor,
            headerBackgroundColor = headerBackgroundColor,
            headerDateTextColor = headerDateTextColor,
            headerTodayIconColor = headerTodayIconColor,
            headerArrowIconColor = headerArrowIconColor,
            headerSelectIconColor = headerSelectIconColor,
            selectionBackgroundColor = selectionBackgroundColor,
            selectionItemTextColor = selectionItemTextColor,
            selectionItemSelectedBackgroundColor = selectionItemSelectedBackgroundColor,
            selectionItemSelectedTextColor = selectionItemSelectedTextColor,
            selectionButtonBackgroundColor = selectionButtonBackgroundColor,
            selectionButtonTextColor = selectionButtonTextColor,
            weekBackgroundColor = weekBackgroundColor,
            weekTextColor = weekTextColor,
            dateTextColor = dateTextColor,
            dateSelectedTextColor = dateSelectedTextColor,
            dateSelectedBackgroundColor = dateSelectedBackgroundColor,
            dateTodayTextColor = dateTodayTextColor,
            dateTodayBackgroundColor = dateTodayBackgroundColor
        )
    }
}