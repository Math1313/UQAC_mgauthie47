package uqac.dim.bestroommate.model.data;


import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import uqac.dim.bestroommate.BestRoommate;
import uqac.dim.bestroommate.MainActivity;
import uqac.dim.bestroommate.R;

public class Tazz {

    // Represents the state of the task
    public enum TaskState {
        DONE(R.string.task_done),
        NOT_DONE(R.string.task_not_done),
        IN_PROGRESS(R.string.task_in_progress),
        UNKNOWN(R.string.task_state_unknown);

        public final int stringId;

        TaskState(int stringId){
            this.stringId = stringId;
        }

        @NonNull
        @Override
        public String toString(){
            return BestRoommate.getAppContext().getString(this.stringId);
        }
    }

    // Represents the type of the task
    public enum TaskType {
        SHOPPING(10, R.drawable.ic_wallet, R.string.task_type_shopping),
        CLEANING(15, R.drawable.ic_clean, R.string.task_type_cleaning),
        COOKING(5, R.drawable.ic_food, R.string.task_type_cooking),
        DELIVERY(10, R.drawable.ic_delivery, R.string.task_type_delivery),
        LAUNDRY(5, R.drawable.ic_laundry, R.string.task_type_laundry),
        DISHES(5, R.drawable.ic_dishes, R.string.task_type_dishes),
        TRASH(5, R.drawable.ic_trash, R.string.task_type_trash),
        OTHER(0, R.drawable.ic_type, R.string.task_type_other),
        UNKNOWN_TASK(0, R.drawable.list_icon_24, R.string.task_type_unknown);

        public final int point;
        public final int ressource;
        public final int stringId;

        TaskType(int point, int ressource, int stringId) {
            this.point = point;
            this.ressource = ressource;
            this.stringId = stringId;
        }

        @NonNull
        @Override
        public String toString(){
            return BestRoommate.getAppContext().getString(this.stringId);
        }
    }

    private String id = "";
    private String title = "";

    private TaskType type;
    private User user;
    private TaskState taskState;
    private int reward;
    private String note;
    private long deadline;
    private String collocName;
    // The constructor is private to prefer the use of the builder pattern
    private Tazz(TaskBuilder builder){
        this.id = builder.id;
        this.title = builder.title;
        this.type = builder.type;
        this.user = builder.user;
        this.taskState = builder.taskState;
        this.reward = builder.reward;
        this.note = builder.note;
        this.deadline = builder.deadline;
    }

    public String getTitle(){
        return this.title;
    }

    public String getId(){ return this.id;}

    public void setId(String id) { this.id = id; }

    public void setTitle(String title){
        this.title = title;
    }

    public TaskType getType(){
        return this.type;
    }

    public void setType(TaskType type){
        this.type = type;
    }

    public User getUser() throws NullPointerException{
        return this.user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public TaskState getTaskState(){
        return this.taskState;
    }

    public void setTaskState(TaskState taskState){
        this.taskState = taskState;
    }

    public long getDeadLineAsMillis(){
        return this.deadline;
    }

    public Timestamp getDeadLineAsTimestamp(){
        return new Timestamp(new Date(this.deadline));
    }
    public void setDeadLine(Date _deadline){
        this.deadline = _deadline.getTime();
    }

    public void setDeadLine(long _deadline){
        this.deadline = _deadline;
    }


    public String getNote(){
        return this.note;
    }

    public void setNote(String note){
        this.note = note;
    }

    public int getReward(){
        return this.reward;
    }

    public void setReward(int reward){
        this.reward = reward;
    }

    public int getImageRessource(){
        return this.type.ressource;
    }

    public String getCollocName() {
        return collocName;
    }

    public void setCollocName(String collocName) {
        this.collocName = collocName;
    }

    public static class TaskBuilder {

        private  String id;
        private final String title;
        private String location = "";
        private TaskType type = TaskType.UNKNOWN_TASK;
        private User user = null;
        private TaskState taskState = TaskState.UNKNOWN;
        private int reward = type.point;
        private String note = "";
        private long deadline = new Date().getTime();

        public TaskBuilder(String title){
            this.title = title;
        }

        public TaskBuilder setId(String id){
            this.id = id;
            return this;
        }
        public TaskBuilder setUser(User user){
            this.user = user;
            return this;
        }


        public TaskBuilder setReward(int reward){
            this.reward = reward;
            return this;
        }

        public TaskBuilder setNote(String note){
            this.note = note;
            return this;
        }

        public TaskBuilder setDeadline(long _deadline){
            this.deadline = _deadline;
            return this;
        }

        public TaskBuilder setDeadLine(LocalDate _deadline){
            this.deadline = Date.from(_deadline.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime();
            return this;
        }

        public TaskBuilder setDeadLine(Date _deadLine){
            this.deadline = _deadLine.getTime();
            return this;
        }
        public TaskBuilder setLocation(String _location){
            this.location = _location;
            return this;
        }

        public TaskBuilder setType(TaskType _type){
            this.type = _type;
            this.reward = _type.point;
            return this;
        }
        public TaskBuilder setTaskState(TaskState taskState){
            this.taskState = taskState;
            return this;
        }
        public Tazz build(){
            return new Tazz(this);
        }
    }

    public static TaskState getTaskState(String state) {

        switch (state) {
            case "DONE":
                return TaskState.DONE;
            case "NOT_DONE":
                return TaskState.NOT_DONE;
            case "IN_PROGRESS":
                return TaskState.IN_PROGRESS;
            default:
                return TaskState.UNKNOWN;
        }
    }

    public static TaskType getTaskType(String categorie) {

        switch (categorie) {
            case "OTHER":
                return TaskType.OTHER;
            case "SHOPPING":
                return TaskType.SHOPPING;
            case "CLEANING":
                return TaskType.CLEANING;
            case "COOKING":
                return TaskType.COOKING;
            case "DELIVERY":
                return TaskType.DELIVERY;
            case "LAUNDRY":
                return TaskType.LAUNDRY;
            case "DISHES":
                return TaskType.DISHES;
            case "TRASH":
                return TaskType.TRASH;
            default:
                return TaskType.UNKNOWN_TASK;

        }
    }

}
