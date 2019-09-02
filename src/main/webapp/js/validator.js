/* register a custom validator fields */
let hrReg = document.getElementById('hr-register');
let hrInv = document.getElementById('hr-invalid');
let unReg = document.getElementById('username-register');
let unEx = document.getElementById('username-exists');
let emReg = document.getElementById('email-register');
let emEx = document.getElementById('email-exists');
let cpReg = document.getElementById('conf-password-register');
let pwReg = document.getElementById('password-register');

function limitSize(file) {
    var FileSize = file.files[0].size / 1024 / 1024; // in MB
    if (FileSize > 5) {
        alert('File size exceeds 5 MB');
        file.value = null;
    }
}

function fieldExists(text, validating) {
	if(validating === 'un') {
		if (text === 'true') {
			unEx.innerHTML = 'Username already exists'
			unReg.setCustomValidity('Username already exists')
		} else {
			unReg.setCustomValidity('')
			unEx.innerHTML = ''
		}
	} else if (validating === 'em') {
		if (text === 'true') {
			emEx.innerHTML = 'Email already exists'
			emReg.setCustomValidity('Email already exists')
		} else {
			emReg.setCustomValidity('')
			emEx.innerHTML = ''
		}
	} else if (validating === 'hr') {
		if (text === 'false') {
			hrInv.innerHTML = 'Invalid HR Code'
			hrReg.setCustomValidity('Invalid HR Code')
		} else {
			hrReg.setCustomValidity('')
			hrInv.innerHTML = ''
		}
	}
}

if (unReg != null) {
  hyperform.addValidator(unReg,
    function (element) {
      var re = new RegExp('^[a-zA-Z].*');
      var valid = re.test(element.value);
      element.setCustomValidity(valid ? ''
        : 'Length must be between 5 to 15 characters, '
        + 'Must start with a letter,'
        + 'Please enter alphanumeric characters only');
      return valid;
    })
}

if (cpReg != null) {
  hyperform.addValidator(cpReg,
    function (element) {
      var valid = element.value === pwReg.value;
      element.setCustomValidity(valid ? ''
        : 'The password does not match the control field.');
      return valid;
    });

}

if (pwReg != null) {
  hyperform.addValidator(pwReg,
    function (element) {
      var re = new RegExp('^(?=.{5,20})(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$');
      var valid = re.test(element.value);
      element.setCustomValidity(valid ? ''
        : 'Length must be between 5 to 20 characters, '
        + 'must have at least one uppercase letter,'
        + 'at least one lowercase letter, and at least one lowercase letter');

      console.log(valid);
      return valid;
    })
  pwReg.addEventListener('keyup',
    function () {
      cpReg.reportValidity();
    });
}

hyperform(window);
