package com.agstudio.tennisdata

data class GameHistoryItem(
    val player1Points: ArrayList<TennisPoints> = ArrayList(),
    var player2Points: ArrayList<TennisPoints> = ArrayList()
) : BaseHistoryItem()