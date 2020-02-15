package com.codose.idearepo.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.codose.idearepo.R;
import com.codose.idearepo.ViewModels.RecycleViewModel;
import com.codose.idearepo.adapters.RecycleAdapter;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecycleBinFragment extends Fragment {
    private RecycleViewModel recycleViewModel;
    private RecyclerView recyclerView;
    private View view;
    private Button button;
    public RecycleBinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_container, container, false);
        recycleViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(RecycleViewModel.class);
        initViews();
        return view;
    }

    private void initViews() {
        button = view.findViewById(R.id.fragment_button);
        button.setText("Delete All");
        button.setOnClickListener(view -> {
            recycleViewModel.deleteAllRecycled();
        });
        recyclerView = view.findViewById(R.id.fragment_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        final RecycleAdapter recycleAdapter = new RecycleAdapter();
        recyclerView.setAdapter(recycleAdapter);
        recycleViewModel.getAllRecycled().observe(Objects.requireNonNull(getActivity()), recycleAdapter::setRecycleBins);
    }
}
