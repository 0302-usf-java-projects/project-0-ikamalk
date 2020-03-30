package com.revature.repository;

import java.util.List;
import com.revature.model.Account;

public interface InterfaceDAO <T> {
	boolean addAccount(Account account); 
	//void deleteAccount(Account account); 
	//void updateAccount(Account account); 
	boolean login(Account account);
	double depositMoney(Double amount);
	double withdrawalMoney(Double amount);
	boolean checkUsername(String username);
	boolean transferMoney(String username, double amount);
	List getMyTransations();
}
