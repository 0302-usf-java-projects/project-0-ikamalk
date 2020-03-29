package com.revature.service;

import com.revature.exception.AccountNameAlreadyTakenException;
import com.revature.exception.BalanceTooLowException;
import com.revature.exception.PasswordTooShortException;
import com.revature.model.Account;
import java.util.Scanner;
import com.revature.repository.*;

public class BankServices {
	private static BankDAO accountManager = new BankDAO(); 
	private static Scanner scanner = new Scanner(System.in); 
	private static Account user = new Account();
	public void createAccount() {
		
		int numTries = 0; 
		while (numTries < 3) {
			System.out.println("Please enter your desired username");
			String username = scanner.nextLine(); 
			System.out.println("Please enter your desired password"); 
			String password = scanner.nextLine(); 
			try {
				Account newAccount = new Account(username, password);
				if(password.length() < 8) {
				  throw new PasswordTooShortException();
				}
				if (!accountManager.addAccount(newAccount)) {
				  throw new AccountNameAlreadyTakenException();
				}
				System.out.println("Thank you, your account has been created and you may now log in."); 
				break;
			} catch (PasswordTooShortException e) {
				System.out.println("Passwords must be at least 8 characters long, please try again."); 
			} catch (AccountNameAlreadyTakenException e) {
				System.out.println("That username is already taken. If you already have an account, please log in instead."); 
			}
			numTries += 1; 
		
		}
		if (numTries == 3) {
			System.out.println("It seems like you were having some issues. Returning to the menu, please try again.");
		}
		
	}
	
  
	
	public boolean attemptLogIn(Account account) {
	return	accountManager.login(account);
	}
	
	
	public double depositMoney(double input) {
     	return  accountManager.depositMoney(input);
	}
	

    public double withdrawMoney(double input) throws BalanceTooLowException {
      if (input > user.getBalance()) {
          throw new BalanceTooLowException();
      } else {
        return  accountManager.withdrawalMoney(input);
      }
  }
    
    public boolean attemptTranfer(String username, double amount) {
      return  accountManager.transferMoney(username, amount);
    }
	
}
