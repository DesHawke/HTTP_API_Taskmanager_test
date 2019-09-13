package com.example.taskmanager.controller

import com.example.taskmanager.Response
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
    fun findAll() = taskRepository.findAll()

    // Добавление задачи
    @PostMapping("/task")
    @ResponseBody
    fun addNewTask(@Valid @RequestBody newTask: Task): Task =
            taskRepository.save(newTask)

    // Изменение задачи
    @PostMapping("/task/{id}")
    @ResponseBody
    fun updateTask(@PathVariable id: Long, @Valid @RequestBody updatedTask: Task): Response {
        if (!taskRepository.existsById(id))
            throw Exception()
        else {
            val newTask = taskRepository.findById(id).get().copy(
                    name = updatedTask.name,
                    description = updatedTask.description,
                    taskDate = updatedTask.taskDate,
                    tagId = updatedTask.tagId)
            taskRepository.save(newTask)
            return Response(HttpStatus.OK.value(), "Task $id updated")
        }
    }


    // Удаление задачи
    @DeleteMapping("/task/{id}")
    @ResponseBody
    fun deleteTask(@PathVariable id : Long) : Response {
        if (!taskRepository.existsById(id))
            throw Exception()
        else {
            taskRepository.deleteById(id)
            return Response(HttpStatus.OK.value(),"Task $id Deleted")
        }
    }
}