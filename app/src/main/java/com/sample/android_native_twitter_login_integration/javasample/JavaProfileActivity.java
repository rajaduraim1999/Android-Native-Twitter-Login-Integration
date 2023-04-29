package com.sample.android_native_twitter_login_integration.javasample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.sample.android_native_twitter_login_integration.R;

public class JavaProfileActivity extends AppCompatActivity {

    ImageView ivUser;
    TextView tvUserValue;
    String twitterData, twitterProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_profile);

        initUI();

        Intent intent = getIntent();

        twitterData = intent.getStringExtra("twitterData");
        twitterProfileImage = intent.getStringExtra("twitterProfileImage");

        Glide.with(JavaProfileActivity.this)
                .load(twitterProfileImage)
                .circleCrop()
                .into(ivUser);

        tvUserValue.setText(twitterData);
    }

    /**
     * The function initializes the user interface by finding and assigning values to the ImageView and
     * TextView objects.
     */
    private void initUI() {
        ivUser = findViewById(R.id.ivUser);
        tvUserValue = findViewById(R.id.tvUserValue);
    }
}