package com.rack.namegame

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.rack.namegame.dao.NameGameInterface
import com.rack.namegame.entity.Game
import com.rack.namegame.entity.Headshot
import com.rack.namegame.entity.WillowTreeEmployeeEntity
import com.rack.namegame.repositories.GameRepository
import com.rack.namegame.repositories.TreeRepository
import com.rack.namegame.service.NameGameService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension

class TestDAO(val entityManager: TestEntityManager, val treeRepository: TreeRepository, val gameRepository: GameRepository): NameGameInterface {
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
		return treeRepository.findAll().filter {it.firstName?.contains(name, ignoreCase = true) ?: false}.toMutableList()
	}

	override fun getGameByID(id: String): Game {
		return gameRepository.findById(id).get()
	}

	override fun addGame(game: Game): String {
		gameRepository.save(game)
		return game.id ?: ""
	}
}

@ExtendWith(SpringExtension::class)
@DataJpaTest
class NameGameApplicationTests(@Autowired internal val entityManager: TestEntityManager, @Autowired internal var treeRepository: TreeRepository, @Autowired internal val gameRepository: GameRepository) {

	var mapper: ObjectMapper = jacksonObjectMapper()
	val service = NameGameService(TestDAO(entityManager, treeRepository, gameRepository))

	@Test
	fun givenCorrectAnswerScoreIncreases() {
		var employees = arrayOf(WillowTreeEmployeeEntity(), WillowTreeEmployeeEntity(), WillowTreeEmployeeEntity(), WillowTreeEmployeeEntity(), WillowTreeEmployeeEntity(), WillowTreeEmployeeEntity())
		val headshots = arrayOf(Headshot(), Headshot(), Headshot(), Headshot(), Headshot(), Headshot())
		for (i in 0 until 6) {
			employees[i].headshot = headshots[i]
			headshots[i].employee = employees[i]
			entityManager.persistAndFlush(employees[i])
			entityManager.persistAndFlush(headshots[i])
		}
		var newGameID = service.postGame()
		var newGame: Game = mapper.readValue(service.getGame(newGameID ?: ""))
		val updatedGameID = service.postGame(newGame, newGame?.correctEmployee?.id)
		val updatedGameString = service.getGame(updatedGameID ?: "")
		var updatedGame: Game = mapper.readValue(updatedGameString)
		assert(updatedGame.correctGuesses == 1)
	}

	@Test
	fun givenIncorrectAnswerScoreIncreases() {
		var employees = arrayOf(WillowTreeEmployeeEntity(), WillowTreeEmployeeEntity(), WillowTreeEmployeeEntity(), WillowTreeEmployeeEntity(), WillowTreeEmployeeEntity(), WillowTreeEmployeeEntity())
		val headshots = arrayOf(Headshot(), Headshot(), Headshot(), Headshot(), Headshot(), Headshot())
		for (i in 0 until 6) {
			employees[i].headshot = headshots[i]
			headshots[i].employee = employees[i]
			treeRepository.save(employees[i])
		}
		var newGameID = service.postGame()
		var newGame: Game = mapper.readValue(service.getGame(newGameID ?: ""))
		val updatedGameID = service.postGame(newGame, "not_the_id")
		val updatedGameString = service.getGame(updatedGameID ?: "")
		var updatedGame: Game = mapper.readValue(updatedGameString)
		assert(updatedGame.incorrectGuesses == 1)
	}

	@Test
	fun givenValidIDServiceReturnsJSONStringOfEmployee() {
		val employee = WillowTreeEmployeeEntity(firstName = "Michael")
		treeRepository.save(employee)
		val foundGame = mapper.readValue<WillowTreeEmployeeEntity>(service.getEmployee(employee.id ?: "") ?: "")
		assert(foundGame.firstName == "Michael")
	}

	@Test
	fun givenValidEmployeeAndHeadshotObjectsServiceCanPersistEmployee() {
		var employee = WillowTreeEmployeeEntity()
		var headshot = Headshot()
		service.postEmployee(employee, headshot)
		val foundEmployee = treeRepository.findById(employee.id ?: "").get()
		assert(foundEmployee == employee)
		assert(foundEmployee.headshot == headshot)
	}

}
