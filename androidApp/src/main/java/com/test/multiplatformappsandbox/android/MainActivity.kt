package com.test.multiplatformappsandbox.android

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.android.gms.pay.PayApiAvailabilityStatus
import com.google.android.gms.pay.PayClient
import com.test.multiplatformappsandbox.CommonPayClient
import com.test.multiplatformappsandbox.Greeting
import com.test.multiplatformappsandbox.PassClassGenerator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.Date
import java.util.UUID

class MainActivity : ComponentActivity() {
    lateinit var walletClient: PayClient
    private val addToGoogleWalletRequestCode = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        walletClient = (CommonPayClient().client.getClient(this) as PayClient)
        val text: TextView = findViewById(R.id.textView)
        val buttonLayout: FrameLayout = findViewById(R.id.addToGoogleWalletButton)
        val externalScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

        externalScope.launch {
            text.text = greet()
            fetchCanUseGoogleWalletApi(buttonLayout)
        }

        val time : Long = Date().time / 1000L
        val passId = UUID.randomUUID().toString()
        val jsonObject = JSONObject(PassClassGenerator().createUnSignedJwt(time,passId))

        buttonLayout.setOnClickListener {
            walletClient.savePasses(jsonObject.toString(), this, addToGoogleWalletRequestCode)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == addToGoogleWalletRequestCode) {
            when (resultCode) {
                RESULT_OK -> {
                    // Pass saved successfully
                }

                RESULT_CANCELED -> {
                    // Save operation canceled
                }

                PayClient.SavePassesResult.SAVE_ERROR -> data?.let { intentData ->
                    val errorMessage = intentData.getStringExtra(PayClient.EXTRA_API_ERROR_MESSAGE)
                    // Handle error
                }

                else -> {
                    // Handle unexpected (non-API) exception
                }
            }
        }
    }

    private suspend fun greet(): String {
        return Greeting().greet()
    }

    private suspend fun fetchCanUseGoogleWalletApi(container: FrameLayout) {
        walletClient
            .getPayApiAvailabilityStatus(PayClient.RequestType.SAVE_PASSES)
            .addOnSuccessListener { status ->
                if (status == PayApiAvailabilityStatus.AVAILABLE) {
                    container.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener {
                // Hide the button and optionally show an error message
            }
    }
}

