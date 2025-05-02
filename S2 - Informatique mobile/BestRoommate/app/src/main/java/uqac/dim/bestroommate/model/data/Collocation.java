package uqac.dim.bestroommate.model.data;

import java.util.ArrayList;

public class Collocation {
    private String name;
    private String address;
    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<Tazz> tasks = new ArrayList<Tazz>();

    //Require empty constructor for Database request conversion
    public Collocation(){}

    public Collocation(String name, String address){
        this.name = name;
        this.address = address;
    }

    public void addUser(User user){
        this.users.add(user);
    }

    public void removeUser(User user){
        this.users.remove(user);
    }

    public void addTask(Tazz task){
        this.tasks.add(task);
    }

    public void removeTask(Tazz task){
        this.tasks.remove(task);
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }


    public String getAddress(){
        return this.address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public ArrayList<Tazz> getTasks(){
        return this.tasks;
    }
    public void setTasks(ArrayList<Tazz> tasks){
        this.tasks = tasks;
    }

    public ArrayList<User> getUsers() {
        return this.users;
    }
    public void setUsers(ArrayList<User> users){
        this.users = users;
    }

    public User getUserByUsername(String username)
    {
        for(User u: users)
        {
            if(u.getUsername().equals(username)){
                return u;
            }
        }
        return new User();
    }

    public ArrayList<Tazz> getNotDoneTasks(){
        ArrayList<Tazz> notDoneTasks = new ArrayList<Tazz>();
        for(Tazz t: tasks){
            if(t.getTaskState() == Tazz.TaskState.NOT_DONE){
                notDoneTasks.add(t);
            }
        }
        return notDoneTasks;
    }

}
