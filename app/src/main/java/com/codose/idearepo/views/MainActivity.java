package com.codose.idearepo.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.codose.idearepo.R;
import com.codose.idearepo.adapters.IdeaAdapter;
import com.codose.idearepo.models.Idea;
import com.codose.idearepo.models.IdeaViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST = 1;
    private IdeaViewModel ideaViewModel;
    private ImageView drawer_ctrl;
    private DrawerLayout drawerLayout;
    private ConstraintLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        content = findViewById(R.id.constraint_layout);
        FloatingActionButton addIdea = findViewById(R.id.activity_main_add_idea);
        drawer_ctrl = findViewById(R.id.activity_main_drawer_ctrl);
        drawer_ctrl.setOnClickListener(this);
        addIdea.setOnClickListener(this);
        drawerSlide();
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
            }
        });

    }

    private void drawerSlide() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close) {
            private float scaleFactor = 6f;

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float slideX = drawerView.getWidth() * slideOffset;
                content.setTranslationX(slideX);
                content.setScaleX(1 - (slideOffset / scaleFactor));
                content.setScaleY(1 - (slideOffset / scaleFactor));
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
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

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.activity_main_drawer_ctrl:
                openDrawer();
                break;
            case R.id.activity_main_add_idea :
                Intent i = new Intent(MainActivity.this, NewIdeaActivity.class);
                startActivityForResult(i, REQUEST);
                break;
            default:

        }
    }

    private void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }
}
