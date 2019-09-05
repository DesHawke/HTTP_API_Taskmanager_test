package com.example.taskmanager.controller

import com.example.taskmanager.model.Task
import com.example.taskmanager.repo.TaskRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class TaskController(private val taskRepository: TaskRepository) {

    @GetMapping("/tasks")
    fun findAll() = taskRepository.findAll()


    // Добавление задачи
    @PostMapping("/task")
    fun addNewTask(@RequestBody newTask: Task): Task =
        taskRepository.save(newTask)

    // Изменение задачи
    @PostMapping("/task/{id}")
    fun updateTag(@PathVariable id: Long, @Valid @RequestBody updatedTask: Task): ResponseEntity<Task> {
        return taskRepository.findById(id).map {existingTask ->
            val newTask: Task = existingTask.copy(name = updatedTask.name, description = updatedTask.description,
                    taskDate = updatedTask.taskDate, tagId = updatedTask.tagId)
            ResponseEntity.ok().body(taskRepository.save(newTask))
        }.orElse(ResponseEntity.notFound().build())
    }

    // Удаление задачи
    @DeleteMapping("/task/{id}")
    fun deleteTask(@PathVariable id : Long) : ResponseEntity<Void> {
        return taskRepository.findById(id).map {
            taskRepository.delete(it)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
    }
}