ZarinPal In App Billing - Purchase SDK | MPG
============================================
ZarinPal Purchase SDK Provides payment methods on your Android Application. [Java Readme](https://github.com/ZarinPal/Android-SDK/blob/master/README%20_JAVA.md) here if You want to develop it.



Introduction
=============
ZarinPal in-app purchases are the simplest solution to selling digital products or content on Android apps. So many app developers who want to sell digital goods or offer premium membership to users can simply use the it, in-app billing process for smooth and easy checkouts.


<p align="center" width="100%">
<img src="https://raw.githubusercontent.com/ZarinPal/Android-SDK/master/new_logo.svg" alt="sample" width="300" height="100"/>
</p>


Requirements
============

*   Android 5.0 (API level 21) and above
*   Android Gradle Plugin 3.5.1
*   Gradle 5.4.1+
*   AndroidX (as of v11.0.0)

Installation
============

**Step 1**

Add this to your root build.gradle at the end of repositories.
```gradle
    allprojects {
      ext.zarinpalSdkVersion = (LATEST_VERSION_REALEASE) //inform of Releases: https://github.com/ZarinPal/Android-SDK/releases
      repositories {
        ...
        mavenCentral()
     }
```    

**Step 2**

Add the dependency:
```gradle
    dependencies {
      implementation 'com.zarinpal:payment-provider:ext.zarinpalSdkVersion'
    }
```    
    

If your project and business trusted to ZarinPal, SDK ables to providing **Mobile Payment Gateway** on your App so You should add the **MPG** dependency:
```gradle
    dependencies {
      implementation 'com.zarinpal:payment-provider:ext.zarinpalSdkVersion'
      implementation 'com.zarinpal:mpg:ext.zarinpalSdkVersion'
    }
```    

How to use
==========

*   add Permissions in your `Manifest.xml`:
```xml
   <uses-permission android:name="android.permission.INTERNET"/>
   <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
```    

Initialize the billing client
=============================

**Step 1**

* `ZarinPalClientBilling` provides to create the billing client instance:
```kotlin
        val client = ZarinPalBillingClient.newBuilder(this)
            .enableShowInvoice()
            .setListener(stateListener)
            .setNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            .build()
            
      private val stateListener = object : BillingClientStateListener {
            override fun onClientSetupFinished(state: ClientState) {
                //Observing client states
            }

            override fun onClientServiceDisconnected() {
                 Log.v("TAG_INAPP","Billing client Disconnected")
                //When Service disconnect
            }
        }
        
```    

**Step 2**

For start purchase you need a `Purchase` instance, `Purchase` has 3 type of Payment:

*   as **Payment Request** by `asPaymentRequest()`
*   as **Authority ID** by `asAuthority()`
*   as **Sku ID** by `asSku()`

If you would create payment Authority on Client, You must use `asPayementRequest()`, this method needs below parameters:

**Require Parameters:**

*   Merchant id: An unique ID of your business payment gateway.
*   Amount: Amount of Purchase.
*   Callback URL: A valid `URL` Address for sending result purchase.
*   Description: A Content for showing payer.

**Optional Parameters:**

*   Mobile: Valid Mobile number of payer.
*   Email: Valid Email Address of payer.

```kotlin
    val purchase = Purchase.newBuilder()
            .asPaymentRequest(
                "MERCHANT_ID",
                1000,
                "http:\\YOUR_SEVER_URL.com",
                "1000IRR Purchase"
            ).build()
```    

Maybe You had `Authority`, here You must use `asAuthority()`
```kotlin
       val purchase = Purchase .newBuilder()
            .asAuthority("AUTHORITY_RESOLVED")
            .build()
```   
for `Sku` purchase:
```kotlin
      val purchase = Purchase.newBuilder()
            .asSku("SKU_ID") // SKU_ID is an Id that you've generated on ZarinPal panel.
            .build()
```

**Step 3**

You must call `purchase` method to begin flow payment:
```kotlin
        client.launchBillingFlow(purchase, object : FutureCompletionListener<Receipt> {
            override fun onComplete(task: TaskResult<Receipt>) {
                if (task.isSuccess) {
                    val receipt = task.success
                    Log.v("ZP_RECEIPT", receipt?.transactionID)

                    //here you can send receipt data to your server
                    //sentToServer(receipt)
                    
                } else {
                    task.failure?.printStackTrace()
                }
            }
        })
```    
    
**Step 4**

Finally if your eligible to have payment process through **MPG** You should adding `usesCleartextTraffic` to application tag in your `Manifest.xml`
```xml
     
          <application
            android:name="..."
            android:usesCleartextTraffic="true"
            ....
            \>
```    

SKU Query
=========

The ZarinPal Library stores the query results in a List of SkuPurchased objects. You can then call `querySkuPurchased` and you appear sku purchased with inforamtion in your view and provide service.

```kotlin
   val skuQuery = SkuQueryParams.newBuilder("MERCHANT_ID")
            .setSkuList(listOf("SKU_ID_000", "SKU_ID_001"))
            .orderByMobile("0935******")
            .build()
            
            
    client.querySkuPurchased(skuQuery, object : FutureCompletionListener<List<SkuPurchased>> {
            override fun onComplete(task: TaskResult<List<SkuPurchased>>) {
                if (task.isSuccess){
                    val skuPurchaseList = task.success
                    skuPurchaseList?.forEach {
                        Log.v("ZP_SKU_PURCHASED", "${it.authority} ${it.productId}")
                    }
                }else{
                    task.failure?.printStackTrace()
                }
            }
        })
````

KTX
====
a Kotlin extensions for the ZarinPal SDK for Android and Utility Library. These extensions provide Kotlin language features in `Coroutines` async method:

```gradle
    dependencies {
      implementation 'com.zarinpal:payment-provider-ktx:ext.zarinpalSdkVersion'
    }
```    

and to invoke `purchase` suspendable method in coroutine scope to start purchase flow:
```kotlin
     CoroutineScope(Dispatchers.IO).launch {
            try {
                val receipt = client.launchBillingFlow(purchase)
                Log.v("ZP_RECEIPT", receipt.transactionID)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
```

invoke `querySkuPurchased`suspendable method in coroutine scope to start purchase flow:
```kotlin
       CoroutineScope(Dispatchers.IO).launch {
            try {
                val skuPurchaseList = client.querySkuPurchased(skuQuery)
                skuPurchaseList?.forEach {
                    Log.v("ZP_SKU_PURCHASED", "${it.authority} ${it.productId}")
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
```

Contract
========
A contract is a middlware between ZarinPal SDK and your server that provides a new payment provider as credit or digital wallet.
```gradle
    dependencies {
      implementation 'com.zarinpal:contract:ext.zarinpalSdkVersion'
    }
```    

Features
========

**Dark Mode**

```kotlin
    client.setNightMode(AppCompatDelegate.MODE_NIGHT_YES)
```    

**Appear Invoice**

```kotlin
    client.enableShowInvoice()
```    





  

Releases
========

*   The Changelog provides a summary of changes in each release
    
*   The migration guide provides instructions on upgrading from old SDK.
    

Proguard
========

The ZarinPal Android SDK will configure your app’s procured (ruls or text file link)

Developed By
============

The Product developed by ZarinPal Team also You can Communicate and open issue

