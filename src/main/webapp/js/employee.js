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
let welcome = document.getElementById('welcome');

let urlParams = new URLSearchParams(window.location.search);

/// table requests tabulator
let requestHistoryTable = new Tabulator("#request-history-table", {
	placeholder:"<h1>Table is loading data</h1>",
	height:"300px",
	resizableColumns:false,
    layout:"fitColumns",
    layoutColumnsOnNewData:true,
    columns:[
        {title:"Status", field:"status", sorter:"string", width: 100},
        {title:"Date Submitted", field:"created", sorter: "date", sorterParams:{format:"MMM d yyyy"}, width: 170},
        {title:"Date Resolved", field:"resolved", sorter: "date", sorterParams:{format:"MMM d yyyy"}, width: 170},
        {title: "Resolved By", field: "resolver.name", sorter: "string", width: 150},
        {title:"Description", field:"description", sorter: "string", width: 400},
        {title:"Type", field:"type", sorter:"string", width: 100},
        {title:"Amount", field:"ammount", sorter:"number", formatter:"money", formatterParams:{
            decimal:".",
            thousand:",",
            symbol:"$",
            symbolBefore:"p",
            precision:false,
        }, width: 120}
    ]
});

fileBtn.addEventListener('change', function(ev) {
	console.log(ev.target)
	limitSize(ev.target)
});

/// submit form that has all valid values
editBtn.addEventListener('click', function() {
    if(editForm.reportValidity()) {
        // end form json to server via ajax
        let json = JSON.stringify( Object.fromEntries( new FormData(editForm) ) );
        saveFormData(json, '/ers/user/info');
        $('#infoModal').modal('hide')
    } else {
        alert('Values entered are not valid')
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
	requestHistoryTable.download("csv", "employee.csv", {bom:true});
})


// invoke /out when tab is closed
window.onunload = function() {
	localStorage.clear();
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
        /// get form data of requestForm
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
        alert('Values entered are not valid')
    }
})

requestForm.addEventListener('submit', function (ev) {
    // fire this to prevent page from refreshing
    ev.preventDefault();
})


exitForm.addEventListener('submit', function(ev) {
	sessionStorage.clear();
})

/// display current date
let dateRequest = document.getElementById('date-request');
let today = new Date();

dateRequest.setAttribute("placeholder", today.getMonth() + "/"
	+ today.getDate() + "/" + today.getFullYear())
	
window.onload = function() {
	welcome.innerHTML = "Welcome " + urlParams.get('fname') + " " + urlParams.get('lname');
	pdfBtn.disabled = true;
	csvBtn.disabled = true;
	requestBtn.disabled = true;
	getFormDataFromServer("/ers/user/info");
	tableLoad(requestHistoryTable, '/ers/reqs?n=0&type=m', [pdfBtn, csvBtn, requestBtn]);
}

/// add form to table
function addFormItemToTable(json, table) {
	table.updateOrAddData(json);
	table.redraw(true);
}

