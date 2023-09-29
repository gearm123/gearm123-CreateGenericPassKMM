package com.test.multiplatformappsandbox

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform