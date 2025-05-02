package uqac.dim.bestroommate.model.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uqac.dim.bestroommate.R;
import uqac.dim.bestroommate.model.data.Tazz;

public class AllTaskAdapter extends TaskRecyclerViewAdapter<AllTaskAdapter.ViewHolder>{

    public AllTaskAdapter(@NonNull Context context, @NonNull ArrayList<Tazz> tasks, int _LayoutId) {
        // We call the parent constructor with the context, the list of tasks and the layout
        super(context, tasks, _LayoutId);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView taskTitle;
        public TextView taskType;
        public TextView taskState;
        public TextView checkUser;
        public TextView taskReward;
        public ImageView taskImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.taskTitle = itemView.findViewById(R.id.task_title);
            this.taskType = itemView.findViewById(R.id.Type_textview);
            this.taskState = itemView.findViewById(R.id.state_textview);
            this.taskReward = itemView.findViewById(R.id.Task_reward_textview);
            this.taskImage = itemView.findViewById(R.id.task_image);
            this.checkUser = itemView.findViewById(R.id.user_textView);

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
    public AllTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AllTaskAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(this.layoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tazz task = this.dataSet.get(position);
        holder.taskTitle.setText(task.getTitle());
        holder.taskType.setText(task.getType().toString());
        holder.taskReward.setText(String.valueOf(task.getReward()));
        Log.d("VALUE_OF", "" + task.getReward());
        holder.taskImage.setImageResource(task.getType().ressource);
        holder.checkUser.setText(task.getUser() == null ? "No user" : task.getUser().getUsername());
    }
}
