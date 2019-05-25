package com.ydhnwb.mathmachine.models

data class Lesson(var key : String?, var name : String?, var description : String?, var moduleUrl : String?) {
    constructor() : this(null, null, null, null)
}