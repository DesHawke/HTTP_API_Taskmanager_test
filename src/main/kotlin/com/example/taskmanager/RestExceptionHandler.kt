package com.example.taskmanager

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import java.io.IOException
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class RestExceptionHandler {
    @ExceptionHandler(IOException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun badRequest(ex: Exception): ErrorResponse {
        return ErrorResponse(400, "Bad Request")
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun unKnownException(ex: Exception): ErrorResponse {
        return ErrorResponse(404, "Not Found")
    }
}