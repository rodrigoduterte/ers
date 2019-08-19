let $mgOpenReqTable =  $('#open-req-table');
let $mgClosedReqTable = $('#req-hist-table');

/// temp do bootstrapTable
$mgOpenReqTable.bootstrapTable();
$mgClosedReqTable.bootstrapTable();

/// do ajax for all open and closed requests that go to the tables


let editBtn = document.getElementById('edit-form-button');

window.addEventListener('load',function() {
	tableLoad($mgOpenReqTable, "http://localhost:8081/ers/manager/reqs/all?open=true", processTableData);
	tableLoad($mgClosedReqTable, "http://localhost:8081/ers/manager/reqs/all?open=false", processTableData);
});

editBtn.addEventListener('click', function(){
	let editForm = document.forms['edit-form'];
	let json = JSON.stringify(  Object.fromEntries(new FormData(editForm))  );
	console.log( json );
	setFormData( json, "http://localhost:8081/ers/user/info");
});