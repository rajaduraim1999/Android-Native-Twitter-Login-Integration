package com.sample.android_native_google_login_integration.kotlinsample

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.sample.android_native_google_login_integration.R
import com.sample.android_native_google_login_integration.javasample.JavaProfileActivity
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.identity.TwitterAuthClient
import com.twitter.sdk.android.core.identity.TwitterLoginButton
import com.twitter.sdk.android.core.models.User

class KotlinTwitterSignInActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = "JavaTwitterSignIn"

    var tlbTwitterSignIn: TwitterLoginButton? = null
    var cvTwitterSignIn: CardView? = null

    private var client: TwitterAuthClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_twitter_sign_in)

        initUI()

        initTwitter()

        val twitterLoginButton = findViewById<TwitterLoginButton>(R.id.tlbTwitterSignIn)
        twitterLoginButton.callback = object : Callback<TwitterSession?>() {
            override fun success(result: Result<TwitterSession?>) {
                // Do something with result, which provides a TwitterSession for making API calls
                signInAuthorize()
            }

            override fun failure(exception: TwitterException) {
                // Do something on failure
                exception.printStackTrace()
                Log.e(TAG, "Login failed!")
            }
        }

        tlbTwitterSignIn!!.setOnClickListener(this)
        cvTwitterSignIn!!.setOnClickListener(this)

    }

    private fun initUI() {
        tlbTwitterSignIn = findViewById(R.id.tlbTwitterSignIn)
        cvTwitterSignIn = findViewById(R.id.cvTwitterSignIn)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.cvTwitterSignIn -> signIn()
        }
    }

    fun initTwitter() {
        val authConfig = TwitterAuthConfig(
            resources.getString(R.string.com_twitter_sdk_android_CONSUMER_KEY),
            resources.getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET)
        )
        val builder = TwitterConfig.Builder(this)
        builder.twitterAuthConfig(authConfig)
        Twitter.initialize(builder.build())
        client = TwitterAuthClient()
    }

    private fun signIn() {
        client!!.authorize(this@KotlinTwitterSignInActivity, object : Callback<TwitterSession?>() {
            override fun success(result: Result<TwitterSession?>) {
                // Do something with result, which provides a TwitterSession for making API calls
                signInAuthorize()
            }

            override fun failure(exception: TwitterException) {
                // Do something on failure
                exception.printStackTrace()
                Log.e(TAG, "Login failed!")
            }
        })
    }

    private fun signInAuthorize() {
        val session = TwitterCore.getInstance().sessionManager.activeSession
        val username = session.userName
        val id = session.id
        val userId = session.userId
        val authToken = session.authToken
        val token = authToken.token
        val secret = authToken.secret
        Log.e(
            TAG,
            "success: username : $username id : $id userId : $userId token : $token secret : $secret"
        )
        client!!.requestEmail(session, object : Callback<String>() {
            override fun success(result: Result<String>) {
                // Do something with the result, which provides the email address
                // the email is saved in the result variable 'result.data'
                val email = result.data
                Log.e(TAG, "success: email : $email")
                TwitterCore.getInstance().getApiClient(session).accountService.verifyCredentials(
                    false,
                    true,
                    false
                ).enqueue(object : Callback<User>() {
                    override fun success(userResult: Result<User>) {
                        val user = userResult.data
                        val profileImageUrl = user.profileImageUrlHttps
                        val name = user.name
                        val description = user.description
                        val followersCount = user.followersCount.toString()
                        val createdAt = user.createdAt
                        val screenName = user.screenName
                        val friendsCount = user.friendsCount.toString()
                        try {
                            val twitterData =
                                " username : $username\n\nemail : $email\n\n id : $id\n\nuserId : $userId\n\ntoken : $token\n\nsecret : $secret\n\nsecret : $secret\n\ndescription : $description\n\nprofileImageUrl : $profileImageUrl\n\nfollowersCount : $followersCount\n\ncreatedAt : $createdAt\n\nscreenName : $screenName\n\nfriendsCount : $friendsCount"
                            val intent = Intent(
                                this@KotlinTwitterSignInActivity,
                                JavaProfileActivity::class.java
                            )
                            intent.putExtra("twitterData", twitterData)
                            intent.putExtra("twitterProfileImage", profileImageUrl)
                            startActivity(intent)
                            signOut()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun failure(e: TwitterException) {}
                })
            }

            override fun failure(exception: TwitterException) {
                // Do something on failure
                exception.printStackTrace()
                Log.e(TAG, "Login failed!")
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        client!!.onActivityResult(requestCode, resultCode, data)
    }

    fun signOut() {
        val twitterSession = TwitterCore.getInstance().sessionManager.activeSession
        Log.e(
            TAG, "disconnectFromTwitter: twitterSession : $twitterSession"
        )
        if (twitterSession != null) {
            clearCookies(applicationContext)
            //            Twitter.getSessionManager().clearActiveSession();
//            Twitter.logOut();
        }
    }


    fun clearCookies(context: Context?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null)
            CookieManager.getInstance().flush()
        } else {
            val cookieSyncMngr = CookieSyncManager.createInstance(context)
            cookieSyncMngr.startSync()
            val cookieManager = CookieManager.getInstance()
            cookieManager.removeAllCookie()
            cookieManager.removeSessionCookie()
            cookieSyncMngr.stopSync()
            cookieSyncMngr.sync()
        }
    }

}