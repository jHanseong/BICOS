package com.dbtechprojects.photonotes.screens.info

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.project.bicos_project.graphs.AuthScreen

@Composable
fun MyInfo_break2(navController: NavHostController){
    var text by remember { mutableStateOf("") }
    Column {
        MyInfo_drop2(navController)
    }
}

@Composable
fun MyInfo_drop2(navController: NavHostController) {

    val list = listOf("탈퇴 사유를 선택해주세요.","다른 아이디 변경","컨텐츠 및 서비스 부족","개인정보 노출 우려","시스템 장애로 인한 불편","장기간 부재(입대, 유학 등)")
    val expanded = remember { mutableStateOf(false) }
    val currentValue = remember { mutableStateOf(list[0]) }
    val context = LocalContext.current
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(60.dp)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "회원탈퇴", fontSize = 25.sp)
        }

        Divider(color = Color.Gray, thickness = 2.dp)

        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "회원 탈퇴 안내", fontSize = 18.sp)
        }

        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = " - 회원 탈퇴를 하시면 BICOS 서비스를 이용하실 수 없습니다.(유효기간이 남은 멤버십상품도 모두 이용하실 수 없습니다.)",
                fontSize = 14.sp
            )
        }

        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = " - 탈퇴 시 개인정처리방침에 의거하여 모든 개인정보가 분리보관되며, 분리보관된 개인정보는 전자상거래 이용내역 여부에 따라 명시된 기간 후 지체없이 파기됩니다.",
                fontSize = 14.sp
            )
        }

        Text(text = "\n")

        Divider(color = Color.Gray, thickness = 2.dp)

        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "탈퇴 사유 선택", fontSize = 18.sp)
        }

        Button(onClick = {},
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Unspecified,
                contentColor = Color.Gray
            )) {
            Row(modifier = Modifier.clickable {
                expanded.value = !expanded.value
            }) {
                Text(text = currentValue.value)
                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = null)
                DropdownMenu(expanded = expanded.value, onDismissRequest = {
                    expanded.value = false
                }) {
                    list.forEach {
                        DropdownMenuItem(onClick = {
                            currentValue.value = it
                            expanded.value = false
                        }) {
                            Text(text = it)
                        }
                    }
                }
            }
        }
        Button(onClick = {
            Toast.makeText(context,"회원탈퇴가 되었습니다.",Toast.LENGTH_SHORT).show()
            navController.navigate(AuthScreen.Login.route)
                         },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Unspecified,
                contentColor = Color.Gray
            )
        )
         {
            Text(text = "회원탈퇴!!!")
        }
    }
}
