package com.sample.android_native_google_login_integration.kotlinsample

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.sample.android_native_google_login_integration.R

class KotlinProfileActivity : AppCompatActivity() {
    var ivUser: ImageView? = null
    var tvUserValue: TextView? = null
    var twitterData: String? = null
    var twitterProfileImage:kotlin.String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_profile)
        initUI()
        val intent = intent
        twitterData = intent.getStringExtra("twitterData")
        twitterProfileImage = intent.getStringExtra("twitterProfileImage")
        Glide.with(this@KotlinProfileActivity)
            .load(twitterProfileImage)
            .circleCrop()
            .into(ivUser!!)
        tvUserValue!!.text = twitterData
    }

    /**
     * The function initializes the user interface by finding and assigning values to the ImageView and
     * TextView objects.
     */
    private fun initUI() {
        ivUser = findViewById(R.id.ivUser)
        tvUserValue = findViewById(R.id.tvUserValue)
    }
}