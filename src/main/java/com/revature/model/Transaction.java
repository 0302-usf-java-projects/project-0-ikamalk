package com.revature.model;

public class Transaction {
  
  public String operation;
  public double amount;
  public String time_transaction;
  
  public Transaction(String operation, double amount, String time_transaction) {
    super();
    this.operation = operation;
    this.amount = amount;
    this.time_transaction = time_transaction;
  }

}
