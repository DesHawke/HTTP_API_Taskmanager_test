package com.example.taskmanager

class ErrorResponse {
    var status: Int = 0
    var message: String? = null

    constructor() : super() {}
    constructor(status: Int, message: String) : super() {
        this.status = status
        this.message = message
    }
}