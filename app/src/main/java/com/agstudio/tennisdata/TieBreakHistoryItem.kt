package com.agstudio.tennisdata

data class TieBreakHistoryItem (
    var points: ArrayList<Pair<Int, Int>> = ArrayList(),
    var servingPlayerIndex: Int = 0
) : BaseHistoryItem()