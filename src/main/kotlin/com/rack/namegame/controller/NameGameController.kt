package com.rack.namegame.controller

import com.rack.namegame.entity.Game
import com.rack.namegame.entity.WillowTreeEmployeeEntity
import com.rack.namegame.service.NameGameService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class NameGameController(@Autowired internal var service: NameGameService) {

    @RequestMapping("/game", method = arrayOf(RequestMethod.GET))
    @GetMapping
    fun getGame(): String {
        return service.getGame()
    }

    @RequestMapping("/game", method = arrayOf(RequestMethod.POST))
    @PostMapping
    fun postGame(@RequestBody game: Game, @RequestParam("guessID") guessID: String): String {
        return service.postGame(game, guessID)
    }

    @RequestMapping("/employee", method = arrayOf(RequestMethod.GET))
    @GetMapping
    fun getEmployee(@RequestParam employeeID: String): String? {
        return service.getEmployee(employeeID)
    }

    @RequestMapping("/employee", method = arrayOf(RequestMethod.POST))
    @PostMapping
    fun postEmployee(@RequestBody employee: WillowTreeEmployeeEntity) {
        service.postEmployee(employee)
    }
}