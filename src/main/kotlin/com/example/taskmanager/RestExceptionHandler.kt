package com.example.taskmanager

import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.io.IOException
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class RestExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun badRequest(ex: MethodArgumentNotValidException ): Response {
        return Response(HttpStatus.BAD_REQUEST.value(), ex.message)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun unKnownException(ex: Exception): Response {
        return Response(HttpStatus.NOT_FOUND.value(), "Not Found")
    }
}