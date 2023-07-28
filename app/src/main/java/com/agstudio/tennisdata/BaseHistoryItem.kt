package com.agstudio.tennisdata

import java.util.Calendar

open class BaseHistoryItem(
    var playerServed: Int = 0,
    var pointWinnerPlayerIndexes: ArrayList<Int> = ArrayList(),
    var playerWonPoint: Int = 0,
    var pointsRegisteredDate: ArrayList<Calendar> = ArrayList()
)
