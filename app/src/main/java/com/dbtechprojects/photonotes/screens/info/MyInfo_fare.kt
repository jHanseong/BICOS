package com.dbtechprojects.photonotes.screens.info

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.dbtechprojects.photonotes.R
import kotlin.math.exp

@Composable
fun MyInfo_fare(navController: NavHostController) {
    var text by remember { mutableStateOf("") }
    Column {
        Info_fare(navController, contentDescription = "내용입니다.",)
    }
}

@Composable
fun Info_fare(
    painter: NavHostController,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
) {

    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(60.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "이용권구매", fontSize = 30.sp)
        }

        Divider(color = Color.Gray, thickness = 2.dp)

        Text(text = "사용중인 이용권", fontSize = 18.sp)
        Text(text = "사용중인 이용권이 없습니다.", fontSize = 20.sp)

        Divider(color = Color.Gray, thickness = 2.dp)

        var textshow =  remember { mutableStateOf("") }
        var imgshow by remember { mutableStateOf(R.drawable.fault) }
        var visible by remember { mutableStateOf(false)}

        Box() {
            Column() {
                Row() {
                    OutlinedButton(onClick = { textshow.value  = "Normal \n" +
                            " 가격 : 무료 \n" +
                            " 사용기간 : 제한없음 \n" +
                            " 데이터 등록 가능 인원 : 10명 \n" +
                            " 추가기능 : 해당 없음"
                        imgshow = R.drawable.normal
                        visible = true }) {
                        Text("Normal")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    OutlinedButton(onClick = { textshow.value  = "VIP 회원  \n" +
                            " 가격 : 4,900원 \n" +
                            " 사용기간 : 1개월 \n" +
                            " 데이터 등록 가능 인원 : 30명 \n" +
                            " 추가기능 : 고객 대시보드 기능"
                        imgshow = R.drawable.vip
                        visible = true}) {
                        Text("VIP")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    OutlinedButton(onClick = { textshow.value  = "VVIP 회원 \n" +
                            " 가격 : 19,900원 \n" +
                            " 사용기간 : 1년 \n" +
                            " 데이터 등록 가능 인원 : 무제한 \n" +
                            " 추가기능 : 고객대시보드 기능 & 영업 대시보드 기능"
                        imgshow = R.drawable.vvip
                        visible = true}) {
                        Text("VVIP")
                    }
                }

                Box(){
                    Column(){
                        Image(
                            painter = painterResource(imgshow),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.aspectRatio(100.dp/80.dp)
                        )
                        Text("\n")
                        Row() {
                            OutlinedButton(onClick = { /*TODO*/ },
                            ) {
                                Text(textshow.value)
                            }
                        }
                        Text("\n")
                    }
                }
            }
        }

        Row() {
            OutlinedButton(onClick = { /*TODO*/ }) {
                Text(text="구매")
            }
            OutlinedButton(onClick = { /*TODO*/ }) {
                Text(text="취소")
            }
        }
        Divider(color = Color.Gray, thickness = 2.dp)
    }
}
