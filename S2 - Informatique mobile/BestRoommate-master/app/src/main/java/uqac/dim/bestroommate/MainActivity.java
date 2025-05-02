package uqac.dim.bestroommate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.Objects;

import uqac.dim.bestroommate.databinding.ActivityMainBinding;
import uqac.dim.bestroommate.fragment.AllTaskListFragmentDirections;
import uqac.dim.bestroommate.fragment.ToDoListFragmentDirections;
import uqac.dim.bestroommate.model.database.DatabaseHandler;

public class MainActivity extends AppCompatActivity {

    private NavController _navController;

    private ActivityMainBinding _binding;

    private boolean isSupportActionBarMenuVisbile = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this._binding = ActivityMainBinding.inflate(this.getLayoutInflater());
        setContentView(this._binding.getRoot());
        this.initialize_navigation();
        this.initialize_ui();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return this._navController.navigateUp() || super.onSupportNavigateUp();
    }

    // Method that will initialize the navigation (the bottom navigation view buttons)
    private void initialize_navigation(){
        NavHostFragment frag = (NavHostFragment) this.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if(frag != null){
            this._navController = frag.getNavController();
            NavigationUI.setupWithNavController(this._binding.bottomNavigationView, this._navController);
        }
    }

    // Method that will initialize the User Interface
    private void initialize_ui(){

        Toolbar toolbar = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        this._navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.welcomeFragment) {
                window.setStatusBarColor(getColor(R.color.md_theme_light_primary));
                this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.md_theme_light_primary)));
                this._binding.bottomNavigationView.setBackgroundColor(getColor(R.color.md_theme_light_primary));
            } else if (destination.getId() == R.id.allTaskListFragment) {
                window.setStatusBarColor(getColor(R.color.md_theme_light_tertiary));
                this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.md_theme_light_tertiary)));
                this._binding.bottomNavigationView.setBackgroundColor(getColor(R.color.md_theme_light_tertiary));
            } else if (destination.getId() == R.id.toDoListFragment) {
                window.setStatusBarColor(getColor(R.color.md_theme_light_secondary));
                this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.md_theme_light_secondary)));
                this._binding.bottomNavigationView.setBackgroundColor(getColor(R.color.md_theme_light_secondary));
            }else if (destination.getId() == R.id.addEditTaskFragment) {
                window.setStatusBarColor(getColor(R.color.other_theme));
                this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.other_theme)));
                this._binding.bottomNavigationView.setBackgroundColor(getColor(R.color.other_theme));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        for(int i =0; i< menu.size(); i++){
            menu.getItem(i).setVisible(this.isSupportActionBarMenuVisbile);
        }
        return true;
    }

    /**
     * Method that will update the support action bar
     * @param title String that will be the title of the action bar
     * @param isReturnButtonVisible Boolean that will determine if the return button is visible
     */
    public void updateSupportActionBar(String title, boolean isReturnButtonVisible, boolean _isSupportActionBarMenuVisbile){
        Objects.requireNonNull(this.getSupportActionBar()).setTitle(title);
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayHomeAsUpEnabled(isReturnButtonVisible);
        // Check the actual state of the menu and change it if needed
        if(this.isSupportActionBarMenuVisbile != _isSupportActionBarMenuVisbile){
            this.isSupportActionBarMenuVisbile = _isSupportActionBarMenuVisbile;
            // Call onCreateOptionMenu to update the menu
            this.invalidateOptionsMenu();
        }
    }

    /**
     * Method that will change the visibility of the bottom navigation view
     *
     * @param isVisible Boolean that will determine if the bottom navigation view is visible
     */
    public void changeBottomNavigationVisibility(boolean isVisible){
        this._binding.bottomNavigationView.setVisibility((isVisible) ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.addMenuItem){
            if(this._navController.getCurrentDestination().getId() == R.id.allTaskListFragment){
                NavDirections action = AllTaskListFragmentDirections.actionAllTaskListFragmentToAddEditTaskFragment();
                this._navController.navigate(action);
            }
            else if(this._navController.getCurrentDestination().getId() == R.id.toDoListFragment){
                NavDirections action = ToDoListFragmentDirections.actionToDoListFragmentToAddEditTaskFragment();
                this._navController.navigate(action);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}

