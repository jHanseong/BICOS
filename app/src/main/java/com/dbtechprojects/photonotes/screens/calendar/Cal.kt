package com.dbtechprojects.photonotes.screens.calendar

import android.os.Parcel
import android.os.Parcelable

data class Cal(
    val cal_title: String?, val cal_detail: String?,
    val cl_name: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),

    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cal_title)
        parcel.writeString(cal_detail)
        parcel.writeString(cl_name)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cal> {
        override fun createFromParcel(parcel: Parcel): Cal {
            return Cal(parcel)
        }

        override fun newArray(size: Int): Array<Cal?> {
            return arrayOfNulls(size)
        }
    }
}