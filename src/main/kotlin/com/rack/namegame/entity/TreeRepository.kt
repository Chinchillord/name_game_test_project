package com.rack.namegame.entity

import org.springframework.data.jpa.repository.JpaRepository

interface TreeRepository: JpaRepository<WillowTreeEmployeeEntity, String> {
}