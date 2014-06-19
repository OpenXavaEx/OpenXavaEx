package org.openxava.ex.calculators;

import org.openxava.calculators.ICalculator;
import org.openxava.util.Users;

public class CurrentUserCalculator  implements ICalculator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Object calculate() throws Exception {
		String currentUser = Users.getCurrentUserInfo().getGivenName();
		return currentUser;
	}

}
