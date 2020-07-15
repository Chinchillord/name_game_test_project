package com.rack.namegame

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NameGameApplication

fun main(args: Array<String>) {
	runApplication<NameGameApplication>(*args)
}
