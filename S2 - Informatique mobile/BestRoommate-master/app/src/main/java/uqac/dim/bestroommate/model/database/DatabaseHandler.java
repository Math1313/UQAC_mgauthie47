package uqac.dim.bestroommate.model.database;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import uqac.dim.bestroommate.BestRoommate;
import uqac.dim.bestroommate.MainActivity;
import uqac.dim.bestroommate.R;
import uqac.dim.bestroommate.model.DataManager;
import uqac.dim.bestroommate.model.data.Collocation;
import uqac.dim.bestroommate.model.data.Tazz;
import uqac.dim.bestroommate.model.data.User;

public class DatabaseHandler {

    private static DatabaseHandler _instance;
    private FirebaseFirestore database;

    public DatabaseHandler() {
        database = FirebaseFirestore.getInstance();
    }

    public static synchronized DatabaseHandler getInstance() {
        if (_instance == null) {
            _instance = new DatabaseHandler();
        }
        return _instance;
    }

    /*
    * Those two methods are used in the connection fragment to get all the collocations and
    * all the users in each collocation.
    * */
    public void getAllCollocations(final DataManager.DataReadyListener listener) {

        database.collection("Collocations")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<String> dataList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            dataList.add(document.getId());
                        }
                        listener.onDataReady(dataList);
                    }
                });
    }

    public void getAllUserFromCollocName(String collocName, final DataManager.DataReadyListener listener) {

        database.collection("Users")
                .whereEqualTo("collocname", collocName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<User> dataList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            User user = document.toObject(User.class);
                            dataList.add(user);
                        }
                        listener.onDataReady(dataList);
                    }
                });
    }
    public void getCollocFromCollocName(String collocName, final DataManager.DataReadyListener listener) {
        database.collection("Collocations").
                document(collocName)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    Collocation collocation = documentSnapshot.toObject(Collocation.class);
                    Collocation data = null;
                    if (collocation != null) {
                        Log.d("TEST_COLLOCATION", collocation.getName());
                        data = collocation;
                    }
                    listener.onDataReady(data);
                }).addOnFailureListener(e -> {

                });
    }

    public void getUsersFromCollocName(String collocName) {

        database.collection("Users")
                .whereEqualTo("collocname", collocName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            User user = document.toObject(User.class);
                            DataManager.getInstance().getCollocation().addUser(user);

                        }
                    }
                });
    }

    public void getTasksFromCollocName(String collocName, final DataManager.DataReadyListener listener) {

        database.collection("Tasks")
                .whereEqualTo("collocname", collocName)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<Tazz> taskList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Timestamp date = (Timestamp) document.getData().get("deadline");
                            int reward = document.getData().get("reward") != null ? Long.valueOf(document.getData().get("reward").toString()).intValue() : 0;
                            taskList.add(
                                    new Tazz.TaskBuilder(document.getData().get("name").toString())
                                            .setId(document.getId())
                                            .setTaskState(Tazz.getTaskState(document.getData().get("state").toString()))
                                            .setUser(DataManager.getInstance().getCollocation().getUserByUsername(document.getData().get("username").toString()))
                                            .setDeadLine(date.toDate())
                                            .setReward(reward)
                                            .setType(Tazz.getTaskType(document.getData().get("category").toString()))
                                            .setNote(document.getData().get("description").toString())
                                            .build());
                        }
                        listener.onDataReady(taskList);
                    } else {
                        Log.d("TAG", "Error getting documents: ", task.getException());
                    }
                });
    }

    public void addTaskToDatabase(Tazz task) {
        Map<String, Object> data = new HashMap<>();
        data.put("category", task.getType());
        data.put("collocname", task.getCollocName());
        data.put("description", task.getNote());
        data.put("reward", task.getReward());
        data.put("deadline", task.getDeadLineAsTimestamp());
        //data.put("location", task.getLocation());
        data.put("name", task.getTitle());
        data.put("state", task.getTaskState());
        data.put("username", task.getUser() != null ? task.getUser().getUsername() : "");

        database.collection("Tasks")
                .add(data)
                .addOnSuccessListener(documentReference -> {
                    //Add the id of the newly create document to the task
                    //Java pass the reference to the object, so we can add the id after
                    // the document has been created
                    task.setId(documentReference.getId());
                    Toast.makeText(BestRoommate.getAppContext(), R.string.successful_operation, Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
                    Toast.makeText(BestRoommate.getAppContext(), R.string.failed_operation, Toast.LENGTH_SHORT).show();
                });
    }

    public void removeTaskToDatabase(Tazz task) {

        database.collection("Tasks").document(task.getId())
                .delete()
                .addOnCompleteListener(task1 -> {
                    if(task1.isSuccessful())
                    {
                        Toast.makeText(BestRoommate.getAppContext(), R.string.successful_operation, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(BestRoommate.getAppContext(), R.string.failed_operation, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void updateUserToDatabase(User user)
    {
        database.collection("Users")
                .document(user.getUsername())
                .set(user);
    }

    public void updateTaskStatusToDatabase(Tazz task)
    {
        database.collection("Tasks")
                .document(task.getId())
                .update("state", "DONE");
    }
    public void updateTaskToDatabase(Tazz oldTask, Tazz newTask) {
        database.collection("Tasks")
                .document(oldTask.getId())
                .delete()
                .addOnCompleteListener(task -> addTaskToDatabase(newTask));
    }
}
