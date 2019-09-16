package com.example.taskmanager.controller

import com.example.taskmanager.exceptions.AlreadyExistsException
import com.example.taskmanager.exceptions.NotFoundException
import com.example.taskmanager.model.Tag
import com.example.taskmanager.repo.TagRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class TagController(val tagRepository: TagRepository) {

    // Просмотр всех тегов
    @GetMapping("/tags")
    @ResponseBody
    fun getAllTags():ResponseEntity<List<Tag>> {
        val allTags : List<Tag> = tagRepository.findAll()
        if (allTags.isEmpty())
            throw NotFoundException("Tags not found")
        else
            return ResponseEntity(tagRepository.findAll(), HttpStatus.OK)}

    // Добавление нового тега
    @PostMapping("/tag")
    @ResponseBody
    fun createNewTag(@Valid @RequestBody newTag: Tag): ResponseEntity<Void>{
        if (tagRepository.existsByName(newTag.name))
            throw AlreadyExistsException("Tag with name '${newTag.name}' already exists")
        else {
            tagRepository.save(newTag)
            return ResponseEntity(HttpStatus.CREATED)
        }
    }

    // Изменение тега
    @PostMapping("/tag/{id}")
    @ResponseBody
    fun updateTag(@PathVariable id: Long, @Valid @RequestBody newTag: Tag): ResponseEntity<Tag> {
        val curTag=tagRepository.findById(id).orElse(null)

        when {
            curTag==null -> throw NotFoundException("Tag with id=$id not found")
            tagRepository.existsByName(newTag.name) -> throw AlreadyExistsException("Tag with name '${newTag.name}' already exists")
            else -> {
                val updatedTag = tagRepository.findById(id).get().copy(name = newTag.name)
                tagRepository.save(updatedTag)
                return ResponseEntity(updatedTag,HttpStatus.OK)
            }
        }
    }

    // Удаление тега
    @DeleteMapping("/tag/{id}")
    @ResponseBody
    fun deleteTag(@PathVariable id : Long) : ResponseEntity<Tag> {
        val delTag=tagRepository.findById(id).orElse(null)
        if (delTag==null)
            throw NotFoundException("Tag with id=$id not found")
        else {
            tagRepository.deleteById(id)
            return ResponseEntity(HttpStatus.NO_CONTENT)
        }
    }

    // Получение тега по Id и всех его задач
    @GetMapping("/tag/{id}")
    @ResponseBody
    fun getAllTasksById(@PathVariable id: Long): ResponseEntity<Tag> {
        val curTag=tagRepository.findById(id).orElse(null)
        if(curTag==null)
            throw NotFoundException("Tag with id=$id not found")
        else {
            return ResponseEntity(curTag, HttpStatus.OK)
        }
    }
}