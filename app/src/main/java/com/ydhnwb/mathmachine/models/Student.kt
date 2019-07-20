package com.ydhnwb.mathmachine.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Student(var key : String?, var name : String?, var name_lower : String?, var email : String?, var photoUrl : String ) : Parcelable {
    constructor() : this(null, null, null, null, "https://odtukaltev.com.tr/wp-content/uploads/2018/04/person-placeholder-300x300.jpg")
}