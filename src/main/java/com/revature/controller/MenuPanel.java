package com.revature.controller;
import com.revature.model.*;

import java.util.*;
import com.revature.exception.*;
import com.revature.service.BankServices;


public class MenuPanel {
	User user = new User();  
	BankServices bs = new BankServices(); 
	Scanner sc = new Scanner(System.in); 
	
	
	public void runMenu() throws PasswordTooShortException, AccountNameAlreadyTakenException {
		while (!user.isLoggedIn()) {
			String input = runWelcomeMenu(); 
			switch (input) {
			case "1" : 
				runAccountCreation();
				break;
			case "2": 
				runLogIn();
				break; 
			case "3":
				System.out.println("Thanks, please come again."); 
				System.exit(0);
			}
		
		
		}	
		boolean runMainMenu = true;
	      System.out.println("Welcome to the main menu "+user.getUsername().trim()+"! Please select from the following options.");
		while (runMainMenu) {
			String input = runMainMenu(); 
			switch (input) {
			case "1": 
				makeDeposit();
				break;
			case "3":
			//	printAccountBalance();
				break; 
			case "4":
				logOut(); 
				runMainMenu = false; 
				break;
			case "2":
            makeWithdrawal();
				break; 
			default:
				System.out.println("I'm sorry, I didn't understand that. Can you try again?");
			}
		}
		
		runMenu();
		
	}
	
	public void logOut() {
		System.out.println("Thank you, you will be returned to the welcome menu.");
		System.out.println("****************************************************************************************");
		user.setLoggedIn(false);
	}
	
	public void runAccountCreation() {
		bs.createAccount(); 
		System.out.println("****************************************************************************************");
	}
	
	public void runLogIn() throws PasswordTooShortException, AccountNameAlreadyTakenException {
		System.out.println("Please enter your username");
		String username = sc.nextLine(); 
		System.out.println("Please enter your password");
		String password = sc.nextLine();
		if (bs.attemptLogIn(new Account(username,password))) {
			System.out.println("You are now logged in, taking you to the main menu."); 
			System.out.println("****************************************************************************************");
		} else {
			System.out.println("I couldn't log you in with that username and password. Please try again.");
			System.out.println("****************************************************************************************");

		}
	}
	
	public String runWelcomeMenu() {
		System.out.println("Welcome to the bank, please choose from one of the following options:");
		System.out.println("1: Create a new account");
		System.out.println("2: Log in to an existing account");
		System.out.println("3: Quit"); 

		String input = sc.nextLine(); 
		String result; 
		switch (input) {
		case "1" : result = "1";
				break; 
		case "2" : result = "2"; 
				break;
		case "3" : result = "3"; 
				break; 
		default:
			System.out.println("I'm sorry, but that is not an option. Please try again."); 
				result = "0"; 
		}
		return result; 
		
	}
	
	public void makeWithdrawal()  {
	     System.out.println("*****Your Balance is:"+user.getBalance()+"*****");
		System.out.println("Please enter the amount that you would like to withdraw.");
		double input = Double.parseDouble(sc.nextLine());
		double newBalance =0;
    try {
      newBalance = bs.withdrawMoney(input);
    } catch (BalanceTooLowException e) {
      System.out.println("You can withdraw more than your balance, your balance is:"+user.getBalance()+" please try again");
      makeWithdrawal();
    } finally {
      if (newBalance >=0) {
        System.out.println("****************************************************************************************");
        System.out.println("Your balance is now "+user.getBalance());
        System.out.println("****************************************************************************************");

      } else {
        System.out.println("error");
      }
    }
		

	}
	
	public void makeDeposit() {
		System.out.println("Please enter the amount of money you wish to deposit.");
		double input = Double.parseDouble(sc.nextLine());
		if (input > 0) {
		  double newBalance = bs.depositMoney(input);
		  if(newBalance!=-1) {
		    System.out.println("Your balance is now "+newBalance);
		  }
	
			System.out.println("You may now make another transaction");
			System.out.println("****************************************************************************************");

		} else {
			System.out.println("You must deposit more money to update your balance. Please try again.");
			System.out.println("****************************************************************************************");

		}
	}
	

	
	public String runMainMenu() {
		System.out.println("1 Make a deposit");
		System.out.println("2 Make a withdrawal");
		System.out.println("3 View your account balance");
		System.out.println("4 Logout to the welcome menu");  
		String input = sc.nextLine();
		String result = "0";
		switch (input) {
		case "1" : 
			result ="1";
			break;
		case "2": 
			result = "2";
			break;
		case "3":
			result = "3"; 
			break; 
		case "4": 
			result = "4";
			break;
		default: 
			System.out.println("I'm sorry, I didn't understand that option. Returning to the menu."); 
			System.out.println("****************************************************************************************");

		 
		}
		return result;
	}
}

