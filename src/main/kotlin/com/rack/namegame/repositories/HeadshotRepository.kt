package com.rack.namegame.repositories

import com.rack.namegame.entity.Headshot
import org.springframework.data.jpa.repository.JpaRepository

interface HeadshotRepository: JpaRepository<Headshot, String> {
}