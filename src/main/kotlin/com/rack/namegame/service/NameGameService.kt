package com.rack.namegame.service

import com.rack.namegame.dao.TreeRDSDAO
import com.rack.namegame.entity.Game
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
    lateinit var moshi: Moshi


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

    fun getEmployee(employeeID: String): String? {
        return moshi.adapter(WillowTreeEmployeeEntity::class.java)
                .toJson(dao.getEmployeeById(employeeID))
    }

    fun postEmployee(employee: WillowTreeEmployeeEntity): String {
        dao.addEmployee(employee)
        return "success"
    }

    fun postGame(game: Game, id: String): String {
        if (id == game.correctEmployee?.id) {
            game.correctGuesses = game.correctGuesses?.plus(1)
            game.selectionOptions = getRandomEmployees()
        } else {
            game.incorrectGuesses = game.incorrectGuesses?.plus(1)
        }
        val adapter = moshi.adapter(Game::class.java)
        return adapter.toJson(game)
    }

    fun getByName(name: String): MutableList<WillowTreeEmployeeEntity> {
        return dao.getEmployeeByName(name)
    }

    fun getGame(): String {
        var game = Game(getRandomEmployees())
        game.correctEmployee = game.selectionOptions?.random()
        val adapter = moshi.adapter(Game::class.java)
        return adapter.toJson(game)
    }

}