package com.rack.namegame.repositories

import com.rack.namegame.entity.Game
import org.springframework.data.jpa.repository.JpaRepository

interface GameRepository: JpaRepository<Game, String> {
}