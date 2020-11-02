package com.rack.namegame

import com.rack.namegame.entity.Headshot
import com.rack.namegame.entity.TreeRepository
import com.rack.namegame.entity.WillowTreeEmployeeEntity
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
class NameGameApplicationTests(@Autowired internal val entityManager: TestEntityManager, @Autowired internal var repository: TreeRepository) {

	@Test
	fun contextLoads() {
	}

	@Test
	fun canAddMember() {
		var employee = WillowTreeEmployeeEntity(firstName = "rack")
		val headshot = Headshot(url = "headshot")
		employee.headshot = headshot
		headshot.employee = employee
		entityManager.persistAndFlush(employee)
		entityManager.persistAndFlush(headshot)
		val found = repository.findById(employee.id!!).get()
		assert(found.firstName == "rack")
		assert(found.id == headshot.id)
	}

}
