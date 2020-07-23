package com.rack.namegame.service

import com.rack.namegame.dao.TreeRDSDAO
import com.rack.namegame.entity.GameSession
import com.rack.namegame.entity.WillowTreeEmployeeEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service

@Service
class NameGameService(@Autowired internal var dao: TreeRDSDAO) {

    @Autowired
    lateinit var gameSession: GameSession
    @Autowired
    lateinit var moshi: Moshi

    @Bean
    fun gameSession(): GameSession {
        return GameSession(
                null,
                null,
                0,
                0
        )
    }

    @Bean
    fun moshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    private fun treeEmployeesToJson(employees: MutableList<WillowTreeEmployeeEntity>): String? {
        val customType = Types.newParameterizedType(MutableList::class.java, WillowTreeEmployeeEntity::class.java)
        val adapter = moshi.adapter<MutableList<WillowTreeEmployeeEntity>>(customType)
        val treeJSON = adapter.toJson(employees as MutableList<WillowTreeEmployeeEntity>?)
        return treeJSON
    }

    private fun getRandomEmployees(count: Int = 6): MutableList<WillowTreeEmployeeEntity> {
        var employees = dao.getAllEmployees()
        var randomEmployees = mutableListOf<WillowTreeEmployeeEntity>()
        for (i in 0..count) {
            val temp = employees.random()
            randomEmployees.add(temp)
            employees.remove(temp)
        }
        return randomEmployees
    }

    fun getSelectionOptionsJSON(count: Int = 6): String? {
        val employees = getRandomEmployees()
        gameSession.selectionOptions = employees
        gameSession.correctEmployee = gameSession.selectionOptions!!.random()
        return treeEmployeesToJson(employees)
    }

    fun resetGameSession(): String {
        gameSession.selectionOptions = null
        gameSession.correctEmployee = null
        gameSession.correctGuesses = 0
        gameSession.incorrectGuesses = 0
        gameSession.selectionOptions = getRandomEmployees()
        gameSession.correctEmployee = gameSession.selectionOptions?.random()
        return "started"
    }

    fun makeGuess(id: String): String {
        if (id == gameSession.correctEmployee?.id) {
            gameSession.correctGuesses = gameSession.correctGuesses?.plus(1)
            gameSession.selectionOptions = getRandomEmployees()
        } else {
            gameSession.incorrectGuesses = gameSession.incorrectGuesses?.plus(1)
        }
        val adapter = moshi.adapter(GameSession::class.java)
        return adapter.toJson(gameSession)
    }

    fun getByName(name: String): MutableList<WillowTreeEmployeeEntity> {
        return dao.getEmployeeByName(name)
    }

    fun getSession(): String {
        val adapter = moshi.adapter(GameSession::class.java)
        return adapter.toJson(gameSession)
    }

}