/**
 * 
 */

///employee

bootstrapValidate(['#firstname-register', '#lastname-register'], 'required:Please fill out this field!|' +
														 'alpha:Please enter alphabetic characters');

bootstrapValidate('#email-register', 'email:Please give a valid email!|required:Please fill out this field!');

bootstrapValidate('#username-register', 'required:Please fill out valid username!|' +
									'regex:^[A-Za-z].*:Must start with letter|' +
									'min:5:Length between 5 to 15 characters|' +
									'max:15:Length between 5 to 15 characters|' +
									'alphanum:Please enter alphanumeric characters');

bootstrapValidate(['#password-register', '#conf-password-register'], 'required:Please fill out password|' +
									'min:5:Length must be between 5 to 20 characters|' +
									'max:20:Length must be between 5 to 20 characters|' +
									'regex:^(?=.{5,20})(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$:Must have at least one special character like @#$%^&+=!, \n'+
									'at least one uppercase letter, '+
									'at least one lowercase letter and\n'+
									'at least one number');
									//'regex:.*[!@#$%^&*(),.?":{}|<>].*:Must have at least one special character|'); //+
//									'regex:.*[A-Z].*:Must have at least one uppercase letter|' + 
//									'regex:.*[a-z].*:Must have at least one lowercase letter|' + 
//									'regex:.*[0-9].*:Must have at least one number');

bootstrapValidate('#conf-password-register','matches:#password-register:Your passwords should match');
