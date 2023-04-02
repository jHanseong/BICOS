package com.dbtechprojects.photonotes.screens.info

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.dbtechprojects.photonotes.graphs.DetailsScreen

@Composable
fun MyInfo_break(navController: NavHostController){
    var text by remember { mutableStateOf("") }
    Column(
    ) {
        MyInfo_drop(navController)
    }
}

@Composable
fun MyInfo_drop(navController: NavHostController) {

    var cl_pw by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(60.dp)) {

        Row(horizontalArrangement = Arrangement.SpaceBetween){
            Text(text="비밀번호 확인", fontSize = 25.sp)
        }

        Divider(color = Color.Gray, thickness = 2.dp)

        Row(horizontalArrangement = Arrangement.SpaceBetween){
            Text(text="본인 확인을 위해 비밀번호를 입력해주세요.", fontSize = 14.sp)
        }

        OutlinedTextField(
            value = cl_pw,
            onValueChange = { cl_pw = it },
            label = { Text("비밀번호") },
        )

        Divider(color = Color.Gray, thickness = 2.dp)

            Button(
                onClick = {navController.navigate(DetailsScreen.Infodrop2.route) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Unspecified,
                    contentColor = Color.Gray
                ),
                modifier = Modifier.wrapContentSize(),
            ) {
                Text(text = "확인")
            }
        }

            }
