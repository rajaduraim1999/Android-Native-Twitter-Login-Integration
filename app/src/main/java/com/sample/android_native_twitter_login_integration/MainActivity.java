package com.sample.android_native_twitter_login_integration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sample.android_native_twitter_login_integration.javasample.JavaTwitterSignInActivity;
import com.sample.android_native_twitter_login_integration.kotlinsample.KotlinTwitterSignInActivity;

public class MainActivity extends AppCompatActivity {

    CardView cvJava, cvKotlin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        // `cvJava.setOnClickListener` sets a click listener on the `cvJava` CardView. When the
        // CardView is clicked, it creates an intent to start the `JavaGoogleSignInActivity` and starts
        // the activity using `startActivity(intent)`. This allows the user to navigate to the Java
        // version of the Google Sign-In screen.
        cvJava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, JavaTwitterSignInActivity.class);
                startActivity(intent);
            }
        });

        // `cvKotlin.setOnClickListener` sets a click listener on the `cvKotlin` CardView. When the
        // CardView is clicked, it creates an intent to start the `KotlinGoogleSignInActivity` and
        // starts the activity using `startActivity(intent)`. This allows the user to navigate to the
        // Kotlin version of the Google Sign-In screen.
        cvKotlin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, KotlinTwitterSignInActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * The function initializes UI elements for Java and Kotlin.
     */
    private void initUI() {
        cvJava = findViewById(R.id.cvJava);
        cvKotlin = findViewById(R.id.cvKotlin);
    }
}