package com.agstudio.tennisdata

import java.text.DateFormat

open class Match(var numberOfSets: Int, var date: DateFormat, var result: MatchResult? = null) {

    var player1PointsInGame = TennisPoints.ZERO
    var player2PointsInGame = TennisPoints.ZERO
    private var activeSet = 0
    private var isMatchEnded = false
    private var sets: ArrayList<Pair<Int, Int>> = ArrayList() //0 index player1, 1 index player2
    private var tieBreak: Pair<Int, Int>? = null

    fun startTheMatch() {
        for (i in 0 until numberOfSets) {
            sets.add(Pair(0, 0))
        }
        activeSet = 1
    }

    fun getPlayer1Sets(): List<Int> {
        val list = ArrayList<Int>()
        sets.forEach { set ->
            list.add(set.first)
        }
        return list
    }

    fun getPlayer2Sets(): List<Int> {
        val list = ArrayList<Int>()
        sets.forEach { set ->
            list.add(set.second)
        }
        return list
    }

    fun getPlayer1Points(): String {
        return if (tieBreak == null) {
            when(player1PointsInGame) {
                TennisPoints.ZERO -> "0"
                TennisPoints.FIFTEEN -> "15"
                TennisPoints.THIRTY -> "30"
                TennisPoints.FORTY -> "40"
                TennisPoints.ADVANTAGE -> "AD"
                TennisPoints.DISADVANTAGE -> ""
                TennisPoints.GAME -> "_"
            }
        } else {
            tieBreak!!.first.toString()
        }
    }

    fun getPlayer2Points(): String {
        return if (tieBreak == null) {
            when(player2PointsInGame) {
                TennisPoints.ZERO -> "0"
                TennisPoints.FIFTEEN -> "15"
                TennisPoints.THIRTY -> "30"
                TennisPoints.FORTY -> "40"
                TennisPoints.ADVANTAGE -> "AD"
                TennisPoints.DISADVANTAGE -> ""
                TennisPoints.GAME -> "_"
            }
        } else {
            tieBreak!!.second.toString()
        }
    }

    fun addPointToPlayer1() {
        if (!inTiebreak()) {
            player1PointsInGame = getPointWinnerPlayerTennisPoints(player1PointsInGame)
            player2PointsInGame = getPointLoserPlayerTennisPoints(player2PointsInGame, 0)
            if (player1PointsInGame == TennisPoints.GAME) {
                player1PointsInGame = TennisPoints.ZERO
                player2PointsInGame = TennisPoints.ZERO
//                if (!actualSetIsDone(sets[activeSet - 1])) {
                    sets[activeSet - 1] = Pair(sets[activeSet - 1].first + 1, sets[activeSet - 1].second)
//                } else {
//                    activeSet += 1
//                    isMatchEnded = activeSet > numberOfSets
//                    tieBreak = null
//                }
            }
        } else {
            if (tieBreak == null) {
                tieBreak = Pair(0, 0)
            }
            tieBreak?.let {
                tieBreak = Pair(it.first + 1, it.second)
                if (isTieBreakEnded()) {
                    startNewSetWithPlayer1WonLastSet()
                }
            }
        }
        if (actualSetIsDone(sets[activeSet - 1])) {
            activeSet += 1
            isMatchEnded = activeSet > numberOfSets
            tieBreak = null
        }
    }

    fun addPointToPlayer2() {
        if (!inTiebreak()) {
            player2PointsInGame = getPointWinnerPlayerTennisPoints(player2PointsInGame)
            player1PointsInGame = getPointLoserPlayerTennisPoints(player1PointsInGame, 1)
            if (player2PointsInGame == TennisPoints.GAME) {
                player1PointsInGame = TennisPoints.ZERO
                player2PointsInGame = TennisPoints.ZERO
                sets[activeSet - 1] = Pair(sets[activeSet - 1].first, sets[activeSet - 1].second + 1)
            }
        } else {
            if (tieBreak == null) {
                tieBreak = Pair(0, 0)
            }
            tieBreak?.let {
                tieBreak = Pair(it.first, it.second + 1)
                if (isTieBreakEnded()) {
                    startNewSetWithPlayer2WonLastSet()
                }
            }
        }
        if (actualSetIsDone(sets[activeSet - 1])) {
            activeSet += 1
            isMatchEnded = activeSet > numberOfSets
            tieBreak = null
        }
    }

    private fun startNewSetWithPlayer1WonLastSet() {
        player1PointsInGame = TennisPoints.ZERO
        player2PointsInGame = TennisPoints.ZERO
        sets[activeSet - 1] = Pair(sets[activeSet - 1].first + 1, sets[activeSet - 1].second)
    }

    private fun startNewSetWithPlayer2WonLastSet() {
        player1PointsInGame = TennisPoints.ZERO
        player2PointsInGame = TennisPoints.ZERO
        sets[activeSet - 1] = Pair(sets[activeSet - 1].first, sets[activeSet - 1].second + 1)
    }

    private fun isTieBreakEnded(): Boolean {
        tieBreak?.let {
            return (it.first > 6 && it.first - it.second > 1) || (it.second > 6 && it.second - it.first > 1)
        }
        return false
    }

    private fun getPointWinnerPlayerTennisPoints(actualPoints: TennisPoints): TennisPoints {
        return when(actualPoints) {
            TennisPoints.ZERO -> {
                TennisPoints.FIFTEEN
            }
            TennisPoints.FIFTEEN -> {
                TennisPoints.THIRTY
            }
            TennisPoints.THIRTY -> {
                TennisPoints.FORTY
            }
            TennisPoints.FORTY -> {
                if (player1PointsInGame == TennisPoints.FORTY && player2PointsInGame == TennisPoints.FORTY) {
                    TennisPoints.ADVANTAGE
                } else {
                    TennisPoints.GAME
                }
            }
            TennisPoints.ADVANTAGE -> {
                TennisPoints.GAME
            }
            TennisPoints.DISADVANTAGE -> {
                TennisPoints.FORTY
            }
            TennisPoints.GAME ->  {
                TennisPoints.GAME
            }
        }
    }

    private fun getPointLoserPlayerTennisPoints(actualPoints: TennisPoints, playerIndex: Int): TennisPoints {
        when(actualPoints) {
            TennisPoints.FORTY -> {
                val otherPlayerTennisPoints = if(playerIndex == 1) {
                    player2PointsInGame
                } else {
                    player1PointsInGame
                }
                if (otherPlayerTennisPoints == TennisPoints.ADVANTAGE) {
                    return TennisPoints.DISADVANTAGE
                }
            }
            TennisPoints.ADVANTAGE -> {
                return TennisPoints.FORTY
            }
        }
        return actualPoints
    }

    private fun inTiebreak(): Boolean {
        return sets[activeSet - 1].first == 6 && sets[activeSet - 1].second == 6
    }

    private fun actualSetIsDone(set: Pair<Int, Int>): Boolean {
        return ((set.first == 6 && set.second <= 4) ||
                (set.second == 6 && set.first <= 4) ||
                (set.first == 7) ||
                (set.second == 7))
    }

    fun addNewSet() {}
}
