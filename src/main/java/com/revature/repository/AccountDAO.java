package com.revature.repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import com.revature.connection.ConnectionUtil;
import com.revature.model.Account;
import com.revature.model.User;
import com.revature.exception.*;

public class AccountDAO implements DAO <Account>{
  
  static User user = new User();
  WaitClass wait = new WaitClass();


	@Override
	public boolean addAccount(Account account)  {
	  wait.start();
	  
	  if (checkUsername(account.getUsername())) {
	    return false;
	  } else {
	    try(Connection conn = ConnectionUtil.connect()) {
      String sql = "insert into account (username, password, balance) values(?,?,?)";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, account.getUsername());
      ps.setString(2, account.getPassword());
      ps.setDouble(3, account.getBalance());
      boolean b=ps.execute();
        wait.stop();
      ps.close();
      System.out.println();
    } catch(SQLException e) {
      e.printStackTrace();
    }
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
    return false;
  }


  @Override
  public double depositMoney(Double amount) {
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
      return result;
    }catch(SQLException e) {
      e.printStackTrace();
    }
    return -1;
  }




  @Override
  public double withdrawalMoney(Double amount) {
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
      return result;
    }catch(SQLException e) {
      e.printStackTrace();
    }
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
   return false;
  }

}
