package com.rack.namegame.dao

import com.rack.namegame.entity.Game
import com.rack.namegame.repositories.TreeRepository
import com.rack.namegame.entity.WillowTreeEmployeeEntity
import com.rack.namegame.repositories.GameRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class NameGameDAO: NameGameInterface {

    @Autowired
    lateinit var treeRepository: TreeRepository

    @Autowired
    lateinit var gameRepository: GameRepository

    override fun getEmployeeByID(id: String): WillowTreeEmployeeEntity {
        return treeRepository.findById(id).get()
    }

    override fun getAllEmployees(): MutableList<WillowTreeEmployeeEntity> {
        return treeRepository.findAll()
    }

    override fun addEmployee(employee: WillowTreeEmployeeEntity) {
        treeRepository.save(employee)
    }

    override fun getEmployeesByFirstName(name: String): MutableList<WillowTreeEmployeeEntity> {
        return treeRepository.findAll().filter {it.firstName?.contains("matt", ignoreCase = true) ?: false}.toMutableList()
    }

    override fun getGameByID(id: String): Game {
        return gameRepository.findById(id).get()
    }

    override fun addGame(game: Game): String? {
        gameRepository.save(game)
        return game.id
    }

}