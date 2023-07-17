package com.agstudio.tennisdata

import java.util.Calendar

open class BaseHistoryItem(
    var playerServed: Int = 0, //? vagy inkább egyszerűbben?
    var playerWonPoint: Int = 0,
    var pointRegisteredDate: Calendar = Calendar.getInstance()
)
