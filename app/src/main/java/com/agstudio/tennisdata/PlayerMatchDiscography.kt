package com.agstudio.tennisdata

data class PlayerMatchDiscography(
    var aces: Int,
    var doubleFaults: Int,
    var firstServePercent: Int,
    var wonPointsBehindFirstServe: Int,
    var wonPointsBehindSecondServe: Int,
    var breakPoints: Int,
    var setPoints: Int,
    var matchPoints: Int,
    var totalWonPoints: Int,
    var winners: Int,
    var unforcedErrors: Int
)