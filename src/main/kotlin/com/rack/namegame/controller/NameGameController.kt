package com.rack.namegame.controller

import com.rack.namegame.entity.EmployeeHeadshotWrapper
import com.rack.namegame.entity.Game
import com.rack.namegame.entity.Headshot
import com.rack.namegame.entity.WillowTreeEmployeeEntity
import com.rack.namegame.service.NameGameService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class NameGameController(@Autowired internal var service: NameGameService) {

    @RequestMapping("/game", method = arrayOf(RequestMethod.GET))
    @GetMapping
    fun getGame(@RequestParam("gameID") gameID: String): String {
        return service.getGame(gameID)
    }

    @RequestMapping("/game", method = arrayOf(RequestMethod.POST))
    @PostMapping
    fun postGame(@RequestBody game: Game?,
                 @RequestParam("guessID", required = false) guessID: String?,
                 @RequestParam("isMattMode", required = false) isMattMode: Boolean = false)
            : String? {
        return service.postGame(game, guessID, isMattMode)
    }

    @RequestMapping("/employee", method = arrayOf(RequestMethod.GET))
    @GetMapping
    fun getEmployee(@RequestParam employeeID: String): String? {
        return service.getEmployee(employeeID)
    }

    @RequestMapping("/employee", method = arrayOf(RequestMethod.POST))
    @PostMapping
    fun postEmployee(@RequestBody wrapper: EmployeeHeadshotWrapper): String? {
        return service.postEmployee(wrapper.employee, wrapper.headshot)
    }
}