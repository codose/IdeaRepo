package com.codose.idearepo.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codose.idearepo.R;
import com.codose.idearepo.adapters.IdeaAdapter;
import com.codose.idearepo.models.Idea;
import com.codose.idearepo.ViewModels.IdeaViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST = 1;
    private IdeaViewModel ideaViewModel;
    private TextView page_title;
    private ImageView drawer_ctrl;
    private DrawerLayout drawerLayout;
    private ConstraintLayout content;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        page_title = findViewById(R.id.page_title);
        navigationView = findViewById(R.id.navigation_view);
        content = findViewById(R.id.constraint_layout);
        FloatingActionButton addIdea = findViewById(R.id.activity_main_add_idea);
        drawer_ctrl = findViewById(R.id.activity_main_drawer_ctrl);
        drawer_ctrl.setOnClickListener(this);
        addIdea.setOnClickListener(this);
        drawerSlide();
        RecyclerView recyclerView = findViewById(R.id.activity_main_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);

        final IdeaAdapter ideaAdapter = new IdeaAdapter();
        recyclerView.setAdapter(ideaAdapter);

        ideaViewModel = ViewModelProviders.of(this).get(IdeaViewModel.class);
        //Update Data
        ideaViewModel.getAllIdeas().observe(this, ideaAdapter::setIdeas);
        navListeners();
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
        }
    }

    private void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }
    private void hideDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }


    private void navListeners() {
        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.bringToFront();
        final String[] title = new String[1];
        navigationView.setNavigationItemSelectedListener(menuItem -> {
           MenuItem checkedItem = navigationView.getCheckedItem();
           if (checkedItem != null && checkedItem.getItemId() == menuItem.getItemId()) return false;
            Fragment fragment=null;
            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                    finish();
                    navigationView.setCheckedItem(R.id.nav_home);
                    break;
                case R.id.nav_archived:
                    fragment = new ArchiveFragment();
                    Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
                    Log.e("ARCHIVE", "Clicked");
                    navigationView.setCheckedItem(R.id.nav_archived);
                    title[0] = "Archived";
                    break;
                case R.id.nav_recycle_bin:

                    break;

            }

            if(fragment !=null){
                setUpFragment(fragment, title[0]);
            }
            hideDrawer();
            return true;
        });
    }

    private void setUpFragment(Fragment fragment, String title) {
        page_title.setText(title);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();

    }
}
