package ir.nujen.zarinpal

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.nujen.zarinpal.ui.theme.ZarinpalTheme
import com.zarinpal.ZarinPalBillingClient
import com.zarinpal.billing.purchase.Purchase
import com.zarinpal.client.BillingClientStateListener
import com.zarinpal.client.ClientState
import com.zarinpal.provider.core.future.FutureCompletionListener
import com.zarinpal.provider.core.future.TaskResult
import com.zarinpal.provider.model.response.Receipt




class MainActivity : ComponentActivity() {
    private var client: ZarinPalBillingClient? = null
    private var purchaseCompletedListener: FutureCompletionListener<Receipt>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val TAG = "InAppBilling Sample: "

        val billingClientStateListener = object : BillingClientStateListener {
            override fun onClientServiceDisconnected() {
                Log.d(TAG, "onClientServiceDisconnected")
            }

            override fun onClientSetupFinished(state: ClientState) {
                Log.d(TAG, "onClientSetupFinished ${state.name}")

            }
        }


        purchaseCompletedListener = object : FutureCompletionListener<Receipt> {
            override fun onComplete(task: TaskResult<Receipt>) {
                Log.d(TAG, "onComplete Receipt is ${task.isSuccess}")

                if (task.isSuccess) {
                    /* txtReceipt.text = "Receipt: \n" +
                             "Transaction id: ${task.success?.transactionID}\n" +
                             "Amount: ${task.success?.amount}\n" +
                             "Date: ${task.success?.date}\n" +
                             "Status: ${task.success?.isSuccess}\n"*/
                } else {

                    /*  txtReceipt.text = "Receipt failed: \n" +
                              "${task.failure?.message}"*/
                }

            }
        }

        client = ZarinPalBillingClient.newBuilder(this)
            .setNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            .enableShowInvoice()
            .setListener(billingClientStateListener)
            .build()

        setContent {
            ZarinpalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }


    @Composable
    fun Greeting() {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.height(200.dp)) {
            Button(onClick = { client?.launchBillingFlow(getPurchaseAsPaymentRequest(), purchaseCompletedListener) }) {
                Text("Pay as Payment Request",)
            }
            Button(onClick = { client?.launchBillingFlow(getPurchaseAsAuthority(), purchaseCompletedListener) }) {
                Text("Pay as Authority")
            }
            Button(onClick = {  client?.launchBillingFlow(getPurchaseAsSku(), purchaseCompletedListener) }) {
                Text("Pay as Sku")
            }

        }
        Column(horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center, modifier = Modifier.height(200.dp)) {
            Text("Result of Receipt!")
        }
    }

    //------------------------- zarinpanl merchantId
    private fun getPurchaseAsPaymentRequest(): Purchase {
        val merchantId = "6c64a645-1b28-4956-b32e-7b777864121a"
        val amount = 1000L
        val description = "Payment Request via ZarinPal SDK"
        val callback = "https://google.com" // Your Server address


        return Purchase.newBuilder()
            .asPaymentRequest(merchantId, amount, callback, description)
            .build()

    }


    private fun getPurchaseAsAuthority(): Purchase {

        val authority = "" // The authority that resolved from ZarinPal
        return Purchase.newBuilder()
            .asAuthority(authority)
            .build()

    }

    private fun getPurchaseAsSku(): Purchase {
        val sku = "" // sku zarinpall
        return Purchase.newBuilder()
            .asSku(sku)
            .build()
    }

}
