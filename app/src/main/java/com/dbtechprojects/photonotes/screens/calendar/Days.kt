package com.dbtechprojects.photonotes.screens.calendar

import com.google.gson.annotations.SerializedName

data class Days(
    @SerializedName("cal_title")
    val cal_title  : String,

    @SerializedName("cal_detail")
val cal_detail  : String,

@SerializedName("cl_name")
val cl_name  : String,

@SerializedName("selectedTime")
val selectedTime  : String,

@SerializedName("selectedTime2")
val selectedTime2  : String,

)