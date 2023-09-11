package com.example.kaisebhiadmin.utils

sealed class ResponseClass {
}

class Success<T>(var response: T): ResponseClass()

class ResponseError(var msg: String): ResponseClass()