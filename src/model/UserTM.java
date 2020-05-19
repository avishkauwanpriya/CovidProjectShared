package model;

import javafx.scene.control.Button;

public class UserTM {
    private String userName;
    private String name;
    private String role;
    private Button remove;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Button getRemove() {
        return remove;
    }

    public void setRemove(Button remove) {
        this.remove = remove;
    }



    @Override
    public String toString() {
        return "UserTM{" +
                "userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", remove=" + remove +
                '}';
    }



    public UserTM() {
    }



    public UserTM(String userName, String name, String role, Button remove) {
        this.userName = userName;
        this.name = name;
        this.role = role;
        this.remove = remove;
    }







}
