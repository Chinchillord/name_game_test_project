package com.rack.namegame.entity

data class Game (
        var selectionOptions: MutableList<WillowTreeEmployeeEntity>? = null,
        var correctEmployee: WillowTreeEmployeeEntity? = null,
        var correctGuesses: Int = 0,
        var incorrectGuesses: Int = 0
)