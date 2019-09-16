package com.example.taskmanager.exceptions

class NotFoundException(message: String) :Exception(message)
class AlreadyExistsException(message: String):Exception(message)
class NotValidException(message: String):Exception(message)