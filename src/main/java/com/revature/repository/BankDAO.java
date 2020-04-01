package com.revature.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.revature.MainDriver;
import com.revature.connection.ConnectionUtil;
import com.revature.model.Account;
import com.revature.model.Transaction;


public class BankDAO implements InterfaceDAO <Account,Transaction>{
  
  static Account user = new Account();
  WaitClass wait = new WaitClass();
  public static final Logger logger = Logger.getLogger(MainDriver.class);


	@Override
	public boolean addAccount(Account account)  {
	  wait.start();
	  if (checkUsername(account.getUsername())) {
	      wait.stop();
	    return false;
	  } else {
	    try(Connection conn = ConnectionUtil.connect()) {
      String sql = "insert into account (username, password, balance,typeAccount, isActivated) values(?,?,?,?,?)";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, account.getUsername());
      ps.setString(2, account.getPassword());
      ps.setDouble(3, account.getBalance());
      ps.setString(4, "customer");
      ps.setBoolean(5, false);
      ps.execute();
        wait.stop();
      ps.close();
      System.out.println();
      return true;
    } catch(SQLException e) {
      e.printStackTrace();
    }
	    wait.stop();
	    return true;
	  }
	}
	



  @Override
  public boolean login(Account account)  {
    wait.start();
    try(Connection conn = ConnectionUtil.connect()) {
       String sql = "select * from Account where username='"+account.getUsername()+"' and password= '"+account.getPassword()+"';\r\n";
       PreparedStatement ps = conn.prepareStatement(sql);
       ResultSet b=ps.executeQuery();
       boolean response;
       if(b.next()) {
         response = true;
         user.setId(b.getInt(1));
         user.setUsername(b.getString(2));
         user.setBalance(b.getDouble(4));
         user.setRole(b.getString(6));
         user.setActivated(b.getBoolean(7));
         user.setLoggedIn(true);
       } else {
         response = false;
       }
       ps.close();
       wait.stop();
       System.out.println();
       return response;
     } catch(SQLException e) {
       e.printStackTrace();
     }
    logger.error("error with login");
    return false;

  }


  @Override
  public double depositMoney(Double amount) {
    wait.start();
  try(Connection conn = ConnectionUtil.connect()) {
      String sql = "{ ? = call depositMoney(?,?) }";
      CallableStatement cs = conn.prepareCall(sql);
      cs.registerOutParameter(1, Types.DOUBLE);
      cs.setDouble(2, amount);
      cs.setInt(3, user.getId());
      cs.execute();
      double result  = cs.getDouble(1);
      user.setBalance(result);
          cs.close();
          wait.stop();
          System.out.println();
      return result;
    }catch(SQLException e) {
      e.printStackTrace();
    }
  wait.stop();
  System.out.println();
  logger.error("error with depositMoney");

    return -1;
  }




  @Override
  public double withdrawalMoney(Double amount) {
    wait.start();
    try(Connection conn = ConnectionUtil.connect()) {
      String sql = "{ ? = call withdrawalMoney(?,?) }";
      CallableStatement cs = conn.prepareCall(sql);
      cs.registerOutParameter(1, Types.DOUBLE);
      cs.setDouble(2, amount);
      cs.setInt(3, user.getId());
      cs.execute();
      double result  = cs.getDouble(1);
      user.setBalance(result);
          cs.close();
          wait.stop();
          System.out.println();
      return result;
    }catch(SQLException e) {
      e.printStackTrace();
    }
    wait.stop();
    System.out.println();
    logger.error("error with withdrawalMoney");
    return -1;
  }




  @Override
  public boolean checkUsername(String username) {
    try(Connection conn = ConnectionUtil.connect()) {
      String sql = "select exists(select 1 from Account where username= '"+username+"' )";
      PreparedStatement ps = conn.prepareStatement(sql);
      ResultSet b=ps.executeQuery();
      b.next();
      boolean response = b.getBoolean(1);
      ps.close();
      wait.stop();
      System.out.println();
      return response;
    } catch(SQLException e) {
      e.printStackTrace();
    }
    logger.error("error with checkUsername");
   return false;
  }




  @Override
  public boolean transferMoney(String username, double amount) {
    wait.start();
    if (!checkUsername(username)) {
      wait.stop();
      return false;
    } else {
    try(Connection conn = ConnectionUtil.connect()) {
      String sql = "{ ? = call transferMoney(?,?,?) }";
      CallableStatement cs = conn.prepareCall(sql);
      cs.registerOutParameter(1, Types.DOUBLE);
      cs.setString(2, username);
      cs.setDouble(3, amount);
      cs.setInt(4, user.getId());
      cs.execute();
      double result  = cs.getDouble(1);
      user.setBalance(result);
      cs.close();
      wait.stop();
      System.out.println();
      return true;
    }catch(SQLException e) {
      e.printStackTrace();
    }
    wait.stop();
    System.out.println();
    logger.error("error with transferMoney");
    return false;
  }
  }









  @Override
  public List<Account> getListAccountsPending() {
    wait.start();
    try(Connection conn = ConnectionUtil.connect()) {
      String sql = "select * from Account where typeaccount='customer' and isactivated= false";
      PreparedStatement ps = conn.prepareStatement(sql);
      ResultSet rs=ps.executeQuery();
      List<Account> pendingAccounts = new ArrayList<Account>();
      while(rs.next()) {
        pendingAccounts.add(new Account(rs.getString(2)));
      }
      rs.close();
      ps.close();
      wait.stop();
      System.out.println();
      return pendingAccounts;
    } catch(SQLException e) {
      e.printStackTrace();
    }
    logger.error("error with getListAccountsPending");
   return null;
  }




  @Override
  public boolean activateAccount(String username) {
    wait.start();
    try(Connection conn = ConnectionUtil.connect()) {
      String sql = "update Account set isactivated=true where username='"+username+"'";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.execute();
      ps.close();
      wait.stop();
      System.out.println();
      return true;
    } catch(SQLException e) {
      e.printStackTrace();
    }
    logger.error("error with activateAccount");
   return false;
  }






  @Override
  public List<Transaction> getTransactionUser(String username) {
    wait.start();
    try(Connection conn = ConnectionUtil.connect()) {
      String sql = "select * from Bank_Transaction where username='"+username+"'";
      PreparedStatement ps = conn.prepareStatement(sql);
      ResultSet rs=ps.executeQuery();
      List<Transaction> myTransaction = new ArrayList<Transaction>();
      while(rs.next()) {
        myTransaction.add(new Transaction(rs.getString(3),rs.getDouble(4),rs.getString(5)));
      }
      rs.close();
      ps.close();
      wait.stop();
      System.out.println();
      return myTransaction;
    } catch(SQLException e) {
      e.printStackTrace();
    }
    logger.error("error with getTransactionUser");
   return null;
  }




  @Override
  public List<Transaction> getMyTransactions() {
    wait.start();
    try(Connection conn = ConnectionUtil.connect()) {
      String sql = "select * from Bank_Transaction where account_id="+user.getId();
      PreparedStatement ps = conn.prepareStatement(sql);
      ResultSet rs=ps.executeQuery();
      List<Transaction> myTransaction = new ArrayList<Transaction>();
      while(rs.next()) {
        myTransaction.add(new Transaction(rs.getString(3),rs.getDouble(4),rs.getString(5)));
      }
      rs.close();
      ps.close();
      wait.stop();
      System.out.println();
      return myTransaction;
    } catch(SQLException e) {
      e.printStackTrace();
    }
    logger.error("error with getMyTransactions");
   return null;
  }
  
  


}
