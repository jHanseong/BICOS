package com.dbtechprojects.photonotes.screens.calendar

import android.Manifest
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.dbtechprojects.photonotes.R
import com.dbtechprojects.photonotes.graphs.DetailsScreen
import com.dbtechprojects.photonotes.screens.calendar.ui.MinimalCalendar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Composable
fun CalendarScreen(
    navController: NavHostController,
){
    var selectDate by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "BICOS") }

            )

        },
    ) { innerPadding ->
        Column(
            modifier =Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Surface(
                modifier = Modifier
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = 4.dp
            ) {
                MinimalCalendar(
                    onSelectDate = { date ->
                        selectDate = date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
                    }
                )
            }
//            Text(
//                modifier = Modifier.align(Alignment.CenterHorizontally),
//                text = selectDate
//            )
            CalendarDashboardWidget(navController)
            Spacer(modifier = Modifier.padding(30.dp))
        }
    }
}



@SuppressLint("SuspiciousIndentation")
@Composable

fun CalendarDashboardWidget(

    navController: NavHostController,
    modifier: Modifier = Modifier,
//    events: Map<String, List<CalendarEvent>>,
    onPermission: (Boolean) -> Unit = {},
    onClick: () -> Unit = {},
    onAddEventClicked: () -> Unit = {},
//    onEventClicked: (CalendarEvent) -> Unit = {}
) {

    val fbdb = Firebase.firestore.collection("date")
    val users = remember { mutableStateListOf<Cal>() }
    var userInfo = Firebase.auth.currentUser
    users.clear()
    userInfo?.let { it ->
//        Log.d("검색창",it.toString())
        fbdb.whereEqualTo("host",it.email.toString()).get().addOnSuccessListener {documents ->
            for(document in documents){
//                Log.d("검색창",document.get("name").toString())
                users.add(Cal(document.get("cal_title").toString(),
                    document.get("cal_detail").toString(),
                    document.get("cl_name").toString()))
            }
        }

    }

    Card(
        shape = RoundedCornerShape(24.dp),
        elevation = 8.dp,
        modifier = Modifier
//            .fillMaxWidth()
            .size(width = 450.dp, height = 200.dp)
            .padding(16.dp)
    ) {
        val context = LocalContext.current
//        val readCalendarPermissionState = rememberPermissionState(
        Manifest.permission.READ_CALENDAR
//        )
        val isDark = !MaterialTheme.colors.isLight
        Column(
            modifier = modifier
                .clickable { onClick() }
                .padding(8.dp)
        ) {
            Row(

                Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("일정관리", style = MaterialTheme.typography.body1)
                Icon(
                    painterResource(R.drawable.ic_add),
                    stringResource(R.string.app_name),
                    modifier = Modifier
                        .size(18.dp)
                        .clickable {
                            navController.navigate(DetailsScreen.CalDetail.route)
//                            CalendarEvent()
                        }

                )
            }
            Spacer(Modifier.height(8.dp))
            Column(

                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isDark) Color.DarkGray else LightGray),
//                contentPadding = PaddingValues(vertical = 10.dp, horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {

//                Text(text = c_namelist[0]+" 나이:"+c_pricelist)
                test()
                LazyColumnDemo2(users)
//                if (readCalendarPermissionState.hasPermission) {
//                    if (events.isEmpty()) {
//                        item {
//                            LaunchedEffect(true) { onPermission(true) }
//                            Text(
//                                text = stringResource(R.string.no_events),
//                                modifier = Modifier.padding(16.dp),
//                                style = MaterialTheme.typography.body1
//                            )
//                        }
//                    } else {
//                        events.forEach { (day, events) ->
//                            item {
//                                LaunchedEffect(true) { onPermission(true) }
//                                Column(
//                                    verticalArrangement = Arrangement.spacedBy(4.dp),
//                                ) {
//                                    Text(
//                                        text = day,
//                                        style = MaterialTheme.typography.body2
//                                    )
//                                    events.forEach { event ->
//                                        CalendarEventSmallItem(event = event, onClick = {
//                                            onEventClicked(event)
//                                        })
//                                    }
//                                }
//                            }
//                        }
//                    }
//                } else {
//                    item {
//                        LaunchedEffect(true) { onPermission(false) }
//                        NoReadCalendarPermissionMessage(
//                            shouldShowRationale = readCalendarPermissionState.shouldShowRationale,
//                            context
//                        ) {
//                            readCalendarPermissionState.launchPermissionRequest()
//                        }
//                    }
//                }
            }

        }
    }
}

@Composable
fun test(){
    val db = Firebase.firestore

    db.collection("user")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {

                Log.d(ContentValues.TAG, "${document.id} => ${document.data}")

                Log.d("사이클체크","5555")
            }
        }
        .addOnFailureListener { exception ->
            Log.d(ContentValues.TAG, "Error getting documents: ", exception)
        }
}

@Composable
fun LazyColumnDemo2(users: SnapshotStateList<Cal>) {
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        items(items = users, itemContent = { item ->
            ProductCard(item)
        })
    }
}

@Composable
fun ProductCard(user:Cal): Unit {

    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                start = 4.dp,
                end = 4.dp,
                top = 2.dp,
                bottom = 2.dp
            )
            .fillMaxWidth(),
        elevation = 6.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 12.dp)
        ) {
            user.cal_title?.let {title ->
                Text(
                    text = title,
                    modifier = Modifier
                        .fillMaxWidth(0.25f)
                        .wrapContentWidth(Alignment.Start),

                    fontSize = 13.sp
                )
            }
            user.cal_detail?.let { detail ->
                Text(
                    text = detail,
                    modifier = Modifier
//                        .wrapContentWidth(Alignment.End),
                        .fillMaxWidth(0.5f)
                    ,fontSize = 13.sp

                )
            }
            user.cl_name?.let { name ->
                Text(
                    text = name,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
//                        .wrapContentWidth(Alignment.End),

                    ,fontSize = 13.sp

                )
            }
        }

    }
}