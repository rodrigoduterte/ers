let dateRequest = document.getElementById('date-request');
let today = new Date();

dateRequest.setAttribute("placeholder", today.getMonth() + "/"
	+ today.getDate() + "/" + today.getFullYear())

let $employeeRequestTbl = $('emp-req-table');

// / temp do bootstrapTable

$employeeRequestTbl.bootstrapTable();

// / do ajax for loading employee history requests that go to table

// tableLoad($employeeRequestTbl, "https://localhost:8070/employees/info/",
// processTableData);

// do ajax for sending employee request from the table
var inputs = document.querySelectorAll("input, textarea, select")
let editBtn = document.getElementById('edit-form-button');
let editForm = document.forms['edit-form'];

// for (var i = 0; i < inputs.length; ++i) {
// 	inputs.item(i).addEventListener("change", function (ev) {
// 		let json = JSON.stringify(Object.fromEntries(new FormData(editForm)));
// 		var errors = validate(json, editFormConstraints) || {};
// 		console.log(errors);
// 		showErrorsForInput(this, errors[this.name])
// 	});
// }

// [...editForm].forEach(el => {
// 	el.addEventListener('change',function(e){
// 		let json = JSON.stringify(Object.fromEntries(new FormData(editForm)));
// 		let errors = validate(json,editFormConstraints);
// 		console.log(errors);
// 	})
// })

// function displayError () {

// }

// editBtn.addEventListener('click', function () {
// 	console.log("cccccccccccccc");
// 	// let editForm = document.forms['edit-form'];
// 	let json = JSON.stringify(Object.fromEntries(new FormData(editForm)));
// 	console.log(json);
// 	console.log("validate " + validate(json, constraints));
// 	//	var inputs = document.querySelectorAll("input, textarea, select");
// 	console.log(inputs);

// 	// setFormData( json, "http://localhost:8081/ers/user/info");
// });

// Hook up the inputs to validate on the fly

// for (var i = 0; i < inputs.length; ++i) {
// 	inputs.item(i).addEventListener("change", function (ev) {
// 		var errors = validate(editForm, constraints) || {};
// 		showErrorsForInput(this, errors[this.name])
// 	});
// }

editForm.addEventListener("submit", function (ev) {
	ev.preventDefault();
	let json = JSON.stringify( Object.fromEntries( new FormData(this) ) );
	console.log( "json  "+ json  )
	handleFormSubmit(json, this);
});

function handleFormSubmit(json, form) {
	// validate the form against the constraints
	console.log( "json   "+  json );
	console.log( "editFormCons" + JSON.stringify(editFormConstraints))
	var errors = validate(json, editFormConstraints);
	console.log("errors "+ JSON.stringify(errors));
	console.log("form  "+form)
	// then we update the form to reflect the results
	showErrors(form, errors || {});
	if (!errors) {
		console.log(json);
		showSuccess();
	}
}

// Updates the inputs with the validation errors
function showErrors(form, errors) {
	// We loop through all the inputs and show the errors for that input
	_.each(form.querySelectorAll("input[name], select[name]"), function (input) {
		// Since the errors can be null if no errors were found we need to
		// handle
		// that
		console.log( "input:  "+ input)
		showErrorsForInput(input, errors && errors[input.name]);
	});
}

// Shows the errors for a specific input
function showErrorsForInput(input, errors) {
	// This is the root of the input
	var formGroup = closestParent(input.parentNode, "form-group")
		// Find where the error messages will be insert into
		, messages = formGroup.querySelector(".messages");
	// First we remove any old messages and resets the classes
	resetFormGroup(formGroup);
	// If we have errors
	if (errors) {
		// we first mark the group has having errors
		formGroup.classList.add("has-error");
		// then we append all the errors
		_.each(errors, function (error) {
			addError(messages, error);
		});
	} else {
		// otherwise we simply mark it as success
		formGroup.classList.add("has-success");
	}
}

// Recusively finds the closest parent that has the specified class
function closestParent(child, className) {
	if (!child || child == document) {
		return null;
	}
	if (child.classList.contains(className)) {
		return child;
	} else {
		return closestParent(child.parentNode, className);
	}
}

function resetFormGroup(formGroup) {
	// Remove the success and error classes
	formGroup.classList.remove("has-error");
	formGroup.classList.remove("has-success");
	// and remove any old messages
	_.each(formGroup.querySelectorAll(".help-block.error"), function (el) {
		el.parentNode.removeChild(el);
	});
}

// Adds the specified error with the following markup
// <p class="help-block error">[message]</p>
function addError(messages, error) {
	var block = document.createElement("p");
	block.classList.add("help-block");
	block.classList.add("error");
	block.innerText = error;
	messages.appendChild(block);
}

function showSuccess() {
	// We made it \:D/
	alert("Success!");
}
