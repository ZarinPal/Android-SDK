package com.zarinpal.sample.inappbilling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.zarinpal.ZarinPalBillingClient
import com.zarinpal.billing.purchase.Purchase
import com.zarinpal.client.BillingClientStateListener
import com.zarinpal.client.ClientState
import com.zarinpal.provider.core.future.FutureCompletionListener
import com.zarinpal.provider.core.future.TaskResult
import com.zarinpal.provider.model.response.Receipt
import com.zarinpal.sample.inappbilling.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var client: ZarinPalBillingClient? = null

    companion object {
        const val TAG = "InAppBilling Sample: "
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtReceipt = findViewById<TextView>(R.id.txt_receipt)

        val billingClientStateListener = object : BillingClientStateListener {
            override fun onClientServiceDisconnected() {
                Log.d(TAG, "onClientServiceDisconnected")
            }

            override fun onClientSetupFinished(state: ClientState) {
                Log.d(TAG, "onClientSetupFinished ${state.name}")

            }
        }


        val purchaseCompletedListener = object : FutureCompletionListener<Receipt> {
            override fun onComplete(task: TaskResult<Receipt>) {
                Log.d(TAG, "onComplete Receipt is ${task.isSuccess}")

                if (task.isSuccess) {
                    txtReceipt.text = "Receipt: \n" +
                            "Transaction id: ${task.success?.transactionID}\n" +
                            "Amount: ${task.success?.amount}\n" +
                            "Date: ${task.success?.date}\n" +
                            "Status: ${task.success?.isSuccess}\n"
                } else {

                    txtReceipt.text = "Receipt failed: \n" +
                            "${task.failure?.message}"
                }

            }
        }



        client = ZarinPalBillingClient.newBuilder(this)
            .setNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            .enableShowInvoice()
            .setListener(billingClientStateListener)
            .build()


        findViewById<View>(R.id.btn_payment_request).setOnClickListener {
            client?.launchBillingFlow(getPurchaseAsPaymentRequest(), purchaseCompletedListener)
        }

        findViewById<View>(R.id.btn_authority).setOnClickListener {
            client?.launchBillingFlow(getPurchaseAsAuthority(), purchaseCompletedListener)
        }

        findViewById<View>(R.id.btn_sku).setOnClickListener {
            client?.launchBillingFlow(getPurchaseAsSku(), purchaseCompletedListener)
        }

    }

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
        val sku = "" // sku created from ZarinPal
        return Purchase.newBuilder()
            .asSku(sku)
            .build()
    }
}