package com.rack.namegame.dao

interface DAOInterface {
    fun getMembersByName(firstName: String, lastName: String): String?
    fun getAllMembers(tableName: String): String?
}