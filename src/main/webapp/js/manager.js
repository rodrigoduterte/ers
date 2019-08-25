/// temp do bootstrapTable
//$mgOpenReqTable.bootstrapTable();
//$mgClosedReqTable.bootstrapTable();

/// do ajax for all open and closed requests that go to the tables

let editForm = document.forms['edit-form'];
let exitForm = document.forms['exit-form'];
let editBtn = document.getElementById('edit-form-button');
let closeEditBtn = document.getElementById('close-edit-button');
let exitBtn = document.getElementById('exit-button');

let approvalTable = new Tabulator("#approval-table", {
    layout:"fitData",
    columns:[
        {title:"", field:"approve", editor:"tickCross"},
        {title:"Date Submitted", field:"created"},
        {title:"Description", field:"description"},
        {
        	title: "Author",
        	columns: [
        		{title:"Firstname", field:"author.firstName"},
        		{title:"Lastname", field:"author.lastName"}
        	]
        },
        {title:"Type", field:"type"},
        {title:"Amount", field:"ammount", formatter:"money", formatterParams:{
            decimal:".",
            thousand:",",
            symbol:"$",
            symbolAfter:"p",
            precision:false,
        }}
    ]
});

approvalTable.setData([{"created":1524877345000,"resolved":null,
	"id":246,"ammount":4316.23,"description":"Morbi odio odio, elementum eu, interdum eu, tincidunt",
	"receipt":null,"author":{"id":7,"username":"graynor6","password":"47nwj#L6wj=K3,;","firstName":"Graig",
		"lastName":"Raynor","email":"graynor6@chron.com","role":"FINANCE MANAGER","name":"Graig Raynor"},"resolver":null,"type":"TRAVEL","status":"PENDING"}])

let requestHistoryTable = new Tabulator("#request-history-table", {
    layout:"fitData",
    columns:[
        {title:"Request Status", field:"status"},
        {title:"Date Submitted", field:"created"},
        {title:"Date Approved/Denied", field:"resolved"}, 
        {
        	title: "Resolved By",
        	columns: [
        		{title:"Firstname", field:"author.firstName"},
        		{title:"Lastname", field:"author.lastName"}
        	]
        }, 
        {title:"Description", field:"descReq"},
        {title:"Type", field:"typeReq"},
        {title:"Amount", field:"amountReq"}
    ]
});


// invoke /out when tab is closed
window.onunload = function() {
	endSessionOnCloseTab();
}

//edit form rendering
window.onload = function() {
	getFormDataFromServer("/user/info");
	tableLoad(requestHistoryTable, 'http://localhost:8081/ers/employee/req?all=1')
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

exitForm.addEventListener('submit', function(ev) {
	sessionStorage.clear();
})

