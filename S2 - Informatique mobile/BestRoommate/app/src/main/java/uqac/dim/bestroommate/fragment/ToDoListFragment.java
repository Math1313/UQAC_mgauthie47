package uqac.dim.bestroommate.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import uqac.dim.bestroommate.MainActivity;
import uqac.dim.bestroommate.R;
import uqac.dim.bestroommate.databinding.FragmentToDoListBinding;
import uqac.dim.bestroommate.model.DataManager;
import uqac.dim.bestroommate.model.adapter.ToDoTaskAdapter;
import uqac.dim.bestroommate.model.data.Tazz;
import uqac.dim.bestroommate.model.data.Tazz.TaskState;
import uqac.dim.bestroommate.model.data.Tazz.TaskType;

public class ToDoListFragment extends Fragment {

    private FragmentToDoListBinding _binding;

    private ArrayList<Tazz> notDone;
    public ToDoListFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this._binding = FragmentToDoListBinding.inflate(inflater, container, false);
        this.initialize();
        return this._binding.getRoot();
    }

    private void initialize(){
        this.notDone = DataManager.getInstance().getCollocation().getNotDoneTasks();
        // Define the layout manager for the RecyclerView, it's the disposition of the "card"
        this._binding.toDoListRecyclerView.setLayoutManager(new LinearLayoutManager(this.requireContext()));

        // Update the support action bar
        ((MainActivity) this.requireActivity()).updateSupportActionBar(getString(R.string.todo_fragment_name), false, true);

        // Create the adapter with the list of tasks that are not down
        ToDoTaskAdapter adapter = new ToDoTaskAdapter(
                this.requireContext(),
                this.notDone, R.layout.to_do_list_item);

        adapter.setOnItemSelectedDelegate(position -> {
            showAlertDialogOptions(position);
            return true;
        });

        // Bind the adapter to the RecyclerView
        this._binding.toDoListRecyclerView.setAdapter(adapter);


    }

    private void loadToDoListAdapter(){

    }

    // Method to show a popup menu with the options to edit or delete a task
    private void showAlertDialogOptions(int taskListPosition){
        new AlertDialog.Builder(this.requireContext())
                .setTitle(getString(R.string.ad_title))
                .setMessage(getString(R.string.ad_message))
                .setIcon(R.drawable.chart_leaderboard_icon_24)
                // Button done (right)
                .setPositiveButton(getString(R.string.ad_check), (dialog, which) -> {

                    DataManager.getInstance().checkTask(this.notDone.get(taskListPosition));
                    this.notDone.remove(taskListPosition);
                    this._binding.toDoListRecyclerView.getAdapter().notifyDataSetChanged();
                })
                // Button delete (left)
                .setNegativeButton(getString(R.string.ad_cancel), (dialog, which) -> {
                    dialog.cancel();
                })
                .create()
                .show();
    }
}