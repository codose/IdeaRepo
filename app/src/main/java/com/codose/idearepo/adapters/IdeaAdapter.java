package com.codose.idearepo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codose.idearepo.R;
import com.codose.idearepo.models.Idea;

import java.util.ArrayList;
import java.util.List;

public class IdeaAdapter extends RecyclerView.Adapter<IdeaAdapter.IdeaHolder> {
    private List<Idea> ideas = new ArrayList<>();

    @NonNull
    @Override
    public IdeaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_main, parent,false);
        return new IdeaHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IdeaHolder holder, int position) {
        Idea currentIdea = ideas.get(position);
        holder.title.setText(currentIdea.getTitle());
        holder.title.setText(currentIdea.getDescription());
    }

    @Override
    public int getItemCount() {
        return ideas.size();
    }

    public void setIdeas(List<Idea> ideas){
        this.ideas = ideas;
        notifyDataSetChanged();
    }

    class IdeaHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView desc;

        public IdeaHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_activity_main_title);
            title = itemView.findViewById(R.id.item_activity_main_desc);
        }
    }
}
