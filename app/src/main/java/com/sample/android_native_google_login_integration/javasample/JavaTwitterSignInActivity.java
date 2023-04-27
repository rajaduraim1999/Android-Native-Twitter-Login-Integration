package com.sample.android_native_google_login_integration.javasample;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.sample.android_native_google_login_integration.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

public class JavaTwitterSignInActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "JavaTwitterSignIn";

    TwitterLoginButton tlbTwitterSignIn;
    CardView cvTwitterSignIn;

    private TwitterAuthClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_twitter_sign_in);

        initUI();

        initTwitter();

        TwitterLoginButton twitterLoginButton = findViewById(R.id.tlbTwitterSignIn);
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                signInAuthorize();
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                exception.printStackTrace();
                Log.e(TAG, "Login failed!");
            }
        });

        tlbTwitterSignIn.setOnClickListener(this);
        cvTwitterSignIn.setOnClickListener(this);

    }

    private void initUI() {
        tlbTwitterSignIn = findViewById(R.id.tlbTwitterSignIn);
        cvTwitterSignIn = findViewById(R.id.cvTwitterSignIn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.tlbTwitterSignIn:
            case R.id.cvTwitterSignIn:
                signIn();
                break;
        }
    }

    void initTwitter() {

        TwitterAuthConfig authConfig = new TwitterAuthConfig(
                getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_KEY),
                getResources().getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET));
        TwitterConfig.Builder builder = new TwitterConfig.Builder(this);
        builder.twitterAuthConfig(authConfig);

        Twitter.initialize(builder.build());

        client = new TwitterAuthClient();
    }

    private void signIn() {
        client.authorize(JavaTwitterSignInActivity.this, new com.twitter.sdk.android.core.Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                signInAuthorize();
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                exception.printStackTrace();
                Log.e(TAG, "Login failed!");
            }
        });
    }

    private void signInAuthorize() {
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        String username = session.getUserName();
        long id = session.getId();
        long userId = session.getUserId();
        TwitterAuthToken authToken = session.getAuthToken();
        String token = authToken.token;
        String secret = authToken.secret;

        Log.e(TAG, "success: username : " + username + " id : " + id + " userId : " + userId + " token : " + token + " secret : " + secret);

        client.requestEmail(session, new com.twitter.sdk.android.core.Callback<String>() {
            @Override
            public void success(Result<String> result) {
                // Do something with the result, which provides the email address
                // the email is saved in the result variable 'result.data'
                String email = result.data;
                Log.e(TAG, "success: email : " + email);

                TwitterCore.getInstance().getApiClient(session).getAccountService().verifyCredentials(false, true, false).enqueue(new com.twitter.sdk.android.core.Callback<User>() {
                    @Override
                    public void success(Result<User> userResult) {
                        User user = userResult.data;
                        String profileImageUrl = user.profileImageUrlHttps;
                        String name = user.name;
                        String description = user.description;
                        String followersCount = String.valueOf(user.followersCount);
                        String createdAt = user.createdAt;
                        String screenName = user.screenName;
                        String friendsCount = String.valueOf(user.friendsCount);

                        try {

                            String twitterData = " username : " + username + "\n\n" + "email : " + email + "\n\n" + " id : " + id + "\n\n" + "userId : " + userId + "\n\n" + "token : " + token + "\n\n" + "secret : " + secret + "\n\n" + "secret : " + secret + "\n\n" + "description : " + description + "\n\n" + "profileImageUrl : " + profileImageUrl + "\n\n" + "followersCount : " + followersCount + "\n\n" + "createdAt : " + createdAt + "\n\n" + "screenName : " + screenName + "\n\n" + "friendsCount : " + friendsCount;

                            Intent intent = new Intent(JavaTwitterSignInActivity.this, JavaProfileActivity.class);
                            intent.putExtra("twitterData", twitterData);
                            intent.putExtra("twitterProfileImage", profileImageUrl);
                            startActivity(intent);
                            signOut();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void failure(TwitterException e) {
                    }
                });

            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                exception.printStackTrace();
                Log.e(TAG, "Login failed!");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        client.onActivityResult(requestCode, resultCode, data);

    }

    public void signOut() {
        TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
        Log.e(TAG, "disconnectFromTwitter: twitterSession : " + twitterSession);
        if (twitterSession != null) {
            clearCookies(getApplicationContext());
//            Twitter.getSessionManager().clearActiveSession();
//            Twitter.logOut();
        }
    }


    public static void clearCookies(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }

}