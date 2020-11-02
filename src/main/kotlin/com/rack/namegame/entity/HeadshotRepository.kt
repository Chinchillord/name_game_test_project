package com.rack.namegame.entity

import org.springframework.data.jpa.repository.JpaRepository

interface HeadshotRepository: JpaRepository<Headshot, String> {
}