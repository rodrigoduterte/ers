package utility.validator;

public abstract class Validator {

	public static boolean hasCapitalLetters(String string) {
		return string.matches(".*[A-Z].*");
	}

	public static boolean hasSmallLetters(String string) {
		return string.matches(".*[a-z].*");
	}

	public static boolean hasSpecialCharacters(String string) {
		return string.matches(".*[`~!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?].*");
		// return string.matches("[^a-zA-Z0-9]*");
	}

	public static boolean hasNumber(String string) {

		return string.matches(".*[0-9].*");
	}

	public static boolean onlyLetters(String string) {
		return string.matches("[a-zA-Z]+$");
	}

	public static boolean onlyNumbers(String string) {
		return string.matches("[0-9]+$");
	}

	public static boolean isMoreThan(int standardSize, int actualSize) {
		return actualSize >= standardSize;
	}

	public static boolean startsWithLetter(String string) {
		return string.substring(0, 1).matches("[A-Za-z]");
	}

}
