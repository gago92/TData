package com.agstudio.tennisdata

import java.util.Calendar

data class MatchSettings(
    val isSingle: Boolean, //or double
    val winnerSetsNumber: Int,
    val firstPlayerName: String,
    val secondPlayerName: String,
    val isFirstPlayerStartServe: Boolean,
    val hasTiebreak: Boolean, //kettovel nyerni?
    val lastSetTiebreakSuperTiebreakWithTwoSingle: Boolean, //enum
    val numberOfGameInSets: Int,
    val gameEndWithTwo: Boolean,
    val timeOfStart: Calendar
)