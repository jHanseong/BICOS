package com.project.bicos_project.screens.info

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dbtechprojects.photonotes.graphs.DetailsScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MyInfo(){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "BICOS") }
            )
        }
    ){
        MyInfo()
    }
}
@Composable
fun MyInfo(

    navController: NavHostController,
    backgroundColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = contentColorFor(backgroundColor)
) {
    Column(modifier = Modifier.padding(60.dp)) {

        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "내정보", fontSize = 30.sp)
        }

        Divider(color = Color.Gray, thickness = 2.dp)

        Text(text = "회원정보관리", fontSize = 20.sp)

        Button(
            onClick = {navController.navigate(DetailsScreen.Chprofile.route)},
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White,
                contentColor = Color.Gray
            ),
            modifier = Modifier.wrapContentSize(),
        ) {
            Text(text = "회원정보수정 \n - 이메일/휴대폰번호를 수정합니다.")
        }
        Button(
            onClick = { navController.navigate(DetailsScreen.Chprofilepw.route) },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White,
                contentColor = Color.Gray
            ),
            modifier = Modifier.wrapContentSize(),
        ) {
            Text(text = "비밀번호 수정")
        }

        Button(
            onClick = { navController.navigate(DetailsScreen.Infodrop1.route) },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White,
                contentColor = Color.Gray
            ),
            modifier = Modifier.wrapContentSize(),
        ) {
            Text(text = "회원탈퇴")
        }

        Divider(color = Color.Gray, thickness = 2.dp)

        Text(text = "이용권 정보", fontSize = 20.sp)

        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xff6162F5),
                contentColor = Color.Gray
            ),
            modifier = Modifier.wrapContentSize(),
            enabled = false,
        ) {
            Text(text = "현재 이용중인 상품권이 없습니다.")
        }

        Button(
            onClick = { navController.navigate(DetailsScreen.Infofare.route)},
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White,
                contentColor = Color.Gray
            ),
            modifier = Modifier.wrapContentSize(),
        ) {
            Text(text = "이용권 구매")
        }
    }
}