package com.test.multiplatformappsandbox


interface PayClient {
    val payClientName: String
    fun getClient(inputParameter: Any) : Any?
}
expect fun getPayClient() : PayClient


