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
    fun addNewTask(@Valid @RequestBody newTask: Task): Task =
            taskRepository.save(newTask)

    // Изменение задачи
    @PostMapping("/task/{id}")
    fun updateTag(@PathVariable id: Long, @Valid @RequestBody updatedTask: Task): ResponseEntity<String> {
        if (!taskRepository.existsById(id))
            throw Exception()
        else {
            val newTask = taskRepository.findById(id).get().copy(
                    name = updatedTask.name,
                    description = updatedTask.description,
                    taskDate = updatedTask.taskDate,
                    tagId = updatedTask.tagId)
            taskRepository.save(newTask)
            return ResponseEntity.ok().body("Task Updated")
        }
    }

    // Удаление задачи
    @DeleteMapping("/task/{id}")
    fun deleteTask(@PathVariable id : Long) : ResponseEntity<String> {
        if (!taskRepository.existsById(id))
            throw Exception()
        else {
            taskRepository.deleteById(id)
            return ResponseEntity.ok().body("Task Deleted")
        }
    }
}