package com.codose.idearepo.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codose.idearepo.R;
import com.google.android.material.textfield.TextInputEditText;

import static com.codose.idearepo.adapters.IdeaAdapter.DEFAULT_COLOR;

public class NewIdeaActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EDIT_ID =
            "com.codose.idearepo.views.EDIT_ID";
    public static final String EDIT_TITLE =
            "com.codose.idearepo.views.EDIT_TITLE";
    public static final String EDIT_DESCRIPTION =
            "com.codose.idearepo.views.EDIT_DESCRIPTION";
    public static final String BG_COLOR =
            "com.codose.idearepo.views.BG_COLOR";
    private TextInputEditText title, description;
    private TextView pageTitle;
    private ImageView bgBlue , bgPink, bgYellow;
    private Button saveIdea;
    private String bgColor;
    private ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_idea);
        initViews();
        Intent i  = getIntent();
        if(i.hasExtra(EDIT_ID)){
            pageTitle.setText("Edit Idea");
            String bgColor = i.getStringExtra(BG_COLOR);
            if(bgColor == null){
                constraintLayout.setBackgroundColor(Color.parseColor(DEFAULT_COLOR));
            } else{
                constraintLayout.setBackgroundColor(Color.parseColor(bgColor));
            }
            title.setText(i.getStringExtra(EDIT_TITLE));
            description.setText(i.getStringExtra(EDIT_DESCRIPTION));
        }
        bgBlue.setOnClickListener(this);
        bgPink.setOnClickListener(this);
        bgYellow.setOnClickListener(this);
        saveIdea.setOnClickListener(view -> saveIdea());
    }

    private void initViews() {
        constraintLayout = findViewById(R.id.activity_new_idea_bg);
        bgBlue = findViewById(R.id.activity_new_idea_blue);
        bgPink = findViewById(R.id.activity_new_idea_pink);
        bgYellow = findViewById(R.id.activity_new_idea_yellow);
        title = findViewById(R.id.activity_new_idea_edit_title);
        description = findViewById(R.id.activity_new_idea_edit_description);
        saveIdea = findViewById(R.id.activity_new_idea_save_button);
        pageTitle = findViewById(R.id.activity_new_idea_page_textview);
    }


    private void setBackground(int color) {
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
            data.putExtra(BG_COLOR, bgColor);
            int id = getIntent().getIntExtra(EDIT_ID,-1);
            if(id != -1){
                data.putExtra(EDIT_ID,id);
            }
            setResult(RESULT_OK, data);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
            switch (view.getId()) {
                case R.id.activity_new_idea_blue:
                    setBackground(R.color.blue);
                    bgColor = "#909FF4";
                    break;
                case R.id.activity_new_idea_pink:
                    setBackground(R.color.pink);
                    bgColor = "#DF95AE";
                    break;
                case R.id.activity_new_idea_yellow:
                    setBackground(R.color.yellow);
                    bgColor = "#FFF5A2";
                    break;
                default:
                    break;
            }
    }
}
