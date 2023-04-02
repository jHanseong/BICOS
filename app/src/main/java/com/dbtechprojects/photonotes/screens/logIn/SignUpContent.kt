package com.dbtechprojects.photonotes.screens.logIn

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dbtechprojects.photonotes.R
import com.dbtechprojects.photonotes.theme.Purple500
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.project.bicos_project.graphs.AuthScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SignUpContent(name: String, navController: NavHostController, goHome: () -> Unit) {

    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    var name by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var birth by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }

    var expanded by remember { mutableStateOf(false) }
    var selectedeItem by remember { mutableStateOf("성별") }
    var genderList  = listOf("남성", "여성")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "BICOS") }
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(R.drawable.register_img),
                contentDescription = "Register Image",
                modifier = Modifier
                    .width(300.dp)
                    .height(300.dp),
                contentScale = ContentScale.Fit
            )
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                scaffoldState = scaffoldState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(30.dp))
                        .background(Color.White)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
                        text = "회원가입",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primary
                    )
                        OutlinedTextField(
                            value = name,
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Face,
                                    contentDescription = "이름"
                                )
                            },
                            onValueChange = { name = it },
                            label = { Text(text = "이름") },
                            placeholder = { Text(text = "이름", color = Color.Black) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(0.8f),
                        )
                        OutlinedTextField(
                            value = gender,
                            onValueChange = {
                                gender = it
                            },
                            modifier = Modifier.fillMaxWidth(0.8f),
                            label = { Text(text = "성별") },
                            placeholder = { Text(text = "성별을 입력해주세요", color = Color.Black) },
                            leadingIcon = {
                                TextButton(onClick = { expanded = true },) {
                                    Row(modifier = Modifier.wrapContentWidth(Alignment.End)) {
                                        Text(text = "$selectedeItem")
                                        Icon(Icons.Default.ArrowDropDown, contentDescription = "")
                                    }
                                }
                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }) {
                                    genderList.forEach {
                                        DropdownMenuItem(onClick = {
                                            expanded = false
                                            selectedeItem = it
                                            gender = it
                                        }) {
                                            Text(text = it)
                                        }
                                    }
                                }
                            },
                        )
                        OutlinedTextField(
                            value = birth,
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Icon"
                                )
                            },
                            onValueChange = { birth = it },
                            label = { Text(text = "생년월일") },
                            placeholder = { Text(text = "생년월일", color = Color.Black) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(0.8f),
                        )
                        OutlinedTextField(
                            value = phone,
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Call,
                                    contentDescription = "Icon"
                                )
                            },
                            onValueChange = { phone = it },
                            label = { Text(text = "전화번호") },
                            placeholder = { Text(text = "전화번호", color = Color.Black) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(0.8f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        )
                        OutlinedTextField(
                            value = email,
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = "Icon"
                                )
                            },
                            onValueChange = { email = it },
                            label = { Text(text = "이메일") },
                            placeholder = { Text(text = "이메일", color = Color.Black) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(0.8f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        )
                        OutlinedTextField(
                            value = password,
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = "Icon"
                                )
                            },
                            onValueChange = { password = it },
                            label = { Text(text = "비밀번호") },
                            placeholder = { Text(text = "비밀번호", color = Color.Black) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(0.8f),
                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        passwordVisibility = !passwordVisibility
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_baseline_remove_red_eye_24),
                                        contentDescription = "Password Eye",
                                        tint = if (passwordVisibility) Purple500 else Color.Gray
                                    )
                                }
                            },
                            visualTransformation = if (passwordVisibility) VisualTransformation.None
                            else PasswordVisualTransformation()
                        )
                        OutlinedTextField(
                            value = confirmPassword,
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = "Icon"
                                )
                            },
                            onValueChange = { confirmPassword = it },
                            label = { Text(text = "비밀번호 확인") },
                            placeholder = { Text(text = "비밀번호 확인", color = Color.Black) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(0.8f),
                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        confirmPasswordVisibility =
                                            !confirmPasswordVisibility
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_baseline_remove_red_eye_24),
                                        contentDescription = "Password Eye",
                                        tint = if (confirmPasswordVisibility) Purple500 else Color.Gray
                                    )
                                }
                            },
                            visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None
                            else PasswordVisualTransformation()
                        )

                        Spacer(modifier = Modifier.padding(20.dp))
                        Button(colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary),
                            onClick = {
                                if (name.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "이름을 입력하세요",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if (gender.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "성별을 입력하세요",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if (birth.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "생년월일을 입력하세요",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if (phone.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "전화번호를 입력하세요",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if (email.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "이메일을 입력하세요",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if (password.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "비밀번호를 입력하세요",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if (confirmPassword.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "비밀번호를 확인해주세요",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "회원가입 되셨습니다!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    join_mail(
                                        name.toString(),
                                        gender.toString(),
                                        birth.toString(),
                                        phone.toString(),
                                        email.toString(),
                                        password.toString(),
                                        confirmPassword.toString(),
                                        navController,
                                        context
                                    )
//                                    goHome
                                }
                            }
                        ) {
                            Text(text = "회원가입 완료", fontSize = 20.sp, color = Color.White)
                        }
                        Spacer(modifier = Modifier.padding(30.dp))

//                        Text(text = "Login Instead" , color = Color.Black,
//                            modifier = Modifier.clickable {
//                                navController.navigate("login_page")
//                            }
                    }
                }

            }
        }
    }
fun join_mail(
    name: String,
    gender: String,
    birth: String,
    phone: String,
    email: String,
    password: String,
    confirmPassword: String,
    navController: NavHostController,
    context: Context
){
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    Log.d("이메일",email)
    val fbdb3 = Firebase.firestore
    val data = hashMapOf("name" to name, "gender" to gender, "birth" to birth, "phone" to phone, "email" to email, "password" to password, "confirmPassword" to confirmPassword,)
    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
            result ->
        if(result.isSuccessful){
            if(auth.currentUser!=null){
                val adocRef2 = fbdb3.collection("user").document(email.toString())
                adocRef2.set(data)
                navController.navigate(AuthScreen.Login.route)
            }
        }
    }.addOnCanceledListener {
        Toast.makeText(context,"회원가입 실패",Toast.LENGTH_SHORT).show()
    }
}