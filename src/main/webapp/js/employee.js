let dateRequest = document.getElementById('date-request');
let today = new Date();

dateRequest.setAttribute("placeholder", today.getMonth() + "/" + today.getDate() + "/" + today.getFullYear()) 

let $employeeRequestTbl = $('#emp-req-table');

/// temp do bootstrapTable

$employeeRequestTbl.bootstrapTable();

/// do ajax for loading employee history requests that go to table

//tableLoad($employeeRequestTbl, "https://localhost:8070/employees/info/", processTableData);

// do ajax for sending employee request from the table

let editBtn = document.getElementById('edit-form-button');

editBtn.addEventListener('click', function(){
	let editForm = document.forms['edit-form'];
	let json = JSON.stringify(  Object.fromEntries(new FormData(editForm))  );
	console.log( json );
	setFormData( json, "http://localhost:8081/ers/user/info");
});

