package uqac.dim.bestroommate.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

import uqac.dim.bestroommate.R;
import uqac.dim.bestroommate.model.data.Tazz;

public class ToDoTaskAdapter extends TaskRecyclerViewAdapter<ToDoTaskAdapter.ViewHolder> {

    public ToDoTaskAdapter(@NonNull Context context, @NonNull ArrayList<Tazz> tasks, int _LayoutId) {
        // We call the parent constructor with the context, the list of tasks and the layout
        super(context, tasks, _LayoutId);
    }

    // The ViewHolder class that will be used to display the task state, it's a single "card"
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView taskTitle;
        public TextView taskType;
        public TextView taskDeadline;
        public TextView taskNote;
        public TextView taskReward;
        public ImageView taskImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.taskTitle = itemView.findViewById(R.id.task_title);
            this.taskType = itemView.findViewById(R.id.Type_textview);
            this.taskDeadline = itemView.findViewById(R.id.Deadline_textview);
            this.taskNote = itemView.findViewById(R.id.Task_note_textview);
            this.taskReward = itemView.findViewById(R.id.Task_reward_textview);
            this.taskImage = itemView.findViewById(R.id.task_image);

            // When we click on the card, we call the delegate
            itemView.setOnClickListener(v -> {
                if (onItemSelectedDelegate != null) {
                    onItemSelectedDelegate.apply(getAdapterPosition());
                }
            });
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(this.layoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tazz task = this.dataSet.get(position);
        holder.taskTitle.setText(task.getTitle());
        holder.taskType.setText(task.getType().toString());
        // Show the deadline as a string that look like DD/MM/YYYY
        holder.taskDeadline.setText(
                Instant
                        .ofEpochMilli(task.getDeadLineAsMillis())
                        .atZone(ZoneId.systemDefault())
                        .format(
                                DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                        )
        );
        holder.taskNote.setText(task.getNote());
        holder.taskReward.setText(String.valueOf(task.getReward()));
        holder.taskImage.setImageResource(task.getImageRessource());
    }
}
