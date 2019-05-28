package com.ydhnwb.mathmachine.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Lesson(var key : String?, var name : String?, var description : String?, var moduleUrl : String?) : Parcelable {
    constructor() : this(null, null, null, null)
}