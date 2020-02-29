package com.codose.idearepo.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.codose.idearepo.R;
import com.pixplicity.easyprefs.library.Prefs;

import static com.codose.idearepo.views.SettingsFragment.DARK_MODE;

public class BaseActivity extends AppCompatActivity {
    private boolean dark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initPrefLib() {
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
    }


    public void setAppTheme() {
        dark = Prefs.getBoolean(DARK_MODE, false);
        if(dark){
            setTheme(R.style.DarkAppTheme);
        } else{
            setTheme(R.style.mAppTheme);
        }
    }

}
