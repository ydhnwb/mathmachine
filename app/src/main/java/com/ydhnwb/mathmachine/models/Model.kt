package com.ydhnwb.mathmachine.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class MainLesson(var key : String?, var name : String?, var name_idiomatic : String?, var description : String?) : Parcelable{
    constructor() : this(null, null, null ,null)
}


@Parcelize
data class Lesson(var key : String?, var name : String?, var name_idiomatic: String?, var description : String?, var moduleUrl : String?) : Parcelable {
    constructor() : this(null, null, null, null, null)
}