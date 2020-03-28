package com.revature.model;

public class User {
    private static int id; 
    private static String username;
    private static double balance;
    private static boolean loggedIn = false;
    
    public User(int id,String username, double balance) {
      super();
      User.id = id;
      User.username = username;
      User.balance = balance;
    }
    
    public User() {
      
    }

    public String getUsername() {
      return username;
    }

    public void setId(int id) {
      User.id = id;
    }

    public void setUsername(String username) {
      User.username = username;
    }

    public void setBalance(double balance) {
      User.balance = balance;
    }

    public void setLoggedIn(boolean loggedIn) {
      User.loggedIn = loggedIn;
    }

    public double getBalance() {
      return balance;
    }

    public boolean isLoggedIn() {
      return loggedIn;
    }

    public int getId() {
      return id;
    }
    
	

}
