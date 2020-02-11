package com.codose.idearepo.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.codose.idearepo.R;
import com.codose.idearepo.adapters.IdeaAdapter;
import com.codose.idearepo.models.Idea;
import com.codose.idearepo.models.IdeaViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST = 1;
    private IdeaViewModel ideaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton addIdea = findViewById(R.id.activity_main_add_idea);
        addIdea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NewIdeaActivity.class);
                startActivityForResult(i, REQUEST);
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST && resultCode == RESULT_OK){
            String title = data.getStringExtra(NewIdeaActivity.EDIT_TITLE);
            String desc = data.getStringExtra(NewIdeaActivity.EDIT_DESCRIPTION);

            Idea idea = new Idea(title,desc);
            ideaViewModel.insert(idea);
        }
    }
}
