package com.codose.idearepo.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.graphics.Color;
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
import com.codose.idearepo.ViewModels.RecycleViewModel;
import com.codose.idearepo.models.Archive;
import com.codose.idearepo.models.Idea;
import com.codose.idearepo.models.RecycleBin;

import java.util.ArrayList;
import java.util.List;

import static com.codose.idearepo.adapters.IdeaAdapter.DEFAULT_COLOR;

public class ArchiveAdapter extends RecyclerView.Adapter<ArchiveAdapter.IdeaHolder> {
    private List<Archive> archives = new ArrayList<>();
    private IdeaViewModel ideaViewModel;
    private ArchiveViewModel archiveViewModel;
    private RecycleViewModel recycleViewModel;
    private View itemView;
    @NonNull
    @Override
    public IdeaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_main, parent,false);
       ideaViewModel = ViewModelProviders.of((FragmentActivity) parent.getContext()).get(IdeaViewModel.class);
       archiveViewModel = ViewModelProviders.of((FragmentActivity) parent.getContext()).get(ArchiveViewModel.class);
       recycleViewModel = ViewModelProviders.of((FragmentActivity) parent.getContext()).get(RecycleViewModel.class);
        return new IdeaHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final IdeaHolder holder, int position) {
        final Archive currentIdea = archives.get(position);
        String title = currentIdea.getTitle();
        String description = currentIdea.getDescription();
        String bgColor = currentIdea.getColorId();
        Idea idea = new Idea(title,description, bgColor);
        RecycleBin recycleBin = new RecycleBin(title,description, bgColor);
        holder.title.setText(title);
        holder.desc.setText(description);
        holder.option1.setText("Restore");
        holder.option2.setText("Delete");
        if(bgColor == null){
            holder.cardView.setCardBackgroundColor(Color.parseColor(DEFAULT_COLOR));
        } else{
            holder.cardView.setCardBackgroundColor(Color.parseColor(bgColor));
        }
        holder.delete.setOnClickListener(view -> {
            archiveViewModel.delete(currentIdea);
            recycleViewModel.insert(recycleBin);
            holder.arc_del.setVisibility(View.GONE);
        });
        holder.archive.setOnClickListener(view -> {
            archiveViewModel.delete(currentIdea);
            ideaViewModel.insert(idea);
            holder.arc_del.setVisibility(View.GONE);
        });
        holder.item_cancel.setOnClickListener(view -> revealShow(false, holder));
        holder.cardView.setOnLongClickListener(view -> {
            revealShow(true, holder);
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
        return archives.size();
    }

    public void setArchives(List<Archive> archives){
        this.archives = archives;
        notifyDataSetChanged();
    }

    class IdeaHolder extends RecyclerView.ViewHolder{
        private TextView title, desc, option1, option2;
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
            option1 = itemView.findViewById(R.id.option_1);
            option2 = itemView.findViewById(R.id.option_2);

        }
    }
}
