package com.example.taskmanager.controller

import com.example.taskmanager.model.Tag
import com.example.taskmanager.repo.TagRepository
import com.example.taskmanager.repo.TaskRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class TagController(private val tagRepository: TagRepository) {

    @Autowired
    lateinit var taskRepository:TaskRepository

    // Просмотр всех тегов
    @GetMapping("/tags")
    fun getAllTags() = tagRepository.findAll()

    // Добавление нового тега
    @PostMapping("/tag")
    fun createNewTag(@Valid @RequestBody newTag: Tag) = tagRepository.save(newTag)


    @PostMapping("/tag/{id}")
    fun updateTag(@PathVariable id: Long, @Valid @RequestBody newTag: Tag): ResponseEntity<String> {
        if (!tagRepository.existsById(id))
            throw Exception()
        else {
            val updatedTag = tagRepository.findById(id).get().copy(name = newTag.name)
            tagRepository.save(updatedTag)
            return ResponseEntity.ok().body("Tag Updated")
        }
    }

    // Удаление тега
    @DeleteMapping("/tag/{id}")
    fun deleteTag(@PathVariable id : Long) : ResponseEntity<String> {
        if (!tagRepository.existsById(id))
            throw Exception()
        else {
            tagRepository.deleteById(id)
            return ResponseEntity.ok().body("Tag Deleted")
        }
    }

    // Получение тега по Id и всех его задач
    @GetMapping("/tag/{id}")
    fun getAllTasksById(@PathVariable id: Long): ResponseEntity<Map<String,Any>> {
        if(!tagRepository.existsById(id))
            throw Exception()
        else {
            val map = mapOf("tag" to tagRepository.findById(id),"tasks" to taskRepository.findAllByTagId(id))
            return ResponseEntity.ok().body(map)
        }
    }

}