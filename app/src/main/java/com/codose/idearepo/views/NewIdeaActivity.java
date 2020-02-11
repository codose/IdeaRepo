package com.codose.idearepo.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.codose.idearepo.R;
import com.google.android.material.textfield.TextInputEditText;

public class NewIdeaActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EDIT_TITLE =
            "com.codose.idearepo.views.EDIT_TITLE";
    public static final String EDIT_DESCRIPTION =
            "com.codose.idearepo.views.EDIT_DESCRIPTION";
    private TextInputEditText title;
    private TextInputEditText description;
    private ImageView bg_blue;
    private ImageView bg_pink;
    private ImageView bg_yellow;
    private Button save_idea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_idea);
        bg_blue = findViewById(R.id.activity_new_idea_blue);
        bg_pink = findViewById(R.id.activity_new_idea_pink);
        bg_yellow = findViewById(R.id.activity_new_idea_yellow);
        bg_blue.setOnClickListener(this);
        bg_pink.setOnClickListener(this);
        bg_yellow.setOnClickListener(this);
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



    private void setBackground(int color) {
        ConstraintLayout constraintLayout = findViewById(R.id.activity_new_idea_bg);
        constraintLayout.setBackgroundColor(getResources().getColor(color));

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

    @Override
    public void onClick(View view) {
            switch (view.getId()) {
                case R.id.activity_new_idea_blue:
                    setBackground(R.color.colorPrimary);
                    break;
                case R.id.activity_new_idea_pink:
                    setBackground(R.color.colorAccent);
                    break;
                case R.id.activity_new_idea_yellow:
                    setBackground(R.color.yellow);
                    break;
                default:
                    break;
            }
    }
}
