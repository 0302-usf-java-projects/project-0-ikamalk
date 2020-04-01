package com.revature.controller;
import com.revature.model.*;
import java.util.*;
import com.revature.exception.*;
import com.revature.service.BankServices;


public class MenuPanel {
	Account user = new Account();  
	BankServices bs = new BankServices(); 
	Scanner sc = new Scanner(System.in); 
	
	
	public void runMenu(){
	     boolean runMainMenu = false;

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
		
		if(!user.isActivated()) {
		  System.out.println("Your bank account is still pending, please wait to be activated by one of ours employee, thank you !");
		  System.out.println("insert 1 to go to the main menu");
		  if(sc.nextLine().equals("1")) {
		    logOut(); 
            runMainMenu = false;
		  }
		} else {
          runMainMenu = true;
		}
	        while (runMainMenu && user.getRole().trim().equals("employee")) {
	          System.out.println("Welcome to the bank portal "+user.getUsername().trim()+"! ");

	            String input = runMainMenuEmployee(); 
	            switch (input) {
	            case "1": 
	                pendingAccount();
	                break;
	            case "2":
	                viewTransactionsUser();
	                 break;
	            case "3":
	              logOut(); 
	              runMainMenu = false; 
	              break;
	            default:
	                System.out.println("I'm sorry, I didn't understand that. Can you try again?");
	            }
	        }
	        
	        
		while (runMainMenu && user.getRole().trim().equals("customer")) {
          System.out.println("Welcome to the main menu "+user.getUsername().trim()+"! Please select from the following options.");
			String input = runMainMenu(); 
			switch (input) {
			case "1": 
				makeDeposit();
				break;
			case "2":
	            makeWithdrawal();
	                break;
			case "3":
				makeTransfer();
				break; 
			case "4":
				viewMyBalance();
				break;
			case "5":
              viewMyTransactions();
              break;
			case "6":
              logOut(); 
              runMainMenu = false; 
              break;
			default:
				System.out.println("I'm sorry, I didn't understand that. Can you try again?");
			}
		}
		runMenu();
	}
	
	public void logOut() {
		System.out.println("Thank you, you will be returned to the welcome menu.");
        System.out.println("------------------------------------------------------------------------");
		user.setLoggedIn(false);
	}
	
	public void runAccountCreation() {
		bs.createAccount(); 
        System.out.println("------------------------------------------------------------------------");
	}
	
	public void makeTransfer() {

      System.out.println("Please enter name of the user who you want to transfer the money");
      String username = sc.nextLine();
      System.out.println("Please enter the amount");
      double amount = Double.parseDouble(sc.nextLine());
      if(bs.attemptTranfer(username, amount)) {
        System.out.println(amount+" was transfered to "+username+" successfuly");
        System.out.println("your balance is now "+user.getBalance());
      } else {
        System.out.println("Error, the username doesn't exist in your database, try another username");
      }
	}
	
	public void runLogIn(){
		System.out.println("Please enter your username");
		String username = sc.nextLine(); 
		System.out.println("Please enter your password");
		String password = sc.nextLine();
		if (bs.attemptLogIn(new Account(username,password))) {
			System.out.println("You are now logged in, taking you to the main menu."); 
	        System.out.println("-----------------------------------------------------------------------");
		} else {
			System.out.println("I couldn't log you in with that username and password. Please try again.");
	        System.out.println("-----------------------------------------------------------------------");

		}
	}
	
	public String runWelcomeMenu() {
		System.out.println("Welcome to the bank, please choose from one of the following options:");
		System.out.println("1) Create a new account");
		System.out.println("2) Log in to an existing account");
		System.out.println("3) Quit"); 
        System.out.println("-----------------------------------------------------------------------");

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
        System.out.println("-----------------------------------------------------------------------");
		double input = Double.parseDouble(sc.nextLine());
		double newBalance =0;
    try {
      newBalance = bs.withdrawMoney(input);
    } catch (BalanceTooLowException e) {
      System.out.println("You can withdraw more than your balance, your balance is:"+user.getBalance()+" please try again");
      makeWithdrawal();
    } finally {
      if (newBalance >=0) {
        System.out.println("Your balance is now "+user.getBalance());
        System.out.println("-----------------------------------------------------------------------");

      } else {
        System.out.println("error");
      }
    }
		

	}
	
