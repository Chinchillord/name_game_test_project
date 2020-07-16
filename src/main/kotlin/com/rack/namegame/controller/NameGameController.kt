package com.rack.namegame.controller

import com.rack.namegame.service.NameGameService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class NameGameController(@Autowired internal var service: NameGameService) {

    @RequestMapping("/")
    fun index(): String {
        return service.getString()
    }
}