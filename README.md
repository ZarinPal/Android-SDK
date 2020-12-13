<h1>ZarinPal In App Billing - Purchase SDK | MPG</h1>
<p>ZarinPal Purchase SDK Provides payment methods on your Android Application.</p>
<p><br></p>
<h1>Requirements</h1>
<ul>
<li>Android 5.0 (API level 21) and above</li>
<li>Android Gradle Plugin&nbsp;3.5.1</li>
<li>Gradle&nbsp;5.4.1+</li>
<li>AndroidX&nbsp;(as of v11.0.0)</li>
</ul>
<h1>Installation</h1>
<p><strong>Step 1</strong></p>
<p>Add this to your root build.gradle at the end of repositories.</p>
<pre><code class="language-gradle">allprojects {
  ext.zarinpalSdkVersion = 1.0.14-beta
  repositories {
    ...
    maven { url 'https://dl.bintray.com/zarinpali/New-Payment-SDK' }
    }
 }
</code></pre>
<p><strong>Step 2</strong></p>
<p>Add the dependency:</p>
<pre><code class="language-gradle">dependencies {
  implementation 'com.zarinpal:core:ext.zarinpalSdkVersion'
  implementation 'com.zarinpal:payment-provider:ext.zarinpalSdkVersion'
}
</code></pre>
<p>If your project and business trusted to ZarinPal, SDK ables to providing <b>Mobile Payment Gateway</b> on your App so You should add the <b>MPG</b> dependency:</p>
<pre><code class="language-gradle">dependencies {
  implementation 'com.zarinpal:core:ext.zarinpalSdkVersion'
  implementation 'com.zarinpal:payment-provider:ext.zarinpalSdkVersion'
 <br><b>  implementation 'com.zarinpal:mpg:ext.zarinpalSdkVersion'</b>

  
}
</code></pre>
<h1>How to use</h1>
<p><strong>Step 1</strong></p>
<ul>
<li>add Permissions in your <code>Manifest.xml</code>:</li>
</ul>
<pre><code class="language-xml"> android:name=android.permission.INTERNET
 android:name=android.permission.ACCESS_NETWORK_STATE
</code></pre>
<p><strong>Step 2</strong></p>
<ul>
<li>get <code>ZarinPal</code> purchase instance:</li>
</ul>
<pre><code class="language-kotlin">  val zarinPalPurchase = ZarinPal.init(application)
</code></pre>
<ul>
<li>if you'll show Invoice of SDK:</li>
</ul>
<pre><code class="language-kotlin">  zarinPalPurchase.enableShowInvoice = true
</code></pre>
<p><strong>Step 3</strong></p>
<p>For <code>start</code> purchase you need a <code>Request</code> instance, <code>Request</code> has two type of Payment:</p>
<ul>
<li>as <b>Payment Request</b> by <code>Request.asPaymentRequest()</code></li>
<li>as <b>Authority ID</b> by <code>Request.asAuthority()</code></li>
</ul>
<p>If you would create payment Authority on Client, You must use <code>Request.asPayementRequest()</code>, this method needs below parameters:</p>
<p><b>Require Parameters:</b></p>
<ul>
<li>Merchant id: An unique ID of your business payment gateway.</li>
<li>Amount: Amount of Purchase.</li>
<li>Callback URL: A valid <code>URI</code> or <code>URL</code> Address for sending result purchase.</li>
<li>Description: A Content for showing payer.</li>
</ul>
<p><b>Optional Parameters:</b></p>
<ul>
<li>Mobile: Valid Mobile number of payer.</li>
<li>Email: Valid Email Address of payer.</li>
</ul>
<pre><code class="language-kotlin">val request = Request.asPaymentRequest(
            "MERCHANT_ID",
             1000,
            "https://yourServer.com/callback/",
            "A New Payment.")
</code></pre>
<p>Maybe You got <code>Authority</code> from your server, here You must use <code>Request.asAuthority()</code></p>
<pre><code class="language-kotlin">val request = Request.asAuthority("AUTHORITY")
</code></pre>
<p><strong>Step 4</strong></p>
<p>You must call <code>start</code> to begin purchase:</p>
<pre><code class="language-kotlin">  zarinPalPurchase.start(request,object :PaymentCallback{
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

</code></pre>
<p><br></p>
<p><strong>Step 5</strong></p>
<p>Finally if your eligible to have payment process through <b>MPG</b> You should adding <code>usesCleartextTraffic</code> to application tag in your  <code>Manifest.xml</code></p>
<pre><code> 
      <\application
        android:name="..."
        android:allowBackup=".."
        android:usesCleartextTraffic="true"
        android:icon="..."
        android:label="...."
        android:roundIcon="..."
        android:supportsRtl="..."
        android:theme="..."\>

</code></pre>
<p><br></p>

<h1>Releases</h1>
<ul>
<li>
<p>The Changelog&nbsp;provides a summary of changes in each release</p>
</li>
<li>
<p>The&nbsp;migration guide&nbsp;provides instructions on upgrading from old SDK.</p>
</li>
</ul>
<h1>Proguard</h1>
<p>The ZarinPal Android SDK will configure your app’s procured (ruls or text file link)</p>
<h1>Developed By</h1>
<p>The Product developed by ZarinPal Team also You can Communicate and open issue</p>
