package com.dbtechprojects.photonotes.screens.client

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.dbtechprojects.photonotes.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.project.bicos_project.graphs.Graph
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.concurrent.TimeUnit

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddClientScreen(navController: NavHostController) {

    val context = LocalContext.current
    val result = remember { mutableStateOf<Bitmap?>(null) }
    var usersinfo = remember { mutableStateOf<Users2?>(null) }
    var imguri = remember { mutableStateOf<Uri?>(null) }

    val getimg = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()){
        if (it != null) {
            Log.d("이미지사이즈",it.height.toString())
            Log.d("이미지사이즈",it.width.toString())
            result.value = it
            var names = newJpgFileName()

            val path = context.filesDir
//            val path = MediaStore.Images.Media.RELATIVE_PATH

            val letDirectory = File(path,"image")
            Log.d("경로",path.toString())
            saveBitmapToFileCache(it, letDirectory.toString(), names, imguri, context)
//            plrease(names,letDirectory.toString(),usersinfo)
            Log.d("반환값",usersinfo.value.toString())
        } else {
            Toast(context, )
        }
    }

    var text by remember { mutableStateOf("") }
    Scaffold(
        modifier = Modifier.fillMaxSize(),

        topBar = {
            TopAppBar(
                title = { Text(text = "BICOS") },
            )
//        Button(onClick = {
//            val itt = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            rememberLauncherForActivityResult() },
//            shape = CircleShape ,
//            modifier = Modifier.padding(start = 330.dp),)
//        {
//            Image(painter = painterResource(id = R.drawable.camera), contentDescription = "Image" )
//        }
        }
    ){
        Column {
            ProfileImage(usersinfo , imguri)
            ClientInfo(usersinfo, imguri,navController)
        }
    }

}
//@Composable
//fun ScannerScreen() {
//    var resultUri by remember { mutableStateOf("") }
//    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        result.data?.let { intent ->
//            resultUri = intent.data.toString()
//        }
//    }
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
////        verticalArrangement = androidx.compose.ui.layout.VerticalArrangement.Center
//    ) {
//        if (resultUri.isNotEmpty()) {
//            Text(
//                text = "Selected image URI: $resultUri",
//                style = MaterialTheme.typography.h6,
//                modifier = Modifier.padding(16.dp)
//            )
//        }
//        Button(
//            onClick = { openDocumentScanner(launcher) },
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Text(text = "Scan document")
//        }
//    }
//}
//
//fun openDocumentScanner(launcher: ActivityResultLauncher<Intent>) {
//    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//    intent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true)
//    intent.putExtra(MediaStore.EXTRA_OUTPUT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//    launcher.launch(intent)
//}

