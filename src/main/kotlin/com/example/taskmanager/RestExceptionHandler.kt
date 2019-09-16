package com.example.taskmanager

import com.example.taskmanager.exceptions.AlreadyExistsException
import com.example.taskmanager.exceptions.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.*

@RestControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    @ResponseBody
    fun notFoundException(ex: NotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse(Date(),HttpStatus.NOT_FOUND.value(), ex.message), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(AlreadyExistsException::class)
    @ResponseBody
    fun alreadyExistsException(ex: AlreadyExistsException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse(Date(),HttpStatus.CONFLICT.value(), ex.message), HttpStatus.CONFLICT)
    }
}