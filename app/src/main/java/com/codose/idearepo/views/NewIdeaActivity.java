package com.codose.idearepo.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codose.idearepo.R;
import com.google.android.material.textfield.TextInputEditText;

public class NewIdeaActivity extends AppCompatActivity {
    public static final String EDIT_TITLE =
            "com.codose.idearepo.views.EDIT_TITLE";
    public static final String EDIT_DESCRIPTION =
            "com.codose.idearepo.views.EDIT_DESCRIPTION";
    private TextInputEditText title;
    private TextInputEditText description;
    private Button save_idea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_idea);

        title = findViewById(R.id.activity_new_idea_edit_title);
        description = findViewById(R.id.activity_new_idea_edit_description);
        save_idea = findViewById(R.id.activity_new_idea_save_button);
        save_idea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveIdea();
            }
        });
    }

    private void saveIdea() {
        String ideaTitle = title.getText().toString().trim();
        String ideaDesc = description.getText().toString().trim();

        if(ideaTitle.isEmpty() || ideaDesc.isEmpty()){
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
        }else{
            Intent data = new Intent();
            data.putExtra(EDIT_TITLE, ideaTitle);
            data.putExtra(EDIT_DESCRIPTION, ideaDesc);
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