@Composable
fun ClientInfo(usersinfo: MutableState<Users2?>, imguri: MutableState<Uri?>,navController:NavHostController) {
    val context = LocalContext.current

    var expanded by remember { mutableStateOf(false) }
    var selectedeItem by remember { mutableStateOf("성별") }
    var genderList = listOf("남성", "여성")

    var cl_name by remember { mutableStateOf("") }
    var cl_gender by remember { mutableStateOf("") }
    var cl_birth by remember { mutableStateOf("") }
    var cl_phone by remember { mutableStateOf("") }

    var cl_company by remember { mutableStateOf("") }
    var cl_rank by remember { mutableStateOf("") }
    var cl_email by remember { mutableStateOf("") }
    var cl_Cphone by remember { mutableStateOf("") }
    var cl_fax by remember { mutableStateOf("") }
    var data: HashMap<String, String>? = null

    if(usersinfo.value?.names != null){
        LaunchedEffect(true){
            if (usersinfo != null) {
                cl_name = usersinfo.value?.names.toString()
            }
            if (usersinfo != null) {
                cl_rank = usersinfo.value?.grade.toString()
            }
            if (usersinfo != null) {
                cl_phone = usersinfo.value?.phnum.toString()
            }
            if (usersinfo != null) {
                cl_email = usersinfo.value?.email.toString()
            }
            if (usersinfo != null) {
                cl_company = usersinfo.value?.office.toString()
            }
            if (usersinfo != null) {
                cl_Cphone = usersinfo.value?.officenum.toString()
            }
            if (usersinfo != null) {
                cl_fax = usersinfo.value?.fax.toString()
            }
        }
    }
    fun addContact(context : Context){

        val intent = Intent(ContactsContract.Intents.Insert.ACTION)
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE)

        intent.putExtra(ContactsContract.Intents.Insert.NAME, cl_name)
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, cl_phone)
        intent.putExtra(ContactsContract.Intents.Insert.COMPANY, cl_company)
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, cl_email)
        context.startActivity(intent)

    }

    data = hashMapOf("name" to cl_name,
        "gender" to cl_gender, "birth" to cl_birth,
        "phone" to cl_phone, "cl_company" to cl_company,"cl_rank" to cl_rank, "cl_email" to cl_email,"cl_Cphone" to cl_Cphone,"cl_fax" to cl_fax )

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Card(modifier = Modifier.padding(16.dp)) {
            Column {
                Text(
                    text = "고객 기본 정보",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.primary
                )
                OutlinedTextField(
                    value = cl_name,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Face,
                            contentDescription = "emailIcon"
                        )
                    },
                    onValueChange = {
                        cl_name = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "이름") },
                    placeholder = { Text(text = "이름을 입력하세요") },
                )
                OutlinedTextField(
                    value = cl_gender,
                    leadingIcon = {
                        TextButton(onClick = { expanded = true }) {
                            Row {
                                Text(text = "$selectedeItem")
                                Icon(Icons.Default.ArrowDropDown, contentDescription = "")
                            }
                        }
                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            genderList.forEach {
                                DropdownMenuItem(onClick = {
                                    expanded = false
                                    selectedeItem = it
                                    cl_gender = it
                                }) {
                                    Text(text = it)
                                }
                            }
                        }
                    },

                    onValueChange = {
                        cl_gender = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "성별") },
                    placeholder = { Text(text = "성별을 입력해주세요") },
                )
                OutlinedTextField(
                    value = cl_birth,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "dateIcon"
                        )
                    },

                    onValueChange = {
                        cl_birth = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "생년월일") },
                    placeholder = { Text(text = "생년월일을 입력해주세요") },
                )
                OutlinedTextField(
                    value = cl_phone,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "emailIcon"
                        )
                    },

                    onValueChange = {
                        cl_phone = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "전화번호") },
                    placeholder = { Text(text = "전화번호를 입력해주세요") },
                )
            }
        }
        Card(modifier = Modifier.padding(16.dp)) {
            Column {
                Text(
                    text = "고객 직업 정보",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.primary
                )
                OutlinedTextField(
                    value = cl_company,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "emailIcon"
                        )
                    },
                    onValueChange = {
                        cl_company = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "회사명") },
                    placeholder = { Text(text = "회사명을 입력하세요") },
                )
                OutlinedTextField(
                    value = cl_rank,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "emailIcon"
                        )
                    },

                    onValueChange = {
                        cl_rank = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "직급") },
                    placeholder = { Text(text = "직급을 입력해주세요") },
                )
                OutlinedTextField(
                    value = cl_email,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "emailIcon"
                        )
                    },

                    onValueChange = {
                        cl_email = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "이메일") },
                    placeholder = { Text(text = "이메일을 입력해주세요") },
                )
                OutlinedTextField(
                    value = cl_Cphone,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "emailIcon"
                        )
                    },

                    onValueChange = {
                        cl_Cphone = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "전화번호") },
                    placeholder = { Text(text = "전화번호를 입력해주세요") },
                )
                OutlinedTextField(
                    value = cl_fax,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "emailIcon"
                        )
                    },

                    onValueChange = {
                        cl_fax = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "팩스") },
                    placeholder = { Text(text = "팩스를 입력해주세요") },
                )
                Row (modifier = Modifier.padding(top = 16.dp)){
                    Button(onClick = {

                        addContact(context)
                    }, Modifier.size(width = 170.dp, height = 40.dp)) {
                        Image(
                            painterResource(id = R.drawable.phone_book),
                            colorFilter = ColorFilter.tint(Color.White),
                            contentDescription = "전화번호 등록"
                        )
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text(text = "전화번호 등록", fontSize = 15.sp)
                    }

                    Spacer(modifier = Modifier.padding(15.dp))

                    Button(onClick = {
                        saveUser(imguri , navController, data)
                    }, Modifier.size(width = 180.dp, height = 40.dp)) {
                        Image(
                            painterResource(id = R.drawable.save),
                            contentDescription = "고객정보 저장"
                        )
                        Spacer(modifier = Modifier.padding(10.dp))
                        Text(text = "고객정보 저장", fontSize = 15.sp)
                    }
                    Spacer(modifier = Modifier.padding(50.dp))
                }
            }
        }
    }
}
// 고객 등록하는 버튼 액션
fun saveUser(
    imguri: MutableState<Uri?>,
    navController: NavHostController,
    data: HashMap<String, String>
) {
    val Storge = FirebaseStorage.getInstance()
    val fbdb = Firebase.firestore.collection("data")
    var cl_email =""


    if (imguri.value == null){

    }else{
        var userInfo = Firebase.auth.currentUser
        userInfo?.let { it ->
            cl_email = it.email.toString()
            data.put("host",cl_email)
        }
        imguri.value?.let { Storge.reference.child("BusinessCard/").child(cl_email+data.get("name")).putFile(it).addOnCompleteListener {
            if(it.isSuccessful){
                fbdb.document(cl_email+data.get("name")).set(data).addOnSuccessListener {
                    navController.navigate(Graph.HOME)
                }

            }
        } }
    }


}

