package com.codose.idearepo.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.codose.idearepo.R;
import com.codose.idearepo.ViewModels.ArchiveViewModel;
import com.codose.idearepo.ViewModels.IdeaViewModel;
import com.codose.idearepo.adapters.ArchiveAdapter;
import com.codose.idearepo.adapters.IdeaAdapter;
import com.codose.idearepo.models.Archive;
import com.codose.idearepo.models.Idea;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArchiveFragment extends Fragment {

    private ArchiveViewModel archiveViewModel;
    private IdeaViewModel ideaViewModel;
    private RecyclerView recyclerView;
    private View view;
    private Button button;

    public ArchiveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_container, container, false);

        // Inflate the layout for this fragment
        archiveViewModel = ViewModelProviders.of(getActivity()).get(ArchiveViewModel.class);
        ideaViewModel = ViewModelProviders.of(getActivity()).get(IdeaViewModel.class);
        initViews();


        return view;
    }

    private void initViews() {
        button = view.findViewById(R.id.fragment_button);
        button.setOnClickListener(view -> {
            LiveData<List<Archive>> allArchives = archiveViewModel.getAllArchives();
            List<Archive> archives = allArchives.getValue();
            for (int i = 0 ; i<archives.size();i++){
                String title = archives.get(i).getTitle();
                String desc = archives.get(i).getDescription();
                String bgColor = archives.get(i).getColorId();
                Idea idea = new Idea(title, desc, bgColor);
                ideaViewModel.insert(idea);
            }
            archiveViewModel.deleteAllArchives();
        });
        recyclerView = view.findViewById(R.id.fragment_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        final ArchiveAdapter archiveAdapter = new ArchiveAdapter();
        recyclerView.setAdapter(archiveAdapter);
        archiveViewModel.getAllArchives().observe(getActivity(), archiveAdapter::setArchives);
    }
}
