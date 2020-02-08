package com.codose.idearepo.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStoreOwner;

import android.os.Bundle;
import android.widget.Toast;

import com.codose.idearepo.R;
import com.codose.idearepo.models.Idea;
import com.codose.idearepo.models.IdeaViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private IdeaViewModel ideaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ideaViewModel = ViewModelProviders.of(this).get(IdeaViewModel.class);
        ideaViewModel.getAllIdeas().observe(this, new Observer<List<Idea>>() {
            @Override
            public void onChanged(List<Idea> ideas) {
                //Update Data
                Toast.makeText(MainActivity.this, "Changed", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
