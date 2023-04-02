package com.dbtechprojects.photonotes.screens.logIn

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dbtechprojects.photonotes.R
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginContent(
    loginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotClick: () -> Unit
) {
    val context = LocalContext.current

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }
//    val isFormValid by derivedStateOf {
//        email.isNotBlank() && password.length >= 7
//        login_mail(email, password)
//    }

    Scaffold(backgroundColor = MaterialTheme.colors.primary) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.bicos),
                contentDescription = "App Logo",
                modifier = Modifier
                    .weight(1f)
                    .size(200.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
            Card(
                Modifier
                    .weight(2f)
                    .padding(8.dp),
                shape = RoundedCornerShape(32.dp)
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(32.dp)
                ) {
                    Text(text = "환영합니다!", fontWeight = FontWeight.Bold, fontSize = 32.sp)
                    Column(
                        Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.weight(1f))

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = email,
                            onValueChange = { email = it },
                            label = { Text(text = "아이디") },
                            singleLine = true,
                            trailingIcon = {
                                if (email.isNotBlank())
                                    IconButton(onClick = { email = "" }) {
                                        Icon(
                                            imageVector = Icons.Filled.Clear,
                                            contentDescription = ""
                                        )
                                    }
                            }
                        )
                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )
                        OutlinedTextField(
                            password, { password = it }, Modifier.fillMaxWidth(),
                            label = { Text(text = "비밀번호") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            {
                                if(!email.equals("")and !password.equals("")) {
                                    login_mail(email, password, context, loginClick)
                                }else{
                                    Toast.makeText(context, "이메일과 비밀번호를 작성해주세요", Toast.LENGTH_SHORT).show()
                                    Log.d("로그인","실패")
                                }

                            },
                            // 로그인 하고 싶으면 위에 주석풀고 밑에 loginClick지우면 됨
//                            loginClick,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                text = "로그인"
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextButton(
                                onSignUpClick) {
                                Text(text = "회원가입")
                            }
                            TextButton(
                                onForgotClick) {
                                Text(text = "비밀번호 찾기", color = Color.Gray)
                            }
                        }
                    }
                }
            }
        }
    }
}

fun login_mail(email:String, password:String, context: Context, loginClick: () -> Unit){
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener {
                result->
            if(result.isSuccessful){
                Log.d("로그인","성공")
                Log.d("이메일 아이디",email)
                loginClick()
            }
            else {
                Toast.makeText(context, "이메일형식을 지켜서 올바르게 입력해주세요", Toast.LENGTH_SHORT).show()
                Log.d("로그인","실패")
            }

        }
        .addOnCanceledListener {
            Toast.makeText(context, "이메일형식을 지켜서 올바르게 입력해주세요", Toast.LENGTH_SHORT).show()
            Log.d("로그인","실패")
        }
}
