package com.agstudio.tennisdata

data class TieBreakHistoryItem (
    var points: ArrayList<Pair<Int, Int>> = ArrayList()
) : BaseHistoryItem()