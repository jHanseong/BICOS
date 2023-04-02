package com.dbtechprojects.photonotes.screens.memo.search

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dbtechprojects.photonotes.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreen() {
    var ClientQuery = remember { mutableStateOf("") }
    val fbdb = Firebase.firestore.collection("data")
    val users2 = ArrayList<User>()
    var users = remember { mutableStateListOf<User>() }
    var userInfo = Firebase.auth.currentUser
    LaunchedEffect(ClientQuery.value){
        userInfo?.let { it ->
            Log.d("검색창",it.toString())
            users.clear()
            fbdb.whereEqualTo("host",it.email.toString()).get().addOnSuccessListener {documents ->
                for(document in documents){
                    if(ClientQuery.value.equals("")){
                        Log.d("검색창",document.get("name").toString())
                        users.add(
                            User(document.get("name").toString(),
                            document.get("gender").toString(),
                            document.get("birth").toString(),
                            document.get("phone").toString(),
                            document.get("cl_email").toString())
                        )
                    }else{
                        Log.d("검색창",document.get("name").toString())
                        if(document.get("name").toString().equals(ClientQuery.value)){
                            users.add(
                                User(document.get("name").toString(),
                                document.get("gender").toString(),
                                document.get("birth").toString(),
                                document.get("phone").toString(),
                                document.get("cl_email").toString())
                            )
                        }
                    }
                }
            }
        }

    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "BICOS") }
            )
        }
    )
    {
        Column (modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(20.dp)
        ){
            Clsearch(query = ClientQuery, navController = NavController)
            LazyColumnDemo(users,onUserChange = {users= it})
        }
    }
}
@Composable
fun LazyColumnDemo(users: SnapshotStateList<User>, onUserChange:(SnapshotStateList<User>) ->Unit ) {
    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        items(items = users, itemContent = { item ->
            ListItemView(item)
        })
    }
}

@Composable
fun ListItemView(user: User) : Unit {
    Card(
        backgroundColor = Color.White,
        elevation = Dp(2F),
        modifier = Modifier
            .padding(bottom = 16.dp)
            .border(width = 3.dp, color = Color.Gray, shape = RoundedCornerShape(20.dp))
            .clip(shape = RoundedCornerShape(20.dp))
    ) {
        Row {
            Card(
                modifier = Modifier
                    .size(width = 130.dp, height = 174.dp),
                shape = RoundedCornerShape(20.dp),
                elevation = 2.dp
            ) {
                Image(
                    painterResource(R.drawable.user),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp)
            ) {
                user.name?.let {
                    Text(
                        text = it,
                        modifier = Modifier.padding(all = 4.dp),
                        textAlign = TextAlign.Start,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                }
                user.gender?.let {
                    Text(
                        text = it,
                        modifier = Modifier.padding(all = 4.dp),
                        textAlign = TextAlign.Start,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }
                user.birth?.let {
                    Text(
                        text = it.toString(),
                        modifier = Modifier.padding(all = 4.dp),
                        textAlign = TextAlign.Start,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
                user.phone?.let {
                    Text(
                        text = it,
                        modifier = Modifier.padding(all = 4.dp),
                        textAlign = TextAlign.Start,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
                user.email?.let {
                    Text(
                        text = it,
                        modifier = Modifier.padding(all = 4.dp),
                        textAlign = TextAlign.Start,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun Clsearch(query: MutableState<String>, navController: NavController.Companion){
    Column(modifier = Modifier.padding(bottom = 25.dp)) {
        TextField(
            value = query.value,
            placeholder = { Text("검색") },
            maxLines = 1,
            onValueChange = { query.value = it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    tint = Color.Black.copy(
                        alpha = ContentAlpha.medium
                    )
                )
            },
            modifier = Modifier
                .background(Color.White)
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
            ),
            trailingIcon = {
                AnimatedVisibility(
                    visible = query.value.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(onClick = { query.value = "" }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.icon_cross),
                            contentDescription = stringResource(
                                R.string.clear_search
                            )
                        )
                    }
                }
            })
    }
}
