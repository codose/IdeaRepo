package com.codose.idearepo.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.codose.idearepo.R;
import com.codose.idearepo.adapters.IdeaAdapter;
import com.codose.idearepo.models.Idea;
import com.codose.idearepo.models.IdeaViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private IdeaViewModel ideaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.activity_main_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);

        final IdeaAdapter ideaAdapter = new IdeaAdapter();
        recyclerView.setAdapter(ideaAdapter);

        ideaViewModel = ViewModelProviders.of(this).get(IdeaViewModel.class);
        ideaViewModel.getAllIdeas().observe(this, new Observer<List<Idea>>() {
            @Override
            public void onChanged(List<Idea> ideas) {
                //Update Data
                ideaAdapter.setIdeas(ideas);
                Toast.makeText(MainActivity.this, "Changed", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