@Preview
@Composable
fun Preview() {
//    ClientInfo(usersinfo)
}
// 이미지 파일 이름 만들기
fun newJpgFileName() : String {
    val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
    val filename = sdf.format(System.currentTimeMillis())
    return "${filename}.jpg"
}
// 이미지 파일 저장
fun saveBitmapToFileCache(
    bitmap: Bitmap,
    strFilePath: String,
    fileName: String,
    imguri: MutableState<Uri?>,
    context: Context,

    ){
    val file = File(strFilePath)
    if (!file.exists()) file.mkdirs()
    val fileCacheItem = File(strFilePath+fileName)
    var out : OutputStream? = null
    try {
        var bitmap2 = rotate(bitmap,0)
        fileCacheItem.createNewFile()
        out = FileOutputStream(fileCacheItem)
        if (bitmap2 != null) {
            bitmap2.compress(Bitmap.CompressFormat.JPEG,100,out)
        }
        val path = MediaStore.Images.Media.insertImage(
            context.contentResolver,
            bitmap2,
            "Title",
            null
        )
        imguri.value = Uri.parse(path)


    }catch (e:Exception){
        e.printStackTrace()
    }finally {
        try {
            out?.close()
        }catch (e:IOException){
            e.printStackTrace()
        }
    }
}

fun plrease(fileName: String, strFilePath: String, usersinfo: MutableState<Users2?>){

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(100, TimeUnit.SECONDS)
        .readTimeout(100, TimeUnit.SECONDS)
        .writeTimeout(100, TimeUnit.SECONDS)
        .build()

    val retrofit = Retrofit.Builder().baseUrl("http://8622-34-150-137-173.ngrok.io/").client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build();
    val service = retrofit.create(RetrofitService::class.java);
    val file = File(strFilePath+fileName)



    Log.d("files",file.toString())
    Log.d("files",strFilePath+fileName)
    // RequestBody로 Multipart.Part 객체 생성
    val body = file.asRequestBody()
    val emptyPart = MultipartBody.Part.createFormData("test",fileName,body)
    Log.d("멀티팔트",emptyPart.body.toString())

    service.post_profile(emptyPart).enqueue(object :Callback<Users2>{
        override fun onResponse(call: Call<Users2>, response: Response<Users2>) {
//            Log.d("if안들어감","안들어감?")
            if(response.isSuccessful){
                Log.d("서버","성공이냐?")
                usersinfo.value = response.body()!!
            }
            else{
                Log.d("서버",response.toString())
            }
        }

        override fun onFailure(call: Call<Users2>, t: Throwable) {
//            Log.d("서버","실패")
            Log.d("서버",t.message.toString())
        }

    })
}

@Composable
fun ProfileImage(usersinfo: MutableState<Users2?>, imguri: MutableState<Uri?>) {
    val context = LocalContext.current
    val imageUri = rememberSaveable { mutableStateOf("") }
    val painter = rememberAsyncImagePainter(
        imageUri.value.ifEmpty { R.drawable.plus },
    )
    var bitmap4 : Bitmap? = null
    Modifier.fillMaxSize()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { imageUri.value = it.toString() }
        bitmap4 = MediaStore.Images.Media.getBitmap(context.contentResolver,uri)
        if( bitmap4 !=null){

            Log.d("이미지사이즈", bitmap4!!.height.toString())
            Log.d("이미지사이즈",bitmap4!!.width.toString())
            var names = newJpgFileName()

            val path = context.filesDir
//            val path = MediaStore.Images.Media.RELATIVE_PATH

            val letDirectory = File(path,"image")
            Log.d("경로",path.toString())
            saveBitmapToFileCache(bitmap4!!,letDirectory.toString(),names,imguri,context)
            plrease(names,letDirectory.toString(),usersinfo)
            Log.d("반환값",usersinfo.value.toString())
        }



    }

    Column(
        modifier = Modifier
//            .padding(8.dp)
            .fillMaxWidth(),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RectangleShape,
            modifier = Modifier
                .padding(8.dp)
                .size(width = 400.dp, height = 200.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { launcher.launch("image/*") },
                contentScale = ContentScale.Crop
            )
        }
    }
}



fun rotate(bitmap: Bitmap?, degrees: Int): Bitmap? { // 이미지 회전 및 이미지 사이즈 압축
    var bitmap = bitmap
    if (degrees != 0 && bitmap != null) {
        val m = Matrix()
        m.setRotate(degrees.toFloat(), bitmap.width.toFloat() / 1,
            bitmap.height.toFloat() / 1)
        try {
            val converted = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.width, bitmap.height, m, true)
            if (bitmap != converted) {
                bitmap.recycle()
                bitmap = converted
                val options = BitmapFactory.Options()
                options.inSampleSize = 4
                bitmap = Bitmap.createScaledBitmap(bitmap, 4032, 3024, true) // 이미지 사이즈 줄이기
            }
        } catch (ex: OutOfMemoryError) {
            // 메모리가 부족하여 회전을 시키지 못할 경우 그냥 원본을 반환합니다.
        }
    }
    return bitmap
}
