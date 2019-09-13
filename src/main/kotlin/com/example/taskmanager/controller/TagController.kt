package com.example.taskmanager.controller

import com.example.taskmanager.Response
import com.example.taskmanager.model.Tag
import com.example.taskmanager.repo.TagRepository
import com.example.taskmanager.repo.TaskRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class TagController(private val tagRepository: TagRepository) {

    @Autowired
    lateinit var taskRepository:TaskRepository

    // Просмотр всех тегов
    @GetMapping("/tags")
    @ResponseBody
    fun getAllTags() = tagRepository.findAll()

    // Добавление нового тега
    @PostMapping("/tag")
    @ResponseBody
    fun createNewTag(@Valid @RequestBody newTag: Tag) = tagRepository.save(newTag)


    @PostMapping("/tag/{id}")
    @ResponseBody
    fun updateTag(@PathVariable id: Long, @Valid @RequestBody newTag: Tag): Response {
        if (!tagRepository.existsById(id))
            throw Exception()
        else {
            val updatedTag = tagRepository.findById(id).get().copy(name = newTag.name)
            tagRepository.save(updatedTag)
            return Response(HttpStatus.OK.value(), "Tag $id updated")
        }
    }

    // Удаление тега
    @DeleteMapping("/tag/{id}")
    @ResponseBody
    fun deleteTag(@PathVariable id : Long) : Response {
        if (!tagRepository.existsById(id))
            throw Exception()
        else {
            tagRepository.deleteById(id)
            return Response(HttpStatus.OK.value(), "Tag $id deleted")
        }
    }

    // Получение тега по Id и всех его задач
    @GetMapping("/tag/{id}")
    @ResponseBody
    fun getAllTasksById(@PathVariable id: Long): ResponseEntity<Map<String,Any>> {
        if(!tagRepository.existsById(id))
            throw Exception("Tag $id not found")
        else {
            val map = mapOf("tag" to tagRepository.findById(id),"tasks" to taskRepository.findAllByTagId(id))
            return ResponseEntity.ok().body(map)
        }
    }
}