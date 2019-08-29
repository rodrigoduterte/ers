package utility.validator;

public abstract class UsernameValidator extends Validator {

	private static final int size = 5;

	public static boolean isValid(String input) {
		return isMoreThan(UsernameValidator.size, input.length()) && !hasSpecialCharacters(input)
				&& !hasCapitalLetters(input) && startsWithLetter(input) && (!hasNumber(input) || hasNumber(input));
	}

}
