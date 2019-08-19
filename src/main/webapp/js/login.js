/**
 * 
 */
function setFormData(formData, url) {
	var xhttp = new XMLHttpRequest();
	xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhttp.send( formData );
    console.log(formData);
}

let btnLogin =  document.getElementById('button-login');
let btnRegister = document.getElementById('button-register');

btnLogin.addEventListener('click', function() {
	let loginForm = document.forms['login-form'];
	let json = JSON.stringify(  Object.fromEntries(new FormData(loginForm))  );
	console.log( json );
	setFormData( json, "http://localhost:8081/ers/login")
})

