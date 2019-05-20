package com.example.myapplication

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Calculation(val weight: Int, val height: Int, val bmi: Double, val weightUnit: String?, val heightUnit: String?, val date: String? = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(weight)
        parcel.writeInt(height)
        parcel.writeDouble(bmi)
        parcel.writeString(weightUnit)
        parcel.writeString(heightUnit)
        parcel.writeString(date)
    }
    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<Calculation> {
        override fun createFromParcel(parcel: Parcel): Calculation {
            return Calculation(parcel)
        }

        override fun newArray(size: Int): Array<Calculation?> {
            return arrayOfNulls(size)
        }
    }
}