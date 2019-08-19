package utility.validator;

import value.*;

public class ValueValidator extends Validator {
	public static boolean isCurrencyValid(String currency) {
		if(ValueMap.currency.
				containsValue(currency.toUpperCase())) {
			return true;
		}
		return false;
	}
}
