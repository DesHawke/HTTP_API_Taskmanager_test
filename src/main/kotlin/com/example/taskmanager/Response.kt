package com.example.taskmanager

class Response {
    var status: Int? = null
    var message: String? = null

    constructor() : super()
    constructor(status: Int, message: String) : super() {
        this.status = status
        this.message = message
    }
}