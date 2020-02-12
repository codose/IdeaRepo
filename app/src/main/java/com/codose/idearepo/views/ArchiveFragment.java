package com.codose.idearepo.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codose.idearepo.R;
import com.codose.idearepo.ViewModels.ArchiveViewModel;
import com.codose.idearepo.adapters.ArchiveAdapter;
import com.codose.idearepo.adapters.IdeaAdapter;
import com.codose.idearepo.models.Archive;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArchiveFragment extends Fragment {

    private ArchiveViewModel archiveViewModel;
    private RecyclerView recyclerView;
    private View view;

    public ArchiveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_archive, container, false);
        // Inflate the layout for this fragment
        archiveViewModel = ViewModelProviders.of(getActivity()).get(ArchiveViewModel.class);
        initViews();


        return view;
    }

    private void initViews() {
        recyclerView = view.findViewById(R.id.fragment_archive_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        final ArchiveAdapter archiveAdapter = new ArchiveAdapter();
        recyclerView.setAdapter(archiveAdapter);
        archiveViewModel.getAllArchives().observe(getActivity(), new Observer<List<Archive>>() {
            @Override
            public void onChanged(List<Archive> archives) {
                archiveAdapter.setArchives(archives);
            }
        });
    }
}
