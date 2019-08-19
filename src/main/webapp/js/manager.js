let $mgOpenReqTable =  $('#open-req-table');
let $mgClosedReqTable = $('#req-hist-table');

/// temp do bootstrapTable
$mgOpenReqTable.bootstrapTable();
$mgClosedReqTable.bootstrapTable();

/// do ajax for all open and closed requests that go to the tables

//tableLoad($mgOpenReqTable, "https://localhost:8070/managers/reim/open", processTableData);
//tableLoad($mgClosedReqTable, "https://localhost:8070/managers/reim/closed", processTableData);
let editBtn = document.getElementById('edit-form-button');

editBtn.addEventListener('click', function(){
	let editForm = document.forms['edit-form'];
	let json = JSON.stringify(  Object.fromEntries(new FormData(editForm))  );
	console.log( json );
	setFormData( json, "http://localhost:8081/ers/user/info");
});