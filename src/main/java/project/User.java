package project;

public class User {
    private int id;
    private String name;
    private String surname;
    private String address;
    private String username;
    private String password;

    public User(int id, String name, String surname, String address, String username, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStringValueOfID() {
        String id = String.valueOf(this.id);
        return id;
    }

}







