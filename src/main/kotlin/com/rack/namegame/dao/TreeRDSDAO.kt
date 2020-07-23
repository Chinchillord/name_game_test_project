package com.rack.namegame.dao

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.rack.namegame.entity.TreeRepository
import com.rack.namegame.entity.WillowTreeEmployeeEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TreeRDSDAO {

    @Autowired
    lateinit var treeRepository: TreeRepository

    fun getAllEmployees(): MutableList<WillowTreeEmployeeEntity> {
        return treeRepository.findAll()
    }

    fun getEmployeeByName(name: String): MutableList<WillowTreeEmployeeEntity> {
        return treeRepository.findAll().filter { it.firstName == name } as MutableList<WillowTreeEmployeeEntity>
    }

    fun getEmployeeById(id: String): MutableList<WillowTreeEmployeeEntity> {
        return treeRepository.findById(id) as MutableList<WillowTreeEmployeeEntity>
    }

}