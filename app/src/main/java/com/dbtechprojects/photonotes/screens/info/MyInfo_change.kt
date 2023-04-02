package com.dbtechprojects.photonotes.screens.info

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.dbtechprojects.photonotes.graphs.DetailsScreen
import com.dbtechprojects.photonotes.screens.client.ProfileImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun MyInfo_change(navController: NavHostController){
    var text by remember { mutableStateOf("") }
    Column(
    ) {
        Info(navController)
    }
}
fun change_info(cl_name:String,cl_gender:String,cl_birth:String,cl_phone:String,cl_email:String){
    val fbdb = Firebase.firestore.collection("user")
    val data = hashMapOf("name" to cl_name, "gender" to cl_gender, "birth" to cl_birth, "phone" to cl_phone, "email" to cl_email)
    fbdb.document(cl_email).set(data)

}
@Composable
fun Info(navController: NavHostController) {
    val fbdb = Firebase.firestore.collection("user")
    var cl_name by remember { mutableStateOf("") }
    var cl_gender by remember { mutableStateOf("") }
    var cl_birth by remember { mutableStateOf("") }
    var cl_phone by remember { mutableStateOf("") }
    var cl_email by remember { mutableStateOf("") }

    var userInfo = Firebase.auth.currentUser
    userInfo?.let { it ->
        cl_email = it.email.toString()
    }
    fbdb.document(cl_email).get().addOnCompleteListener { it ->
        var data = it.getResult()
        cl_gender = data.get("gender").toString()
        cl_name = data.get("name").toString()
        cl_birth= data.get("birth").toString()
        cl_phone = data.get("phone").toString()
    }
    Column(
        modifier = Modifier.padding(60.dp)) {
//        ProfileImage()
        OutlinedTextField(
            value = cl_name,
            onValueChange = { cl_name = it },
            label = { Text("이름") },
            maxLines = 1,
            textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
            modifier = Modifier,
            enabled = false
        )
        OutlinedTextField(
            value = cl_gender,
            onValueChange = { cl_gender = it },
            label = { Text("성별") },
            enabled = false
        )
        OutlinedTextField(
            value = cl_birth,
            onValueChange = { cl_birth = it },
            label = { Text("생년월일") },
            enabled = false
        )
        OutlinedTextField(
            value = cl_email,
            onValueChange = { cl_email = it },
            label = { Text("이메일") },
            enabled = false
        )
        OutlinedTextField(
            value = cl_phone,
            onValueChange = { cl_phone = it },
            label = { Text("전화번호") }
        )
        Row() {
            Row(){
                val context = LocalContext.current
                Button(onClick = {
                    change_info(cl_name,cl_gender,cl_birth,cl_phone,cl_email)
                    Toast.makeText(context,"개인정보를 수정하였습니다.",Toast.LENGTH_SHORT).show()
                    navController.navigate(DetailsScreen.Chprofilecl.route)
                },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Unspecified,
                        contentColor = Color.Gray,
                    ),
                    modifier = Modifier.wrapContentSize(),
                ) {
                    Text(text = "수정")
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
}