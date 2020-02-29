package com.codose.idearepo.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.codose.idearepo.R;
import com.pixplicity.easyprefs.library.Prefs;

import static com.codose.idearepo.views.SettingsFragment.DARK_MODE;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initPrefLib();
        setAppTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Boolean dark = Prefs.getBoolean(DARK_MODE, false);
        ImageView splashImage = findViewById(R.id.activity_splash_logo_image_view);
        if(dark){
            splashImage.setImageResource(R.drawable.idea_repo2);
        }
        Animation heartBeatAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        splashImage.setAnimation(heartBeatAnim);
        navigateTo();
    }

    private void navigateTo() {
        new Handler().postDelayed(() -> {
                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);
                finish();
        }, 3000);
    }
}
