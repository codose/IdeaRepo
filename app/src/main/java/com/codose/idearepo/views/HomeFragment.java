package com.codose.idearepo.views;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.codose.idearepo.R;
import com.codose.idearepo.ViewModels.IdeaViewModel;
import com.codose.idearepo.adapters.ArchiveAdapter;
import com.codose.idearepo.adapters.IdeaAdapter;
import com.codose.idearepo.models.Idea;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    public static final int REQUEST = 1;
    private RecyclerView recyclerView;
    private IdeaViewModel ideaViewModel;
    private View view;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_container, container, false);
        ideaViewModel = ViewModelProviders.of(getActivity()).get(IdeaViewModel.class);
        initViews();
        return view;
    }

    private void initViews() {
        Button button = view.findViewById(R.id.fragment_button);
        button.setOnClickListener(view -> {
            Intent i = new Intent(getContext(), NewIdeaActivity.class);
            startActivityForResult(i, REQUEST);
        });
        button.setText("Add Idea");
        button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add, 0,0, 0);
        recyclerView = view.findViewById(R.id.fragment_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        final IdeaAdapter ideaAdapter = new IdeaAdapter();
        recyclerView.setAdapter(ideaAdapter);
        ideaViewModel.getAllIdeas().observe(getActivity(), ideaAdapter::setIdeas);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST && resultCode == RESULT_OK){
            String title = data.getStringExtra(NewIdeaActivity.EDIT_TITLE);
            String desc = data.getStringExtra(NewIdeaActivity.EDIT_DESCRIPTION);
            Idea idea = new Idea(title,desc);
            ideaViewModel.insert(idea);
        }
    }
}
