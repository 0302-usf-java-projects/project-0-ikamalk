package com.revature.TestBank;

import static com.revature.config.EnvConfig.PASSWORD;
import static com.revature.config.EnvConfig.URL;
import static com.revature.config.EnvConfig.USERNAME;
import static org.junit.Assert.assertTrue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.Test;
import com.revature.repository.BankDAO;

public class TestBank {
  
  BankDAO bankDao = new BankDAO();
  

  
  
  @Test
  public void testServer() {
    try {
      Connection conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
      System.out.println(conn.isClosed());
      assertTrue(conn.isClosed() == false);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  @Test
  public void isUsernameExist() {
    assertTrue(bankDao.checkUsername("kamal") == true);
  }
}
