package com.test.multiplatformappsandbox

import android.content.Context
import com.google.android.gms.pay.Pay


class AndroidPayClient() : PayClient {
    override val payClientName: String = "Google Wallet Pay Client"
    override fun getClient(inputParameter: Any): Any? {
        if (inputParameter is Context) {
            return Pay.getClient(inputParameter)
        }
        return null
    }

}

actual fun getPayClient(): PayClient {
    return AndroidPayClient()
}


