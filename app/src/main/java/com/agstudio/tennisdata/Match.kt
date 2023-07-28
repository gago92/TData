package com.agstudio.tennisdata

import java.lang.RuntimeException
import java.text.DateFormat

open class Match(private val settings: MatchSettings) {

    private var isMatchEnded = false // kell?
    private val points: Array<TennisPoints> = arrayOf(TennisPoints.ZERO, TennisPoints.ZERO)
    private var sets: ArrayList<Score> = ArrayList()
    private var tieBreak: Score? = null

    fun startTheMatch() {
        points[0] = TennisPoints.ZERO
        points[1] = TennisPoints.ZERO
        sets.add(Score(0, 0, settings.isFirstPlayerStartServe))
    }

    fun getRecyclerViewAdapterDataForPlayer(indexOfPlayer: Int): ArrayList<String> {
        val list: ArrayList<String> = ArrayList()
        sets.forEach { score ->
            if (indexOfPlayer == 0) {
                list.add(score.firstPlayerPoint.toString())
            } else {
                list.add(score.secondPlayerPoint.toString())
            }
        }
        return list
    }

    fun getPlayerPoints(indexOfPlayer: Int): String {
        return if (tieBreak == null) {
            when(points[indexOfPlayer]) {
                TennisPoints.ZERO -> "0"
                TennisPoints.FIFTEEN -> "15"
                TennisPoints.THIRTY -> "30"
                TennisPoints.FORTY -> "40"
                TennisPoints.ADVANTAGE -> "AD"
                TennisPoints.DISADVANTAGE -> ""
                TennisPoints.GAME -> "_"
            }
        } else {
            tieBreak?.let { t ->
                return if (indexOfPlayer == 0) {
                    t.firstPlayerPoint.toString()
                } else {
                    t.secondPlayerPoint.toString()
                }
            }
            throw RuntimeException("Invalid tiebreak")
        }
    }

    fun addPointToPlayer(indexOfPlayer: Int) {
        if (!inTiebreak()) {
            if (indexOfPlayer == 0) {
                points[0] = getPointWinnerPlayerTennisPoints(points[0])
                points[1] = getPointLoserPlayerTennisPoints(points[1], points[0])
            } else {
                points[1] = getPointWinnerPlayerTennisPoints(points[1])
                points[0] = getPointLoserPlayerTennisPoints(points[0], points[1])
            }
            if (points[0] == TennisPoints.GAME || points[1] == TennisPoints.GAME) {
                points[0] = TennisPoints.ZERO
                points[1] = TennisPoints.ZERO
                if (indexOfPlayer == 0) {
                    sets.last().firstPlayerPoint += 1
                } else {
                    sets.last().secondPlayerPoint += 1
                }
                sets.last().isFirstPlayerServe = sets.last().isFirstPlayerServe.not()
            }
        } else {
            if (tieBreak == null) {
                tieBreak = Score(0, 0, sets.last().isFirstPlayerServe)
            }
            if (indexOfPlayer == 0) {
                tieBreak!!.firstPlayerPoint += 1
            } else {
                tieBreak!!.secondPlayerPoint += 1
            }
            if (isTieBreakEnded(7)) {
                tieBreak?.let { t ->
                    if (t.firstPlayerPoint > t.secondPlayerPoint) {
                        sets.last().firstPlayerPoint += 1
                    } else {
                        sets.last().secondPlayerPoint += 1
                    }
                }
                tieBreak = null
                endOfSet()
            }
        }
        if (actualSetIsDone(sets.last())) {
            endOfSet()
        }
    }

    private fun endOfSet() {
        if (sets.size == settings.winnerSetsNumber + settings.winnerSetsNumber - 1 || oneOfPlayersWin()) {
            isMatchEnded = true
        } else {
            sets.add(Score(0, 0, !sets.last().isFirstPlayerServe))
        }
    }

    private fun oneOfPlayersWin(): Boolean {
        var firstPlayerWinnerSetsNumber = 0
        var secondPlayerWinnerSetsNumber = 0
        sets.forEachIndexed { i, set ->
            if (i < sets.size - 1 && set.firstPlayerPoint > set.secondPlayerPoint) {
                firstPlayerWinnerSetsNumber += 1
            } else {
                secondPlayerWinnerSetsNumber += 1
            }
        }
        val p1 = sets[sets.size -1].firstPlayerPoint
        val p2 = sets[sets.size -1].secondPlayerPoint
        if (p1 == 7 || p1 == 6 && p2 <= 4) {
            firstPlayerWinnerSetsNumber += 1
        }
        if (p2 == 7 || p2 ==6 && p1 <= 4) {
            secondPlayerWinnerSetsNumber += 1
        }
        return firstPlayerWinnerSetsNumber == settings.winnerSetsNumber || secondPlayerWinnerSetsNumber == settings.winnerSetsNumber
    }

    private fun isTieBreakEnded(maxPoint: Int): Boolean {
        tieBreak?.let {
            return (it.firstPlayerPoint > maxPoint - 1 && it.firstPlayerPoint - it.secondPlayerPoint > 1) ||
                    (it.secondPlayerPoint > maxPoint - 1 && it.secondPlayerPoint - it.firstPlayerPoint > 1)
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
                if (points[0] == TennisPoints.FORTY && points[1] == TennisPoints.FORTY) {
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

    private fun getPointLoserPlayerTennisPoints(actualPoints: TennisPoints, winnerPoints: TennisPoints): TennisPoints {
        return when(actualPoints) {
            TennisPoints.FORTY -> {
                if (winnerPoints == TennisPoints.ADVANTAGE) {
                    TennisPoints.DISADVANTAGE
                } else {
                    TennisPoints.FORTY
                }
            }
            TennisPoints.ADVANTAGE -> {
                TennisPoints.FORTY
            }
            else -> {
                actualPoints
            }
        }
    }

    private fun inTiebreak(): Boolean {
        return sets.last().firstPlayerPoint == 6 && sets.last().secondPlayerPoint == 6
    }

    private fun actualSetIsDone(set: Score): Boolean {
        return ((set.firstPlayerPoint == 6 && set.secondPlayerPoint <= 4) ||
                (set.secondPlayerPoint == 6 && set.firstPlayerPoint <= 4) ||
                (set.firstPlayerPoint == 7) ||
                (set.secondPlayerPoint == 7))
    }
}
