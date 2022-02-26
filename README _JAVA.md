ZarinPal In App Billing - Purchase SDK | MPG
============================================
ZarinPal Purchase SDK Provides payment methods on your Android Application.



Introduction
=============
ZarinPal in-app purchases are the simplest solution to selling digital products or content on Android apps. So many app developers who want to sell digital goods or offer premium membership to users can simply use the it, in-app billing process for smooth and easy checkouts.


<p align="center" width="100%">
<img src="https://raw.githubusercontent.com/ZarinPal/Android-SDK/master/logo%20%E2%80%93%201.png" alt="sample" width="300" height="100"/>
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
```java

        BillingClientStateListener listener = new BillingClientStateListener() {
            @Override
            public void onClientSetupFinished(@NotNull ClientState state) {
                //Observing client states

            }

            @Override
            public void onClientServiceDisconnected() {
                Log.v("TAG_INAPP","Billing client Disconnected");
                //When Service disconnect
            }
        };

        ZarinPalBillingClient client = ZarinPalBillingClient.newBuilder(this)
                .enableShowInvoice()
                .setListener(listener)
                .setNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                .build();

        
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
*   Callback URL: A valid `URI` or `URL` Address for sending result purchase.
*   Description: A Content for showing payer.

**Optional Parameters:**

*   Mobile: Valid Mobile number of payer.
*   Email: Valid Email Address of payer.

```java
        Purchase purchase = Purchase.newBuilder().asPaymentRequest(
                "MERCHANT_ID",
                1000L,
                "http:\\YOUR_SEVER_URL.com",
                "1000IRR Purchase"
        ).build();
```    

Maybe You had `Authority`, here You must use `asAuthority()`
```java
        Purchase purchase = Purchase.newBuilder()
                .asAuthority("AUTHORITY_RESOLVED")
                .build();
```   
for `Sku` purchase:
```java
        Purchase purchase = Purchase.newBuilder()
                .asSku("SKU_ID") // SKU_ID is an Id that you've generated on ZarinPal panel.
                .build();
```

**Step 3**

You must call `purchase` method to begin flow payment:
```java
           client.launchBillingFlow(purchase, new FutureCompletionListener<Receipt>() {
            @Override
            public void onComplete(TaskResult<Receipt> task) {
                if (task.isSuccess()) {
                    Receipt receipt = task.getSuccess();
                    Log.v("ZP_RECEIPT", receipt.getTransactionID());

                    //here you can send receipt data to your server
                    //sentToServer(receipt)

                } else {
                    task.getFailure().printStackTrace();
                }
            }
        });
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

```java
    List<String> skus = Arrays.asList("SKU_ID_000", "SKU_ID_001");
    SkuQueryParams skuQuery = SkuQueryParams.newBuilder("MERCHANT_ID")
                .setSkuList(skus)
                .orderByMobile("0935******")
                .build();
            
            
    client.querySkuPurchased(skuQuery, new FutureCompletionListener<List<SkuPurchased>>() {
            @Override
            public void onComplete(TaskResult<List<SkuPurchased>> task) {
                if (task.isSuccess()) {
                    List<SkuPurchased> skuPurchaseList = task.getSuccess();
                    for (SkuPurchased it : skuPurchaseList) {
                        Log.v("ZP_SKU_PURCHASED", "${it.authority} ${it.productId}");
                    }
                } else {
                    task.getFailure().printStackTrace();
                }
            }
        });
````

Features
========

**Dark Mode**

```java
    client.setNightMode(AppCompatDelegate.MODE_NIGHT_YES)
```    

**Appear Invoice**

```java
    client.enableShowInvoice()
```    

