package com.codose.idearepo.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codose.idearepo.R;
import com.codose.idearepo.ViewModels.IdeaViewModel;
import com.codose.idearepo.models.Idea;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.codose.idearepo.views.HomeFragment.EDIT_REQUEST;
import static com.codose.idearepo.views.NewIdeaActivity.EDIT_DESCRIPTION;
import static com.codose.idearepo.views.NewIdeaActivity.EDIT_ID;
import static com.codose.idearepo.views.NewIdeaActivity.EDIT_TITLE;

public class IdeaActivity extends AppCompatActivity {
    private TextView title,description;
    private FloatingActionButton editIdea;
    private IdeaViewModel ideaViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_idea);
        ideaViewModel = ViewModelProviders.of(this).get(IdeaViewModel.class);
        title = findViewById(R.id.fragment_idea_title);
        description = findViewById(R.id.fragment_idea_description);
        editIdea = findViewById(R.id.fragment_idea_edit_fab);
        String ideaTitle = getIntent().getStringExtra(EDIT_TITLE);
        String ideaDescription = getIntent().getStringExtra(EDIT_DESCRIPTION);
        int ideaId = getIntent().getIntExtra(EDIT_ID,0);
        title.setText(ideaTitle);
        description.setText(ideaDescription);

        editIdea.setOnClickListener(view -> {
            Intent intent = new Intent(this,NewIdeaActivity.class);
            intent.putExtra(EDIT_TITLE,ideaTitle);
            intent.putExtra(EDIT_DESCRIPTION,ideaDescription);
            intent.putExtra(EDIT_ID,ideaId);
            startActivityForResult(intent,EDIT_REQUEST);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == EDIT_REQUEST && resultCode == RESULT_OK){
            String ideaTitle = data.getStringExtra(EDIT_TITLE);
            String desc = data.getStringExtra(EDIT_DESCRIPTION);
            title.setText(ideaTitle);
            description.setText(desc);
            int id = data.getIntExtra(EDIT_ID, -1);
            Idea idea = new Idea(ideaTitle,desc);
            idea.setId(id);
            ideaViewModel.update(idea);
        }
    }
}
