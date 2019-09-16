package com.example.taskmanager.repo

import ch.qos.logback.core.joran.spi.ConsoleTarget
import ch.qos.logback.core.joran.spi.ConsoleTarget.findByName
import com.example.taskmanager.model.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
@Repository
interface TagRepository : JpaRepository<Tag,Long>{
    fun existsByName(name: String):Boolean{
        return findByName(name) !=null
    }
}