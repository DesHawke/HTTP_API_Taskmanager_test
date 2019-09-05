package com.example.taskmanager.controller

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
    fun getAllTags() = tagRepository.findAll()

    // Добавление нового тега
    @PostMapping("/tag")
    fun createNewTag(@Valid @RequestBody newTag: Tag) = tagRepository.save(newTag)

    // Изменение существующего тега
    @PostMapping("/tag/{id}")
    fun updateTag(@PathVariable id: Long, @Valid @RequestBody newTag: Tag): ResponseEntity<Tag> {
        return tagRepository.findById(id).map {existingTag ->
            val updatedTag: Tag = existingTag.copy(name = newTag.name)
            ResponseEntity.ok().body(tagRepository.save(updatedTag))
        }.orElse(ResponseEntity.notFound().build())
    }

    // Удаление тега
    @DeleteMapping("/tag/{id}")
    fun deleteTag(@PathVariable id : Long) : ResponseEntity<Void> {
        return tagRepository.findById(id).map {
            tagRepository.delete(it)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
    }

    // Получение тега по Id и всех его задач
    @GetMapping("/tag/{id}")
    fun getAllTasksById(@PathVariable id: Long) =
            mapOf("tag" to tagRepository.findById(id), "tasks" to taskRepository.findAllByTagId(id))
}