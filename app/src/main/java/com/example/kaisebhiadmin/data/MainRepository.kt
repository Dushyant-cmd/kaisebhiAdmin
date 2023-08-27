package com.example.kaisebhiadmin.data

import kotlinx.coroutines.yield

class MainRepository() {
    /**Below method is for test */
    suspend fun getAllQuestions(): String {
        yield()
        return "hello"
    }
}