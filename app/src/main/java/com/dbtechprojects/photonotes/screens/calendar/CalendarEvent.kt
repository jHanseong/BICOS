package com.dbtechprojects.photonotes.screens.calendar

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dbtechprojects.photonotes.R
import com.dbtechprojects.photonotes.graphs.DetailsScreen
import com.dbtechprojects.photonotes.screens.client.Users2
import com.dbtechprojects.photonotes.screens.logIn.join_mail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.project.bicos_project.graphs.Graph
import java.util.*
import kotlin.collections.HashMap


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CalendarEvent(
    navController: NavHostController

) {
    var usersinfo = remember { mutableStateOf<Days?>(null) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "BICOS") }
            )
        }
    )
    {
        CalEvent(navController,usersinfo)
    }
}

@Composable
fun CalEvent(
    navController: NavHostController,
    usersinfo: MutableState<Days?>


){

    var cal_title by remember { mutableStateOf("") }
    var cal_detail by remember { mutableStateOf("") }
    var cl_name by remember { mutableStateOf("") }
    var cal_location by remember { mutableStateOf("") }
//    var ck_time1 by remember { mutableStateOf("") }
//    var ck_time2 by remember { mutableStateOf("") }
    var data: HashMap<String, String>? = null
    //add
    if(usersinfo.value?.cal_title != null){
        LaunchedEffect(true) {
            if (usersinfo != null) {
                cal_title = usersinfo.value?.cal_title.toString()
            }
            if (usersinfo != null) {
                cal_detail = usersinfo.value?.cal_detail.toString()
            }
            if (usersinfo != null) {
                cl_name = usersinfo.value?.cl_name.toString()
            }
        }


    }
    data = hashMapOf("cal_title" to cal_title,
        "cal_detail" to cal_detail, "cl_name" to cl_name)
    Column(
        modifier = Modifier
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        OutlinedTextField(
            value = cal_title,
            onValueChange = {
                cal_title = it
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            label = { Text(text = "제목") },
        )
        Column {
            Row (modifier = Modifier.padding(top = 16.dp)) {
                Image(
                    painterResource(id = R.drawable.time),
                    contentDescription = "시간",
                    modifier = Modifier
                        .size(25.dp)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(text = "종일", fontSize = 14.sp)
                Modifier.padding(top = 10.dp)
                Modifier.padding(16.dp)
            }
            DatePicker(usersinfo)
        }
        OutlinedTextField(
            value = cal_detail,
            onValueChange = {
                cal_detail = it
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            label = { Text(text = "내용") },
        )
        OutlinedTextField(
            value = cl_name,
            onValueChange = {
                cl_name = it
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            label = { Text(text = "고객") },
        )
        OutlinedTextField(
            value = cal_location,
            onValueChange = {
                cal_location = it
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            label = { Text(text = "위치") },
            leadingIcon = {
                Icon(
                    Icons.Default.Place,
                    contentDescription = null,
                    modifier = Modifier
//                        .clickable(
//                            enabled = true,
//                            role = Role.Button,
////                            onClick = {navController.navigate(DetailsScreen.MapDetail.route)}
//                        )
                )
            },
        )
        //
        Spacer(modifier = Modifier.padding(10.dp))
        Button(colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary),
            onClick = {
//                datememo(
//                    cal_title.toString(),
//                    cal_detail .toString(),
//                    cl_name .toString(),
//
//                )
                saveDate(navController, data = HashMap(data))
            }
        ) {
            Text(text = "일정 추가", fontSize = 20.sp, color = Color.White)
        }
        Spacer(modifier = Modifier.padding(30.dp))

//                        Text(text = "Login Instead" , color = Color.Black,
//                            modifier = Modifier.clickable {
//                                navController.navigate("login_page")
//                            }
    }
}

@Composable
fun DatePicker(
    usersinfo: MutableState<Days?>,

    ) {


    val calendar = Calendar.getInstance()
    var selectedDate by rememberSaveable{ mutableStateOf( "" ) }
    var selectedDate2 by rememberSaveable{ mutableStateOf( "" ) }
    var selectedTime by rememberSaveable{ mutableStateOf( "" ) }
    var selectedTime2 by rememberSaveable{ mutableStateOf( "" ) }

    val pickedDate = dateStringParser( calendar, selectedDate)
    val pickedDate2 = dateStringParser( calendar, selectedDate2)
    val pickedTime = timeStringParser( calendar, selectedTime)
    val pickedTime2 = timeStringParser( calendar, selectedTime2)

    val onSelectionChangedDate = DatePickerDialog.OnDateSetListener {
            _, year, month, dayOfMonth ->
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = month
        calendar[Calendar.DAY_OF_MONTH] = dayOfMonth

        selectedDate = "${calendar[Calendar.YEAR]}/${calendar[Calendar.MONTH] + 1}/${calendar[Calendar.DAY_OF_MONTH]}"
    }

    val onSelectionChangedDate2 = DatePickerDialog.OnDateSetListener {
            _, year, month, dayOfMonth ->
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = month
        calendar[Calendar.DAY_OF_MONTH] = dayOfMonth

        selectedDate2 = "${calendar[Calendar.YEAR]}/${calendar[Calendar.MONTH] + 1}/${calendar[Calendar.DAY_OF_MONTH]}"
    }

    val onSelectionChangedTime = TimePickerDialog.OnTimeSetListener {
            _, hourOfDay, minute ->
        calendar[Calendar.HOUR] = hourOfDay
        val AM_PM: String
        AM_PM = if (hourOfDay < 12) {
            "AM"
        } else {
            "PM"
        }

        calendar[Calendar.MINUTE] = minute
        AM_PM

        selectedTime = "${AM_PM}_${calendar[Calendar.HOUR]}시${calendar[Calendar.MINUTE]}분"

    }

    val onSelectionChangedTime2 = TimePickerDialog.OnTimeSetListener {
            _, hourOfDay, minute ->
        calendar[Calendar.HOUR] = hourOfDay
        val AM_PM: String
        AM_PM = if (hourOfDay < 12) {
            "AM"
        } else {
            "PM"
        }

        calendar[Calendar.MINUTE] = minute
        AM_PM


        selectedTime2 = "${AM_PM}_${calendar[Calendar.HOUR]}시${calendar[Calendar.MINUTE]}분"
    }

    val onTextChangedDate : ( String ) -> Unit = { newValue -> selectedDate = newValue }
    val onTextChangedDate2 : ( String ) -> Unit = { newValue -> selectedDate2 = newValue }
    val onTextChangedTime : ( String ) -> Unit = { newValue -> selectedTime = newValue }
    val onTextChangedTime2 : ( String ) -> Unit = { newValue -> selectedTime2 = newValue }



    val dialogdate = DatePickerDialog(
        LocalContext.current,
        onSelectionChangedDate,
        pickedDate[0],
        pickedDate[2],
        pickedDate[1],

        )
    val dialogdate2 = DatePickerDialog(
        LocalContext.current,
        onSelectionChangedDate2,
        pickedDate2[0],
        pickedDate2[2],
        pickedDate2[1],

        )

    val dialogtime = TimePickerDialog(
        LocalContext.current,
        onSelectionChangedTime,
        pickedTime[0],
        pickedTime[1],
        false


    )
    val dialogtime2 = TimePickerDialog(
        LocalContext.current,
        onSelectionChangedTime2,
        pickedTime2[0],
        pickedTime2[1],
        false


    )

    val onClickedDate = {
        dialogdate.show()
    }
    val onClickedDate2 = {
        dialogdate2.show()
    }
    val onClickedTime = {
        dialogtime.show()
    }

    val onClickedTime2 = {
        dialogtime2.show()
    }

    var data: HashMap<String, String>? = null
    //add
    if(usersinfo.value?.cal_title != null){
        LaunchedEffect(true) {
            if (usersinfo != null) {
                selectedTime = usersinfo.value?.selectedTime.toString()
            }
            if (usersinfo != null) {
                selectedTime2 = usersinfo.value?.selectedTime2.toString()
            }

        }


    }
    data = hashMapOf("selectedTime" to selectedTime,
        "selectedTime2" to selectedTime2)
    Column() {


        OutlinedTextField(
            value = selectedDate,
            onValueChange = onTextChangedDate,
            label = { Text("날짜 선택") },
            leadingIcon = {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable(
                            enabled = true,
                            role = Role.Button,
                            onClick = onClickedDate
                        )
                )
            },
            readOnly = false,
            modifier = Modifier
        )
        OutlinedTextField(
            value = selectedDate2,
            onValueChange = onTextChangedDate2,
            label = { Text("날짜 선택") },
            leadingIcon = {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable(
                            enabled = true,
                            role = Role.Button,
                            onClick = onClickedDate2
                        )
                )
            },
            readOnly = false,
            modifier = Modifier
        )
        OutlinedTextField(

            value = selectedTime,
            onValueChange = onTextChangedTime,
            label = { Text("시간 선택") },
            leadingIcon = {
                Icon(
                    Icons.Default.Call,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable(
                            enabled = true,
                            role = Role.Button,
                            onClick = onClickedTime
                        )
                )
            },
            readOnly = false,
            modifier = Modifier
        )
        OutlinedTextField(
            value = selectedTime2,
            onValueChange = onTextChangedTime2,
            label = { Text("시간 선택") },
            leadingIcon = {
                Icon(
                    Icons.Default.Call,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable(
                            enabled = true,
                            role = Role.Button,
                            onClick = onClickedTime2
                        )
                )
            },
            readOnly = false,
            modifier = Modifier

        )

    }
}
fun dateStringParser( calendar: Calendar, date: String ): Array<Int> {

    var day = calendar[Calendar.DAY_OF_MONTH]
    var month = calendar[Calendar.MONTH]
    var year = calendar[Calendar.YEAR]

    Log.d("년도체크",""+year)

    if( date.isNotEmpty() && date.isNotBlank() && date.contains( "/" )) {
        val breakdown = date.split( "/" )

        if( breakdown.count() == 3 &&
            breakdown[0].isNotEmpty() && breakdown[0].isNotBlank() &&
            breakdown[1].isNotEmpty() && breakdown[1].isNotBlank() &&
            breakdown[2].isNotEmpty() && breakdown[2].isNotBlank()
        ) {
//                day = breakdown[1].toInt()
//                month = breakdown[0].toInt() - 1
//                year = breakdown[2].toInt()
            year = breakdown[0].toInt()
            month = breakdown[1].toInt()
            day = breakdown[2].toInt()
            Log.d("년+1",""+year)
            Log.d("월+2",""+month)
            Log.d("일+3",""+day)
        }
    }

    return arrayOf( year, month, day )
}
fun timeStringParser( calendar: Calendar, time: String ): Array<Int> {

    var hour = calendar[Calendar.HOUR_OF_DAY]
    var minute = calendar[Calendar.MINUTE]
    var ampm = calendar[Calendar.AM_PM]

    if( time.isNotEmpty() && time.isNotBlank() && time.contains( "/" )) {
        val breakdown = time.split( "/" )

        if( breakdown.count() == 3 &&
            breakdown[0].isNotEmpty() && breakdown[0].isNotBlank() &&
            breakdown[1].isNotEmpty() && breakdown[1].isNotBlank() &&
            breakdown[2].isNotEmpty() && breakdown[2].isNotBlank()
        ) {
            hour = breakdown[1].toInt()
            minute = breakdown[0].toInt() - 1
            ampm = breakdown[2].toInt()
        }
    }
    return arrayOf( hour, minute, ampm )
}

fun saveDate(
    navController: NavHostController,
    data: HashMap<String, String>
) {
    val fbdb = Firebase.firestore.collection("date")
    var cl_email = ""

    var userInfo = Firebase.auth.currentUser
    userInfo?.let { it ->
        cl_email = it.email.toString()
        data.put("host", cl_email)
        fbdb.document(data.get("cal_title").toString()).set(data).addOnSuccessListener {
            navController.navigate(Graph.HOME)
        }
    }
}