package com.agstudio.tennisdata

import java.text.DateFormat

class SingleMatch
    @JvmOverloads constructor(
    numberOfSets: Int,
    date: DateFormat,
    result: MatchResult? = null,
    player1: Player,
    player2: Player
) : Match (numberOfSets, date, result) {

//    fun startTheMatch() {}
//
//    fun addPointToPlayer1() {}
//
//    fun addPointToPlayer2() {}

}