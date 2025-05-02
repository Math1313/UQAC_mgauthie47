package uqac.dim.bestroommate.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.Executor;

import uqac.dim.bestroommate.MainActivity;
import uqac.dim.bestroommate.R;
import uqac.dim.bestroommate.databinding.FragmentAddEditTaskBinding;
import uqac.dim.bestroommate.databinding.FragmentAllTaskListBinding;
import uqac.dim.bestroommate.model.DataManager;
import uqac.dim.bestroommate.model.adapter.AllTaskAdapter;
import uqac.dim.bestroommate.model.adapter.ToDoTaskAdapter;
import uqac.dim.bestroommate.model.data.Tazz;

public class AllTaskListFragment extends Fragment {

    private FragmentAllTaskListBinding _binding;

    public AllTaskListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this._binding = FragmentAllTaskListBinding.inflate(inflater, container, false);
        this.initialize();
        return this._binding.getRoot();
    }

    private void initialize(){
        // Update the support action bar
        ((MainActivity) this.requireActivity()).updateSupportActionBar(getString(R.string.alltask_fragment_name), false, true);

        // Define the layout manager for the RecyclerView, it's the disposition of the "card"
        this._binding.allTaskListRecyclerView.setLayoutManager(new LinearLayoutManager(this.requireContext()));

        // Create the adapter with the list of tasks
        AllTaskAdapter adapter = new AllTaskAdapter(
                this.requireContext(),
                DataManager.getInstance().getCollocation().getTasks(), R.layout.all_tasks_list_item);

        adapter.setOnItemSelectedDelegate(position -> {
            showAlertDialogOptions(position);
            return true;
        });
        // Bind the adapter to the RecyclerView
        this._binding.allTaskListRecyclerView.setAdapter(adapter);
    }
    // Method to show a popup menu with the options to edit or delete a task
    @SuppressLint("NotifyDataSetChanged")
    private void showAlertDialogOptions(int taskListPosition){
        new AlertDialog.Builder(this.requireContext())
                .setTitle(getString(R.string.ad_title))
                .setMessage(getString(R.string.ad_message))
                .setIcon(R.drawable.chart_leaderboard_icon_24)
                // Button edit (right)
                .setPositiveButton(getString(R.string.ad_edit), (dialog, which) -> {
                    // If "Edit" pressed navigate to AddEditTaskFragment with the task position as argument
                    NavHostFragment
                            .findNavController(this)
                            .navigate(
                                    AllTaskListFragmentDirections
                                            .actionAllTaskListFragmentToAddEditTaskFragment()
                                            .setArgTaskPos(taskListPosition)
                            );
                })
                // Button delete (center)
                .setNeutralButton(getString(R.string.ad_delete), (dialog, which) -> {
                    Tazz task = DataManager.getInstance().getCollocation().getTasks().get(taskListPosition);
                    DataManager.getInstance().deleteTask(task);
                    // Refresh the adapter
                    Objects.requireNonNull(this._binding.allTaskListRecyclerView.getAdapter()).notifyDataSetChanged();
                    dialog.cancel();
                })
                // Button canal (left)
                .setNegativeButton(getString(R.string.ad_cancel), (dialog, which) -> {
                    dialog.cancel();
                })
                .create()
                .show();
    }
}