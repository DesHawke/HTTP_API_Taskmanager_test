package com.example.taskmanager.controller

import com.example.taskmanager.exceptions.AlreadyExistsException
import com.example.taskmanager.exceptions.NotFoundException
import com.example.taskmanager.model.Task
import com.example.taskmanager.repo.TaskRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class TaskController(private val taskRepository: TaskRepository) {

    @GetMapping("/tasks")
    @ResponseBody
    fun getAllTags():ResponseEntity<List<Task>> {
        val allTasks : List<Task> = taskRepository.findAll()
        if (allTasks.isEmpty())
            throw NotFoundException("Tasks not found")
        else
            return ResponseEntity(taskRepository.findAll(), HttpStatus.OK)
    }

    // Добавление задачи
    @PostMapping("/task")
    @ResponseBody
    fun addNewTask(@Valid @RequestBody newTask: Task): ResponseEntity<Void> {
        if (taskRepository.existsByName(newTask.name))
            throw AlreadyExistsException("Task with name '${newTask.name}' already exists")
        else {
            taskRepository.save(newTask)
            return ResponseEntity(HttpStatus.CREATED)
        }
    }

    // Изменение задачи
    @PostMapping("/task/{id}")
    @ResponseBody
    fun updateTask(@PathVariable id: Long, @Valid @RequestBody newTask: Task): ResponseEntity<Task> {
        val updatedTask=taskRepository.findById(id).orElse(null)
        when {
            updatedTask==null -> throw NotFoundException("Task with id=$id not found")
            taskRepository.existsByName(newTask.name) -> throw AlreadyExistsException("Task with name '${newTask.name}' already exists")
            else -> {
                updatedTask.copy(
                        name = newTask.name,
                        description = newTask.description,
                        taskDate = newTask.taskDate,
                        tag = newTask.tag)
                taskRepository.save(updatedTask)
                return ResponseEntity(updatedTask, HttpStatus.OK)
            }
        }
    }


    // Удаление задачи
    @DeleteMapping("/task/{id}")
    @ResponseBody
    fun deleteTask(@PathVariable id : Long) : ResponseEntity<Task> {
        val curTask=taskRepository.findById(id).orElse(null)
        if (curTask==null)
            throw NotFoundException("Task with id=$id not found")
        else {
            taskRepository.deleteById(id)
            return ResponseEntity(HttpStatus.NO_CONTENT)
        }
    }
}