package com.rack.namegame.service

import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Service
class NameGameService {

    fun getString(): String {
        return "Hello World"
    }

}