package com.codose.idearepo.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.codose.idearepo.R;
import com.codose.idearepo.ViewModels.ArchiveViewModel;
import com.codose.idearepo.ViewModels.IdeaViewModel;
import com.codose.idearepo.models.Archive;
import com.codose.idearepo.models.Idea;

import java.util.ArrayList;
import java.util.List;

public class ArchiveAdapter extends RecyclerView.Adapter<ArchiveAdapter.IdeaHolder> {
    private List<Archive> archives = new ArrayList<>();
    private IdeaViewModel ideaViewModel;
    private ArchiveViewModel archiveViewModel;
    private View itemView;
    @NonNull
    @Override
    public IdeaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_main, parent,false);
       ideaViewModel = ViewModelProviders.of((FragmentActivity) parent.getContext()).get(IdeaViewModel.class);
       archiveViewModel = ViewModelProviders.of((FragmentActivity) parent.getContext()).get(ArchiveViewModel.class);
        return new IdeaHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final IdeaHolder holder, int position) {
        final Archive currentIdea = archives.get(position);
        String title = currentIdea.getTitle();
        String description = currentIdea.getDescription();
        holder.title.setText(title);
        holder.desc.setText(description);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        holder.archive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.arc_del.setVisibility(View.GONE);
            }
        });
        holder.item_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                revealShow(false, holder);
            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                revealShow(true, holder);
                return true;
            }
        });
    }

    private void revealShow(Boolean b, IdeaHolder holder) {

        int cx = holder.arc_del.getWidth() / 2;
        int cy = holder.arc_del.getHeight() / 2;

        int endRadius = (int) Math.hypot(cx, cy);

        if (b) {
            Animator revealAnimator = ViewAnimationUtils.createCircularReveal(holder.arc_del, cx,cy, 0, endRadius);
            holder.arc_del.setVisibility(View.VISIBLE);
            revealAnimator.setDuration(700);
            revealAnimator.start();
        } else {
            Animator anim =
                    ViewAnimationUtils.createCircularReveal(holder.arc_del, cx, cy, endRadius, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    holder.arc_del.setVisibility(View.GONE);
                }
            });
            anim.setDuration(700);
            anim.start();
        }
    }

    @Override
    public int getItemCount() {
        return archives.size();
    }

    public void setArchives(List<Archive> archives){
        this.archives = archives;
        notifyDataSetChanged();
    }

    class IdeaHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView desc;
        private ConstraintLayout arc_del;
        private CardView cardView;
        private ImageView item_cancel, archive, delete;

        public IdeaHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.item_activity_main_title);
            desc = itemView.findViewById(R.id.item_activity_main_desc);
            arc_del = itemView.findViewById(R.id.item_archive_delete);
            cardView = itemView.findViewById(R.id.card_item);
            item_cancel = itemView.findViewById(R.id.item_cancel);
            archive = itemView.findViewById(R.id.archive_item);
            delete = itemView.findViewById(R.id.recycle_item);

        }
    }
}
