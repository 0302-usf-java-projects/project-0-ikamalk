package com.revature;

import com.revature.controller.*;
import com.revature.exception.*;
import com.revature.model.*;
import com.revature.repository.*;
import com.revature.service.*;
public class MainDriver {

	public static void main(String[] args) throws PasswordTooShortException, AccountNameAlreadyTakenException {
		MenuPanel menuPanel = new MenuPanel();
		menuPanel.runMenu();
	}
}
