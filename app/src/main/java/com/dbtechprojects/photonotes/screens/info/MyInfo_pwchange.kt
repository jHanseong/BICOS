package com.dbtechprojects.photonotes.screens.info

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.dbtechprojects.photonotes.graphs.DetailsScreen

@Composable
fun MyInfo_pwchange(navController: NavHostController){
    var text by remember { mutableStateOf("") }
    Column(
    ) {
        MyInfo_pw(navController)
    }
}
@Composable
fun MyInfo_pw(navController: NavHostController) {

    var cl_pw1 by remember { mutableStateOf("") }
    var cl_pw2 by remember { mutableStateOf("") }
    var cl_pw3 by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(60.dp)) {

        Row(horizontalArrangement = Arrangement.SpaceBetween){
            Text(text="비밀번호 수정", fontSize = 25.sp)
        }

        Row(horizontalArrangement = Arrangement.SpaceBetween){
            Text(text="비밀번호를 변경해 주세요.", fontSize = 20.sp)
        }

        Divider(color = Color.Gray, thickness = 2.dp)

        OutlinedTextField(
            value = cl_pw1,
            onValueChange = { cl_pw1 = it },
            label = { Text("현재비밀번호") },
        )

        Divider(color = Color.Gray, thickness = 2.dp)

        OutlinedTextField(
            value = cl_pw2,
            onValueChange = { cl_pw2 = it },
            label = { Text("비밀번호") },
        )

        Text(text = "8~20자리/영문(대/소문자),숫자,특수문자 혼합", fontSize = 12.sp, color=Color.LightGray)

        Divider(color = Color.Gray, thickness = 2.dp)

        OutlinedTextField(
            value = cl_pw3,
            onValueChange = { cl_pw3 = it },
            label = { Text("비밀번호 확인") }
        )

        Divider(color = Color.Gray, thickness = 2.dp)

        Text(text = "아이디와 생일, 전화번호 등 개인정보와 관련된 숫자 등 쉽게 알아 낼 수 있는 비밀번호는 개인정보 유출의 위험이 있으니 사용을 자제해주시기 바랍니다.", fontSize = 14.sp, color=Color.LightGray)

        Row(){
            val context = LocalContext.current
            Button(
                onClick = {
                    Toast.makeText(context,"비밀번호를 수정하였습니다.",Toast.LENGTH_SHORT).show()
                    navController.navigate(DetailsScreen.Chprofilecl.route) },
                    colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Unspecified,
                    contentColor = Color.Gray,

                ),
                modifier = Modifier.wrapContentSize(),
            ) {
                Text(text = "완료")
            }

            Button(
                onClick = {navController.navigate(DetailsScreen.Chprofilecl.route) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Unspecified,
                    contentColor = Color.Gray
                ),
                modifier = Modifier.wrapContentSize(),
            ) {
                Text(text = "취소")
            }
        }

            }
        }