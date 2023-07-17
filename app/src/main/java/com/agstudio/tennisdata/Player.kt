package com.agstudio.tennisdata

import java.util.*

data class Player(var name: String, var userID: UUID, var results: ArrayList<MatchResult>)
