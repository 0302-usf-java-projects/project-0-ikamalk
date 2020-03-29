package com.revature;

import com.revature.controller.*;
import com.revature.exception.*;
public class MainDriver {

	public static void main(String[] args) throws PasswordTooShortException, AccountNameAlreadyTakenException {
		MenuPanel menuPanel = new MenuPanel();
		menuPanel.runMenu();
	}
}
