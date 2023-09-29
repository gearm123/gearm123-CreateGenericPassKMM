package com.test.multiplatformappsandbox

class Greeting {
    private val platform: Platform = getPlatform()

    @Throws(Exception::class)
    suspend fun greet(): String {
        return "hello world"
    }
}

