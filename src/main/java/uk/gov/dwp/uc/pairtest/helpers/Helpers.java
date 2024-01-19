package uk.gov.dwp.uc.pairtest.helpers;

/* Helper functions */
public class Helpers {

	public int checkIfInteger(String val) {
		try {
			return Integer.parseInt(val);
		} catch (NumberFormatException ex) {
			return -1;
		}
	}

	public boolean validatePerson(String name, int age) {
		if (checkIfInteger(name) >= 0) {
			System.out.println("Name of person must contain both numbers and letters");
			return false;
		} else if (age < 0) {
			System.out.println("Age of person must be greater than or equal to zero");
			return false;
		} else if (age >= 300) {
			System.out.println("Age you an alien?");
			return false;
		}

		return true;
	}

	public long generateId() {
		long leftLimit = 1L;
		long rightLimit = 10000000000000L;
		return leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
	}

}
