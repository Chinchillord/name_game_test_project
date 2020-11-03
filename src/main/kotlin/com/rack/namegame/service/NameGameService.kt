package com.rack.namegame.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.rack.namegame.dao.NameGameInterface
import com.rack.namegame.entity.Game
import com.rack.namegame.entity.Headshot
import com.rack.namegame.entity.WillowTreeEmployeeEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class NameGameService(@Autowired internal var dao: NameGameInterface) {

    var mapper: ObjectMapper = jacksonObjectMapper()

    private fun getRandomEmployees(count: Int = 6): MutableList<WillowTreeEmployeeEntity> {
        val employees = dao.getAllEmployees()
        val randomEmployees = mutableListOf<WillowTreeEmployeeEntity>()
        for (i in 0 until count) {
            val temp = employees.random()
            randomEmployees.add(temp)
            employees.remove(temp)
        }
        return randomEmployees
    }

    private fun getRandomEmployees(count: Int = 6, name: String): MutableList<WillowTreeEmployeeEntity> {
        val employees = dao.getEmployeesByFirstName(name)
        val randomEmployees = mutableListOf<WillowTreeEmployeeEntity>()
        for (i in 0 until count) {
            val temp = employees.random()
            randomEmployees.add(temp)
            employees.remove(temp)
        }
        return randomEmployees
    }

    fun getEmployee(employeeID: String): String? {
        return mapper.writeValueAsString(dao.getEmployeeByID(employeeID))
    }

    fun postEmployee(employee: WillowTreeEmployeeEntity, headshot: Headshot) {
        employee.headshot = headshot
        headshot.employee = employee
        dao.addEmployee(employee)
    }

    private fun newGame(isMattMode: Boolean = false): String? {
        var selectionOptions = mutableListOf<WillowTreeEmployeeEntity>()
        var mode = "default"
        if (isMattMode) {
            mode = "matt"
            selectionOptions = getRandomEmployees(name = "matt")
        } else {
            selectionOptions = getRandomEmployees()
        }
        return dao.addGame(Game(selectionOptions = selectionOptions, correctEmployee = selectionOptions.random(), correctGuesses = 0, incorrectGuesses = 0, mode = mode))
    }

    fun postGame(game: Game? = null, id: String? = null, isMattMode: Boolean = false): String? {
        if (game == null) {
            return newGame(isMattMode)
        }
        if (id == game.correctEmployee?.id) {
            game.correctGuesses = game.correctGuesses?.plus(1)
            game.selectionOptions = getRandomEmployees()
        } else {
            game.incorrectGuesses = game.incorrectGuesses?.plus(1)
        }
        dao.addGame(game)
        return game.id
    }

    fun getGame(id: String): String {
        val game = dao.getGameByID(id)
        return mapper.writeValueAsString(game)
    }

}