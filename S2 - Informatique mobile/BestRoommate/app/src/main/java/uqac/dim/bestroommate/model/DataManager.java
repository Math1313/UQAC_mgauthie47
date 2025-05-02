package uqac.dim.bestroommate.model;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import uqac.dim.bestroommate.BestRoommate;
import uqac.dim.bestroommate.MainActivity;
import uqac.dim.bestroommate.R;
import uqac.dim.bestroommate.model.data.Collocation;
import uqac.dim.bestroommate.model.data.Tazz;
import uqac.dim.bestroommate.model.data.User;
import uqac.dim.bestroommate.model.database.DatabaseHandler;

public final class DataManager {
    private static DataManager _instance;
    private Collocation collocation;
    private User actualuser;

    /*
    * Interface to listen to the data
    * onDataReady method is executed when the data is completely fetch
    * T type to allow every type of data to work with the listener
    * */
    public interface DataReadyListener<T>
    {
        void onDataReady(T data);
    }

    private DataManager(){
        this.init();

    }

    /**
     * Get the instance of the DataManager, ThreadSafety
     * @return DataManager
     */
    public static synchronized DataManager getInstance(){
        if(_instance == null){
            _instance = new DataManager();
        }
        return _instance;
    }

    /**
     * Initialize all the data
     */
    private void init(){
    }

    /**
     * Check a task as done, the user who check the Task will get the reward
     * @param position int position in the list
     */
    public void checkTask(Tazz task){
        task.setTaskState(Tazz.TaskState.DONE);
        task.setUser(this.getActualUser());
        this.getActualUser().addPoints(task.getReward());
        this.updateUser(this.getActualUser());
        DatabaseHandler.getInstance().updateTaskStatusToDatabase(task);
        Toast.makeText(BestRoommate.getAppContext(), R.string.successful_check, Toast.LENGTH_SHORT).show();
    }

    /**
     * Get the actual user object who use the application
     * @return User
     */
    public User getActualUser(){
        return actualuser;
    }

    public void setActualUser(User user) {
        this.actualuser = user;
    }

    public void setActualCollocation(Collocation collocation)
    {
        this.collocation = collocation;
    }

    /**
     * Add a task to the collocation
     * @param task Task object to add
     */
    public void addTask(Tazz task)
    {
        task.setCollocName(collocation.getName());
        collocation.addTask(task);
        DatabaseHandler.getInstance().addTaskToDatabase(task);
    }

    /**
     * Delete a task from the collocation
     * @param task Task object to delete
     */
    public void deleteTask(Tazz task)
    {
        DatabaseHandler.getInstance().removeTaskToDatabase(task);
        collocation.removeTask(task);
    }

    /**
     * Delete a task from the collocation
     * Due to the complexity of updating a task, we delete the old one and add the new one
     * It will not change anything and not decrease the speed cause it's a documents based database.
     * @param oldTask Task object to delete
     * @param newTask Task object to add
     */
    public void updateTask(Tazz oldTask, Tazz newTask)
    {
        newTask.setCollocName(collocation.getName());
        DatabaseHandler.getInstance().updateTaskToDatabase(oldTask, newTask);
        collocation.removeTask(oldTask);
        collocation.addTask(newTask);
    }

    /**
     * Update the User
     * @param user User object to update
     */
    public void updateUser(User user)
    {
        DatabaseHandler.getInstance().updateUserToDatabase(user);
    }

    /**
     * Get the actual collocation object
     * @return Collocation
     */
    public Collocation getCollocation() {
        return collocation;
    }

}
