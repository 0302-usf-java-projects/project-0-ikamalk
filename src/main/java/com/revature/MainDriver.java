package com.revature;

import org.apache.log4j.Logger;
import com.revature.controller.*;
import com.revature.exception.*;
public class MainDriver {
  
  public static final Logger logger = Logger.getLogger(MainDriver.class);

	public static void main(String[] args) throws PasswordTooShortException, AccountNameAlreadyTakenException {
	  logger.info("Launching the app bank !");
		MenuPanel menuPanel = new MenuPanel();
		menuPanel.runMenu();
	}
}
