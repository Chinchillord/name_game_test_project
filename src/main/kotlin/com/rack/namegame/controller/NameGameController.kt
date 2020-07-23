package com.rack.namegame.controller

import com.rack.namegame.service.NameGameService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class NameGameController(@Autowired internal var service: NameGameService) {

    @RequestMapping("/new_session")
    fun resetGameSession(): String {
        return service.resetGameSession()
    }

    @RequestMapping("/get_session")
    fun getSession(): String {
        return service.getSession()
    }

    @RequestMapping("/make_guess")
    fun makeGuess(@RequestParam("id") id: String): String? {
        return service.makeGuess(id)
    }

    @RequestMapping("/random")
    fun random(): String {
        return service.getSelectionOptionsJSON() ?: ""
    }
}