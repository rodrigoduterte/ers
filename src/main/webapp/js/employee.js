/// form operations
let editForm = document.forms['edit-form'];
let requestForm = document.forms['request-form'];
let logoutForm = document.forms['logout-form'];
let exitForm = document.forms['exit-form'];

let fileBtn = document.getElementById('file-request');
let closeEditBtn = document.getElementById('close-edit-button');
let requestBtn = document.getElementById('request-form-button');
let editBtn = document.getElementById('edit-form-button');
let pdfBtn = document.getElementById('pdf-button');
let csvBtn = document.getElementById('csv-button');

/// table requests tabulator
let requestHistoryTable = new Tabulator("#request-history-table", {
	placeholder:"Table is loading data",
	height:"300px",
    layout:"fitData",
    layoutColumnsOnNewData:true,
    dataTreeChildField:"childRows", //look for the child row data array in the childRows field
    columns:[  // i need a query to construct
        {title:"Request Status", field:"status", sorter:"string"},
        {title:"Date Submitted", field:"created", sorter: "date", sorterParams:{format:"MMM d, yyyy"}},
        {title:"Date Approved/Denied", field:"resolved", sorter: "date", sorterParams:{format:"MMM d, yyyy"}},
        {
        	title: "Approved/Denied By",
        	columns: [
        	{title:"Firstname", field:"resolver.firstName", sorter: "string"}, 
            {title:"Lastname", field:"resolver.lastName", sorter: "string"}]
        },
        {title:"Description", field:"description", sorter: "string"},
        {title:"Type", field:"type", sorter:"string"},
        {title:"Amount", field:"ammount", sorter:"number"}
    ]
});

fileBtn.addEventListener('change', function(ev) {
	console.log(ev.target)
	limitSize(ev.target)
});

// edit form submission
editBtn.addEventListener('click', function() {
    if(editForm.reportValidity()) {
        /// send form json to server via ajax
        let json = JSON.stringify( Object.fromEntries( new FormData(editForm) ) );
        saveFormData(json, "http://localhost:8081/ers/user/info");
        console.log(json);
        $('#infoModal').modal('hide')
    } else {
        console.log('Form not valid')
    }
})

// revert to previous form info on click of close button
// anything typed before clicking close button 
// but not edit button is unsaved
closeEditBtn.addEventListener('click', function() {
	let formJson = sessionStorage.getItem('editFormObject');
	renderForm(formJson);
})

pdfBtn.addEventListener('click', function() {
	requestHistoryTable.print();
})

csvBtn.addEventListener('click', function() {
	requestHistoryTable.download("csv", "history_csv.csv", {bom:true});
})


//invoke /out when tab is closed
window.onunload = function() {
	endSessionOnCloseTab();
}

editForm.addEventListener("submit", function (ev) {
    // fire this to prevent page from refreshing
    ev.preventDefault();
})

// request form
// reload table on successful submit of request
requestBtn.addEventListener('click', function() {
    if(requestForm.reportValidity()) {
        /// send form json to server via ajax
        let formData = new FormData(requestForm);
        saveFormDataWithFile(formData);
        /// add attributes to formData
        formData.append('created', moment().format('MMM D, YYYY'));
        formData.append('status', 'PENDING');
        formData.delete('fileReq');
        /// add row to table
        let stringified = JSON.stringify( [ Object.fromEntries(formData) ] )
        addFormItemToTable( stringified, requestHistoryTable );
        /// reset and hide form
        requestForm.reset();
        $('#requestModal').modal('hide');
    } else {
        console.log('Form not valid')
    }
})

requestForm.addEventListener('submit', function (ev) {
    // fire this to prevent page from refreshing
    ev.preventDefault();
})


exitForm.addEventListener('submit', function(ev) {
	sessionStorage.clear();
})

/// input operations
let dateRequest = document.getElementById('date-request');
let today = new Date();

dateRequest.setAttribute("placeholder", today.getMonth() + "/"
	+ today.getDate() + "/" + today.getFullYear())
	
window.onload = function() {
	pdfBtn.disabled = true;
	csvBtn.disabled = true;
	getFormDataFromServer("/user/info");
	tableLoad(requestHistoryTable, 'http://localhost:8081/ers/employee/req?all=0')
}

//document.addEventListener("DOMContentLoaded", function(event) { 
//	pdfBtn.disabled = true;
//	csvBtn.disabled = true;
//	getFormDataFromServer("/user/info");
//	tableLoad(requestHistoryTable, 'http://localhost:8081/ers/employee/req?all=0')
//});

function addFormItemToTable(json, table) {
	table.updateOrAddData(json);
	table.redraw(true);
}

