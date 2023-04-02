package com.dbtechprojects.photonotes.screens.client

import com.google.gson.annotations.SerializedName

data class Users2(
    @SerializedName("names")
    val names : String,
    @SerializedName("grade")
val grade : String,
@SerializedName("phnum")
val phnum : String,
@SerializedName("email")
val email : String,
@SerializedName("office")
val office : String,
@SerializedName("officenum")
val officenum : String,
@SerializedName("fax")
val fax : String,
)