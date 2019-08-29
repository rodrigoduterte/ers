/// form operations
let loginForm = document.forms['login-form'];
let registerForm = document.forms['register-form'];
let loginBtn = document.getElementById('login-button');
let registerBtn = document.getElementById('register-button');

let usernameInput = document.getElementById('username-register');
let emailInput = document.getElementById('email-register');

usernameInput.addEventListener('keyup', function(ev){
	let entered = ev.target.value;
	if(entered) {
		typingTimer = setTimeout(function() {
		    inputValueExists('http://localhost:8081/ers/user/info?field=un&un=' + entered, 'un')
		}, 200);
	}
})

emailInput.addEventListener('keyup', function(ev){
	let entered = ev.target.value;
	if(entered) {
		typingTimer = setTimeout(function() {
		    inputValueExists('http://localhost:8081/ers/user/info?field=em&em=' + entered, 'em')
		}, 200);
	}
})

///
window.onload = function() {
	//preLoad("http://localhost:8081/ers/employee/req?all=2");
}

