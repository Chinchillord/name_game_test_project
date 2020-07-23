package com.rack.namegame.entity

data class GameSession (
        var selectionOptions: MutableList<WillowTreeEmployeeEntity>?,
        var correctEmployee: WillowTreeEmployeeEntity?,
        var correctGuesses: Int = 0,
        var incorrectGuesses: Int = 0
)