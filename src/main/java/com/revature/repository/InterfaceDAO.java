package com.revature.repository;

import java.util.List;
import com.revature.model.Account;
import com.revature.model.Transaction;

public interface InterfaceDAO <T,I> {
	boolean addAccount(Account account); 
	//void deleteAccount(Account account); 
	//void updateAccount(Account account); 
	boolean login(Account account);
	double depositMoney(Double amount);
	double withdrawalMoney(Double amount);
	boolean checkUsername(String username);
	boolean transferMoney(String username, double amount);
	List<I> getMyTransactions();
	List<T> getListAccountsPending();
	boolean activateAccount(String username);
	List<I> getTransactionUser(String username);
}
