package com.example.Bytez_frontend;

public class User
{
    private int id;
    private String username;
    private String email;
//    private String fName;
//    private String lName;

    public User(int id, String username, String email)
    {
        this.id = id;
        this.username = username;
        this.email = email;
//        this.fName = fName;
//        this.lName = lName;
    }

    public int getId()
    {
        return id;
    }

    public String getUsername()
    {
        return username;
    }

    public String getEmail()
    {
        return email;
    }

//    public String getfName() { return fName; }
//
//    public String getlName() { return lName; }
}
