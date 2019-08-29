package utility.validator;

public abstract class PasswordValidator extends Validator {
	private static final Integer size = 8;

	public static boolean isValid(String input) {
		return hasCapitalLetters(input) && hasSmallLetters(input) && hasSpecialCharacters(input)
				&& Validator.isMoreThan(PasswordValidator.size, input.length());
	}
}
