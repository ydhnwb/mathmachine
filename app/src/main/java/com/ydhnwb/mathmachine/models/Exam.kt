package com.ydhnwb.mathmachine.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Exam(var key : String?, var name : String?, var description : String?, var questions : List<Question>) : Parcelable {
    constructor() : this(null, null, null,  mutableListOf())
}


@Parcelize
data class Question (var key : String?, var answer : String?, var image : String?, var description: String?) : Parcelable {
    constructor() : this(null, null, null, null)
}