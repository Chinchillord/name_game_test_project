package com.rack.namegame.repositories

import com.rack.namegame.entity.WillowTreeEmployeeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TreeRepository: JpaRepository<WillowTreeEmployeeEntity, String> {
}