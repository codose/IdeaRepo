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

import static com.codose.idearepo.views.HomeFragment.REQUEST;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView page_title;
    private ImageView drawer_ctrl;
    private DrawerLayout drawerLayout;
    private ConstraintLayout content;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        drawerSlide();
        navListeners();
    }

    private void initViews() {
        drawerLayout = findViewById(R.id.drawer_layout);
        page_title = findViewById(R.id.page_title);
        navigationView = findViewById(R.id.navigation_view);
        content = findViewById(R.id.constraint_layout);
        drawer_ctrl = findViewById(R.id.activity_main_drawer_ctrl);
        drawer_ctrl.setOnClickListener(this);
        Fragment fragment = new HomeFragment();
        setUpFragment(fragment, "My Ideas");
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
    public void onClick(View view) {
        if (view.getId() == R.id.activity_main_drawer_ctrl) {
            openDrawer();
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
                    fragment = new HomeFragment();
                    navigationView.setCheckedItem(R.id.nav_archived);
                    title[0] = "My Ideas";
                    navigationView.setCheckedItem(R.id.nav_home);
                    break;
                case R.id.nav_archived:
                    fragment = new ArchiveFragment();
                    navigationView.setCheckedItem(R.id.nav_archived);
                    title[0] = "Archived";
                    break;
                case R.id.nav_recycle_bin:
                    fragment = new RecycleBinFragment();
                    navigationView.setCheckedItem(R.id.nav_recycle_bin);
                    title[0] = "Recycle Bin";
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
