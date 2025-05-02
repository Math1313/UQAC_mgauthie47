package uqac.dim.bestroommate.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.Calendar;
import java.util.Date;

import uqac.dim.bestroommate.MainActivity;
import uqac.dim.bestroommate.R;
import uqac.dim.bestroommate.databinding.FragmentAddEditTaskBinding;
import uqac.dim.bestroommate.model.DataManager;
import uqac.dim.bestroommate.model.data.Tazz;

public class AddEditTaskFragment extends Fragment {

    private static final String ARG_TASK_ID = "argTaskPos";

    private boolean isInCreateMode = false;

    private FragmentAddEditTaskBinding _binding;

    private int taskPositionInList = -1;

    public AddEditTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this._binding = FragmentAddEditTaskBinding.inflate(inflater, container, false);
        this.initialize();
        return this._binding.getRoot();
    }

    private void initialize(){

        this.requireActivity().findViewById(R.id.bottomNavigationView).setVisibility(View.GONE);

        // Get the fragment title for the support action bar
        String fragmentTitle = getString(R.string.add_fragment_name);
        // Check if the fragment is in create mode or edit mode
        if(this.getArguments() != null){
            this.taskPositionInList = this.getArguments().getInt(ARG_TASK_ID);
            this.isInCreateMode = (this.taskPositionInList == -1);
            if(!this.isInCreateMode) {
                fragmentTitle = getString(R.string.edit_fragment_name);
                this.fillFields();
            }
        }

        // Update the support action bar
        ((MainActivity) this.requireActivity()).updateSupportActionBar(fragmentTitle, true, false);

        // Set the spinner listener to update the reward field depend of the TaskType
        this._binding.typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                changeReward();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Set the calendar view to the current date cause it the user change the actual date it will only update the UI
        this._binding.calendarView2.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            this._binding.calendarView2.setDate(calendar.getTimeInMillis());
        });

        // Set the TaskState spinner adapter from the TaskState enum
        ArrayAdapter<Tazz.TaskState> adapter = new ArrayAdapter<>(this.requireContext(), android.R.layout.simple_spinner_item, Tazz.TaskState.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this._binding.stateSpinner.setAdapter(adapter);

        // Set the TaskType spinner adapter from the TaskType enum
        ArrayAdapter<Tazz.TaskType> adapterType = new ArrayAdapter<>(this.requireContext(), android.R.layout.simple_spinner_item, Tazz.TaskType.values());
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this._binding.typeSpinner.setAdapter(adapterType);

        // Set the save button listener to save the task
        this._binding.saveButton.setOnClickListener(v -> {
            this.saveTask();
        });
    }

    // Function to change the rewardEditText depend of the TaskType
    private void changeReward(){
        Tazz.TaskType type = (Tazz.TaskType) this._binding.typeSpinner.getSelectedItem();
        this._binding.rewardEdittext.setText(String.valueOf(type.point));
    }

    // Function to fill the fields with the task data when in edit mode
    private void fillFields(){
        Tazz task = DataManager.getInstance().getCollocation().getTasks().get(this.taskPositionInList);
        this._binding.titleEditText.setText(task.getTitle());
        this._binding.stateSpinner.setSelection(task.getTaskState().ordinal());
        this._binding.calendarView2.setDate(task.getDeadLineAsMillis());
        this._binding.rewardEdittext.setText(String.valueOf(task.getReward()));
    }

    // Function to get all the fields and save the Task in the database and the DataManager
    private void saveTask(){
        // Retrieve the data from the fields
        String title = this._binding.titleEditText.getText().toString();
        Tazz.TaskState state = (Tazz.TaskState) this._binding.stateSpinner.getSelectedItem();
        Tazz.TaskType type = (Tazz.TaskType) this._binding.typeSpinner.getSelectedItem();
        int reward = Integer.parseInt(this._binding.rewardEdittext.getText().toString());
        long date = this._binding.calendarView2.getDate();
        String note = this._binding.noteEditText.getText().toString();

        // Create a new task object (event if it's for update, see the comments on the updateTask function)
        Tazz task;
        task = new Tazz.TaskBuilder(title)
                .setTaskState(state)
                .setType(type)
                .setNote(note)
                .setReward(reward)
                .setDeadline(date)
                .setUser(DataManager.getInstance().getActualUser())
                .build();

        // If Create mode, create a new task and add it
        if(this.isInCreateMode){
            Log.d("TASKSS", "ADD");
            DataManager.getInstance().addTask(task);
        }
        // Else edit/update it
        else{
            DataManager.getInstance().updateTask(DataManager.getInstance().getCollocation().getTasks()
                    .get(this.taskPositionInList), task);
        }

        // Go back to the previous fragment
        this.requireActivity().getOnBackPressedDispatcher().onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this._binding = null;
        this.requireActivity().findViewById(R.id.bottomNavigationView).setVisibility(View.VISIBLE);
    }
}