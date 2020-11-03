package com.rack.namegame.entity

import javax.persistence.Entity

data class EmployeeHeadshotWrapper (
        val employee: WillowTreeEmployeeEntity,
        val headshot: Headshot
)