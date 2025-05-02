package uqac.dim.bestroommate.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import uqac.dim.bestroommate.MainActivity;
import uqac.dim.bestroommate.R;
import uqac.dim.bestroommate.databinding.FragmentConnectBinding;
import uqac.dim.bestroommate.model.DataManager;
import uqac.dim.bestroommate.model.data.Collocation;
import uqac.dim.bestroommate.model.data.Tazz;
import uqac.dim.bestroommate.model.data.User;
import uqac.dim.bestroommate.model.database.DatabaseHandler;

public class ConnectFragment extends Fragment {

    private FragmentConnectBinding _binding;

    public ConnectFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this._binding = FragmentConnectBinding.inflate(inflater, container, false);

        this.initialize();
        return this._binding.getRoot();
    }

    private void initialize() {
        ((MainActivity)this.requireActivity()).changeBottomNavigationVisibility(false);

        // Update the support action bar
        ((MainActivity) this.requireActivity()).updateSupportActionBar(getString(R.string.app_name), false, false);

        // Set the adapter for the spinner
        // Wait until data is fetch
                DatabaseHandler.getInstance().getAllCollocations(new DataManager.DataReadyListener<ArrayList<String>>(){
                    @Override
                    public void onDataReady(ArrayList<String> dataList) {

                        ArrayAdapter<String> adapterCollocation = new ArrayAdapter<>(
                                requireContext(),
                                android.R.layout.simple_spinner_item,
                                dataList);

                        adapterCollocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                        _binding.spinnerCollocation.setAdapter(adapterCollocation);

                        // Add listener to the spinnerCollocation to update the user spinner
                        _binding.spinnerCollocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                onSpinnerCollocationChange();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                            }
                        });

                        _binding.buttonConnect.setOnClickListener((v) -> onButtonConnect());

                    }
                });


    }

    // Update the user spinner
    private void onSpinnerCollocationChange() {
        DatabaseHandler.getInstance().getAllUserFromCollocName(_binding.spinnerCollocation.getSelectedItem().toString(), new DataManager.DataReadyListener<ArrayList<User>>() {
            @Override
            public void onDataReady(ArrayList<User> data) {
                ArrayAdapter<User> adapterUser = new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        data);
                adapterUser.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                _binding.spinnerUser.setAdapter(adapterUser);
            }
        });
    }

    // Connect the user to the application and change the actual user object in DataManager
    private void onButtonConnect() {
        String collocName = _binding.spinnerCollocation.getSelectedItem().toString();
        DataManager.getInstance().setActualUser((User) this._binding.spinnerUser.getSelectedItem());
        DatabaseHandler.getInstance().getCollocFromCollocName(collocName, new DataManager.DataReadyListener<Collocation>() {
            @Override
            public void onDataReady(Collocation data) {
                DataManager.getInstance().setActualCollocation(data);
                Adapter userAdapter = _binding.spinnerUser.getAdapter();
                Log.d("COLLOCATION", DataManager.getInstance().getCollocation().toString());
                for (int i = 0; i < userAdapter.getCount(); i++)
                {
                    User user = (User) userAdapter.getItem(i);
                    DataManager.getInstance().getCollocation().addUser(user);
                }
                DatabaseHandler.getInstance().getTasksFromCollocName(collocName, new DataManager.DataReadyListener<ArrayList<Tazz>>() {
                    @Override
                    public void onDataReady(ArrayList<Tazz> data) {
                        DataManager.getInstance().getCollocation().setTasks(data);
                        NavHostFragment.findNavController(requireParentFragment()).navigate(ConnectFragmentDirections.actionConnectFragmentToWelcomeFragment());
                    }
                });
            }
        });
    }
}