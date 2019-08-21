/// form operations
let loginForm = document.forms['login-form'];
let registerForm = document.forms['register-form'];
let loginBtn = document.getElementById('login-button');
let registerBtn = document.getElementById('register-button');

registerBtn.addEventListener('click', function() {
    if(registerForm.reportValidity()) {
        /// send form json to server via ajax
        let json = JSON.stringify( Object.fromEntries( new FormData(editForm) ) );
        //setFormData(json, "http://localhost:8081/ers/user/info");
        console.log(json);
    } else {
        console.log('Form not valid')
    }
})


// function setFormData(formData, url) {
//     var xhttp = new XMLHttpRequest();
//     xhttp.open("POST", url, true);
//     xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
//     xhttp.send(formData);
//     console.log(formData);
// }



// loginBtn.addEventListener('click', function () {
//     let loginForm = document.forms['login-form'];
//     let json = JSON.stringify(Object.fromEntries(new FormData(loginForm)));
//     console.log(json);
//     setFormData(json, "http://localhost:8081/ers/login")
// })

