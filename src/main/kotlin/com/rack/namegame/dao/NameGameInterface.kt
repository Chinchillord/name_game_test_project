package com.rack.namegame.dao

import com.rack.namegame.entity.Game
import com.rack.namegame.entity.Headshot
import com.rack.namegame.entity.WillowTreeEmployeeEntity

interface NameGameInterface {
    fun getEmployeeByID(id: String): WillowTreeEmployeeEntity
    fun getAllEmployees(): MutableList<WillowTreeEmployeeEntity>
    fun addEmployee(employee: WillowTreeEmployeeEntity, headshot: Headshot): String?
    fun getEmployeesByFirstName(name: String): MutableList<WillowTreeEmployeeEntity>
    fun getGameByID(id: String): Game
    fun addGame(game: Game): String?
}