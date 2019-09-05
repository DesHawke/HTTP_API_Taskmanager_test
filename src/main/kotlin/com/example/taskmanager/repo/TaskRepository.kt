package com.example.taskmanager.repo

import com.example.taskmanager.model.Task
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : CrudRepository<Task, Long>, JpaSpecificationExecutor<Task>{
    fun findAllByTagId(tagId: Long):List<Task>
}