	public void makeDeposit() {
		System.out.println("Please enter the amount of money you wish to deposit.");
        System.out.println("-----------------------------------------------------------------------");
		double input = Double.parseDouble(sc.nextLine());
		if (input > 0) {
		  double newBalance = bs.depositMoney(input);
		  if(newBalance!=-1) {
		    System.out.println("Your balance is now "+newBalance);
	        System.out.println("-----------------------------------------------------------------------");
		  }
		} else {
			System.out.println("You must deposit more money to update your balance. Please try again.");
	        System.out.println("-----------------------------------------------------------------------");

		}
	}
	
	public void viewMyTransactions() {
	  List<Transaction> transactions = bs.getTransaction();
	  for(Transaction transaction: transactions) {
	    System.out.println("-----------------------------------------------------------------");
	    System.out.println("| "+transaction.operation+ " | " +transaction.amount+" | "+transaction.time_transaction+" |");

	  }
	     System.out.println("---------------------------------------------------------------");

	}
	
	
	public void viewTransactionsUser() {
	  System.out.println("Please enter username of the customer to view his transactions list");
	  
	  String username = sc.nextLine();
	  
	  List<Transaction> transactions = bs.getTransactionsUser(username);
      for(Transaction transaction: transactions) {
        System.out.println("-----------------------------------------------------------------");
        System.out.println("| "+transaction.operation+ " | " +transaction.amount+" | "+transaction.time_transaction+" |");

      }
         System.out.println("---------------------------------------------------------------");
	}
	
	public void viewMyBalance() {
      System.out.println("-----------------------------------------------------------------------");
      System.out.println("-------------------My balance:"+user.getBalance()+"--------------------");
      System.out.println("-----------------------------------------------------------------------");

	}
	
	public void pendingAccount() {
	   List<Account> pendingAccounts = bs.getPendingAccounts();
	   System.out.println("----------------------List of pending accounts------------------------------");
	   if(pendingAccounts.size() != 0) {
	      for(Account pendingAccount: pendingAccounts) {
            System.out.println("-----------------------------------------------------------------------");
            System.out.println(pendingAccount.getUsername());

          }
             System.out.println("-----------------------------------------------------------------------");
             System.out.println("Please enter the username of a customer to activated the compte");
             String getUsername = sc.nextLine();
             if(bs.activateAccount(getUsername)) {
               System.out.println(getUsername+" account has been activated successfully ");
               System.out.println("Press enter to go back");
               sc.nextLine();
             } else {
               System.out.println("there is a problem with db :/");

             }
             
	   } else {
	     System.out.println("------------------there is no pending account !---------------------------");
	   }
	}
	
	
	public String runMainMenuEmployee() {
	  System.out.println("1 accept pending account");
      System.out.println("2 view transactions");
      System.out.println("3 Logout to the welcome menu");  
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
      default: 
          System.out.println("I'm sorry, I didn't understand that option. Returning to the menu."); 
          System.out.println("-----------------------------------------------------------------------");

       
      }
      return result;
	}

	
	public String runMainMenu() {
		System.out.println("1)  Deposit money");
		System.out.println("2)  Withdraw money");
		System.out.println("3)  Transfer money to a different account");
	    System.out.println("4)  Check my balance");
		System.out.println("5)  Check my transactions");
		System.out.println("6)  Logout to the welcome menu");  
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
		case "5": 
          result = "5";
          break;
		case "6": 
          result = "6";
          break;
		default: 
			System.out.println("I'm sorry, I didn't understand that option. Returning to the menu."); 
            System.out.println("-----------------------------------------------------------------------");

		 
		}
		return result;
	}
}

