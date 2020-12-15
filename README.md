ZarinPal In App Billing - Purchase SDK | MPG
============================================

ZarinPal Purchase SDK Provides payment methods on your Android Application.

<center>
<img src="https://github.com/ZarinPal-Lab/Android-PaymentGateway-SDK/blob/master/ezgif.com-gif-maker.gif?raw=true" alt="sample" width="200" height="400"/>
</center>

  

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
      ext.zarinpalSdkVersion = 0.1.20-beta
      repositories {
        ...
        maven { url 'https://dl.bintray.com/zarinpali/payment-sdk' }
        }
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

**Step 1**

*   add Permissions in your `Manifest.xml`:
```xml
     android:name=android.permission.INTERNET
     android:name=android.permission.ACCESS_NETWORK_STATE
```    

**Step 2**

*   get `ZarinPal` purchase instance:
```kotlin
      val zarinPalPurchase = ZarinPal.init(application)
```    

*   if you'll show Invoice of SDK:
```kotlin
      zarinPalPurchase.enableShowInvoice = true
```    

**Step 3**

For `start` purchase you need a `Request` instance, `Request` has two type of Payment:

*   as **Payment Request** by `Request.asPaymentRequest()`
*   as **Authority ID** by `Request.asAuthority()`

If you would create payment Authority on Client, You must use `Request.asPayementRequest()`, this method needs below parameters:

**Require Parameters:**

*   Merchant id: An unique ID of your business payment gateway.
*   Amount: Amount of Purchase.
*   Callback URL: A valid `URI` or `URL` Address for sending result purchase.
*   Description: A Content for showing payer.

**Optional Parameters:**

*   Mobile: Valid Mobile number of payer.
*   Email: Valid Email Address of payer.

```kotlin
    val request = Request.asPaymentRequest(
                "MERCHANT_ID",
                 1000,
                "https://yourServer.com/callback/",
                "A New Payment.")
```    

Maybe You got `Authority` from your server, here You must use `Request.asAuthority()`
```kotlin
    val request = Request.asAuthority("AUTHORITY")
```   

**Step 4**

You must call `start` to begin purchase:
```kotlin
      zarinPalPurchase.start(request,object :PaymentCallback{
                override fun onSuccess(receipt: Receipt, raw: String) {
                    //TODO: When purchase is success .
                }
    
                override fun onException(ex: Exception) {
                   //TODO: When purchase facing exception.
                }
    
                override fun onClose() {
                   //TODO: When Close purchase.
                }
            })
```    
    

  

**Step 5**

Finally if your eligible to have payment process through **MPG** You should adding `usesCleartextTraffic` to application tag in your `Manifest.xml`
```xml
     
          <\application
            android:name="..."
            android:allowBackup=".."
            android:usesCleartextTraffic="true"
            android:icon="..."
            android:label="...."
            android:roundIcon="..."
            android:supportsRtl="..."
            android:theme="..."\>
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

ZarinPal In App Billing - Purchase SDK | MPG
============================================

ZarinPal Purchase SDK Provides payment methods on your Android Application.
