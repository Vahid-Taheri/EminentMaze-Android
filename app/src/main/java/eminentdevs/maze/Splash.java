package eminentdevs.maze;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_splash);

        //Run MainActivity after splash
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                ///main : the activity that runs after a few seconds
                startActivity(new Intent(Splash.this, InputActivity.class));
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        }, 5000); // time based on ms
    }
}
