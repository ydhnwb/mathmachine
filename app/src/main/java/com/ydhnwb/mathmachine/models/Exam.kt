package com.ydhnwb.mathmachine.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Exam(var key : String?, var name : String?, var description : String?, var questions : List<Question>, var status : Boolean) : Parcelable {
    constructor() : this(null, null, null,  mutableListOf(), false)
}


@Parcelize
data class Question (var key : String?, var answer : String?, var image : String?, var description: String?, var choosedAnswer : String?) : Parcelable {
    constructor() : this(null, null, null, null, null)
}

@Parcelize
data class Score (var key: String?, var score : Int, var life : Int?) : Parcelable{
    constructor() : this(null, 0, 0)
}