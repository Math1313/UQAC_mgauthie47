package uqac.dim.bestroommate.model.data;

import androidx.annotation.NonNull;

public class User {

    private String username;
    private String name;
    private String surname;
    private String email;
    private Integer points;


    //Require empty constructor for Database request conversion
    public User(){}
    public User(String username, String name, String surname, String email, int points){
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.points = points;
    }

    public String getUsername(){
        return username;
    }

    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }

    public String getEmail(){
        return email;
    }

    public Integer getPoints(){
        return points;
    }

    public void addPoints(int points){
        this.points += points;
    }

    @NonNull
    @Override
    public String toString(){
        return this.username + " " + this.name + " " + this.surname;
    }


}
