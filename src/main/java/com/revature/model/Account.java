package com.revature.model;
public class Account {
    private static int id;
    private static  String username; 
    private static String password;
	private static double balance = 0; 
	private static boolean loggedIn = false;
	
	
	public Account() {

	}
	
	public Account(String username,String password) {
	  Account.username = username;
	  Account.password = password;
	}
	
    public int getId() {
      return id;
    }

    public void setId(int id) {
      Account.id = id;
    }

    public boolean isLoggedIn() {
      return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
      Account.loggedIn = loggedIn;
    }

    public void setBalance(double balance) {
      Account.balance = balance;
    }

	public double getBalance() {
		return balance;
	}

	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    Account.password = password;
  }

  public void setUsername(String username) {
	  Account.username = username;
	}
	
	
}
