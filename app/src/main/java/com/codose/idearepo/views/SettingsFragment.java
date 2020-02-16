package com.codose.idearepo.views;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.codose.idearepo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pixplicity.easyprefs.library.Prefs;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {
    private Switch theme_switch;
    private View view;
    public static final String DARK_MODE = "com.codose.idearepo.views.DARK_MODE";
    private FloatingActionButton fab;
    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        theme_switch = view.findViewById(R.id.theme_switch);
        Boolean dark = Prefs.getBoolean(DARK_MODE,false);
        if(dark){
            theme_switch.setChecked(true);
        }
        fab = view.findViewById(R.id.save_fab);
        switchListeners();
        fab.setOnClickListener(view -> {
            getActivity().recreate();
        });
        return view;
    }

    private void switchListeners() {
        theme_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Prefs.putBoolean(DARK_MODE,true);
                }else {
                    Prefs.putBoolean(DARK_MODE,false);
                }
            }
        });
    }

}
