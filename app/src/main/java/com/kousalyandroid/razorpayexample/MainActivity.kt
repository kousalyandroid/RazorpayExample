package com.kousalyandroid.razorpayexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity(), PaymentResultListener {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /**
         * Preload payment resources
         */
        Checkout.preload(applicationContext);
        findViewById<TextView>(R.id.tv_make_payment).setOnClickListener {
            startPayment()
        }
    }

    private fun startPayment() {
        /*
        *  You need to pass current activity in order to let Razorpay create CheckoutActivity
        * */
        val activity = this
        val checkout = Checkout()
        //key from generate api key in razorpay
        checkout.setKeyID("rzp_test_70PnV4lyRypzyh")
        try {
            val options = JSONObject()
            //order info
            options.put("name", "Razorpay Corp")
            options.put("description", "Demoing Charges")
//            options.put("currency", "INR")
            options.put("order_id", "*****")
//            options.put("amount", "50000")//pass amount in currency subunits

            //retry if failed
            val retryObj = JSONObject()
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            //person details
            val prefill = JSONObject()
            prefill.put("email", "kousalya77k@gmail.com")
            prefill.put("contact", "7358490633")
            options.put("prefill", prefill)

            //launches the Checkout form
            checkout.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
        Log.d(TAG, "onPaymentSuccess: $p0")
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
        Log.d(TAG, "onPaymentSuccess: p0==> $p0, p1==> $p1")
    }
}