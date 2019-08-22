let $mgOpenReqTable =  $('#open-req-table');
let $mgClosedReqTable = $('#req-hist-table');

/// temp do bootstrapTable
$mgOpenReqTable.bootstrapTable();
$mgClosedReqTable.bootstrapTable();

/// do ajax for all open and closed requests that go to the tables

let editForm = document.forms['edit-form'];
let editBtn = document.getElementById('edit-form-button');
let closeEditBtn = document.getElementById('close-edit-button');

//window.addEventListener('load',function() {
//	tableLoad($mgOpenReqTable, "http://localhost:8081/ers/manager/reqs/all?open=true", processTableData);
//	tableLoad($mgClosedReqTable, "http://localhost:8081/ers/manager/reqs/all?open=false", processTableData);
//});

// invoke /out when tab is closed
window.onunload = function() {
	endSessionOnCloseTab();
}

//edit form rendering
window.onload = function() {
	console.log('on load getFormDataFromServer');
	getFormDataFromServer("/user/info");
}

//edit form submission
editBtn.addEventListener('click', function() {
    if(editForm.reportValidity()) {
        /// send form json to server via ajax
        let json = JSON.stringify( Object.fromEntries( new FormData(editForm) ) );
        //setFormData(json, "http://localhost:8081/ers/user/info");
        console.log(json);
        $('#infoModal').modal('hide')
    } else {
        console.log('Form not valid')
    }
})

closeEditBtn.addEventListener('click', function() {
	console.log(sessionStorage.getItem('editFormObject'));
	let formJson = JSON.parse( sessionStorage.getItem('editFormObject') );
	renderForm(formJson);
})
