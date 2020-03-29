package com.revature.connection;

import static com.revature.config.EnvConfig.PASSWORD;
import static com.revature.config.EnvConfig.URL;
import static com.revature.config.EnvConfig.USERNAME;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
  
  
  public static Connection connect() throws SQLException {
    Connection conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
    return conn;

  }
}
