/* register a custom validator fields */
let unReg = document.getElementById('username-register');
let cpReg = document.getElementById('conf-password-register');
let pwReg = document.getElementById('password-register');

if (unReg != null) {
  hyperform.addValidator(unReg,
    function (element) {
      var re = new RegExp('^[a-zA-Z].*');
      //element.value.match(re)
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
      //element.value.match(re)
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


/*
 * whenever the password field changes, revalidate the confirmation field, too
 */


/// custom error messages of each control


hyperform(window);
