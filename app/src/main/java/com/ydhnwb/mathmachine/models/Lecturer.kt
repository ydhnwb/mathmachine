package com.ydhnwb.mathmachine.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Lecturer(var key : String?, var name : String?, var email : String?) : Parcelable{
    constructor() : this(null, null, null)
}