package com.codose.idearepo.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.codose.idearepo.R;
import com.codose.idearepo.ViewModels.ArchiveViewModel;
import com.codose.idearepo.ViewModels.RecycleViewModel;
import com.codose.idearepo.models.Archive;
import com.codose.idearepo.models.Idea;
import com.codose.idearepo.ViewModels.IdeaViewModel;
import com.codose.idearepo.models.RecycleBin;

import java.util.ArrayList;
import java.util.List;

public class IdeaAdapter extends RecyclerView.Adapter<IdeaAdapter.IdeaHolder> {
    private List<Idea> ideas = new ArrayList<>();
    private IdeaViewModel ideaViewModel;
    private ArchiveViewModel archiveViewModel;
    private RecycleViewModel recycleViewModel;
    private OnIdeaClickListener listener;
    public static final String DEFAULT_COLOR = "#FFF5A2";

    @NonNull
    @Override
    public IdeaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_main, parent, false);
       ideaViewModel = ViewModelProviders.of((FragmentActivity) parent.getContext()).get(IdeaViewModel.class);
       recycleViewModel = ViewModelProviders.of((FragmentActivity) parent.getContext()).get(RecycleViewModel.class);
       archiveViewModel = ViewModelProviders.of((FragmentActivity) parent.getContext()).get(ArchiveViewModel.class);
        return new IdeaHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final IdeaHolder holder, int position) {
        final Idea currentIdea = ideas.get(position);
        String title = currentIdea.getTitle();
        String description = currentIdea.getDescription();
        String bgColor = currentIdea.getColorId();
        final Archive archive = new Archive(title, description, bgColor);
        final RecycleBin recycleBin = new RecycleBin(title, description, bgColor);
        holder.title.setText(title);
        holder.desc.setText(description);
        if(bgColor == null){
            holder.cardView.setCardBackgroundColor(Color.parseColor(DEFAULT_COLOR));
        } else{
            holder.cardView.setCardBackgroundColor(Color.parseColor(bgColor));
        }
        holder.delete.setOnClickListener(view -> {
            ideaViewModel.delete(currentIdea);
            recycleViewModel.insert(recycleBin);
            holder.arc_del.setVisibility(View.GONE);
            notifyDataSetChanged();
        });
        holder.archive.setOnClickListener(view -> {
            ideaViewModel.delete(currentIdea);
            archiveViewModel.insert(archive);
            notifyDataSetChanged();
            holder.arc_del.setVisibility(View.GONE);
        });
        holder.item_cancel.setOnClickListener(view -> {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                revealShow(false, holder);
            }else{
                holder.arc_del.setVisibility(View.GONE);
            }
        });
        holder.cardView.setOnLongClickListener(view -> {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                revealShow(true, holder);
            }else{
                holder.arc_del.setVisibility(View.VISIBLE);
            }

            return true;
        });
    }

    private void revealShow(Boolean b, IdeaHolder holder) {
        int cx = holder.arc_del.getWidth() / 2;
        int cy = holder.arc_del.getHeight() / 2;

        int endRadius = (int) Math.hypot(cx, cy);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            if (b) {
                Animator revealAnimator = ViewAnimationUtils.createCircularReveal(holder.arc_del, cx, cy, 0, endRadius);
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

            cardView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION){
                    listener.onIdeaClick(ideas.get(position));
                }
            });

        }
    }

    public interface OnIdeaClickListener{
        void onIdeaClick(Idea idea);
    }
    public void setOnIdeaClickListener(OnIdeaClickListener listener){
        this.listener = listener;
    }
}
