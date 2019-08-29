//// get dom elements
let editForm = document.forms['edit-form'];
let editBtn = document.getElementById('edit-form-button');
let pdfBtn1 = document.getElementById('pdf-button-1');
let csvBtn1 = document.getElementById('csv-button-1');
let pdfBtn2 = document.getElementById('pdf-button-2');
let csvBtn2 = document.getElementById('csv-button-2');

let approveBtn = document.getElementById("approve-button");
let denyBtn = document.getElementById("deny-button");
let closeEditBtn = document.getElementById('close-edit-button');
let urlParams = new URLSearchParams(window.location.search);
let mapResolve = new Set();

/// define functions
function printIcon(cell, formatterParams, onRendered){
	return "<i class='far fa-file-alt'></i>"
}

/// define variables
let setResolve = new Set();

let pendingTable = new Tabulator('#approval-table', {
    index: "id",
	placeholder:"<h1>Table is loading data</h1>",
	selectable:true,
    height:"300px",
    resizableColumns:false,
    layout:"fitColumns",
    virtualDomBuffer:300,
    groupBy:"author.name",
    groupStartOpen:false,
    groupToggleElement:"header",
    rowSelected: function (row) {
        setResolve.add(row.getIndex())
    },
    rowDeselected: function (row) {
        setResolve.delete(row.getIndex())
    },
    columns:[
        {title:"Date Submitted", field:"created", sorter:"date", width: 130},
        {title:"Description", field:"description", formatter:"textarea", sorter: "string", width: 350},
        {title: "Author", field: "author.name", sorter: "string", width: 150},
        {title:"Type", field:"type", sorter:"string", width: 100},
        {title:"Amount", field:"ammount", sorter:"number", formatter:"money", formatterParams:{
            decimal:".", thousand:",", symbol:"$ ", symbolBefore:"p",
            precision:false}, width: 120},
        {title: "Receipts", field:"file", cellClick: function(e, cell) {
        	/// get url of file
        	let row = cell.getRow()
            console.log(row.getCells());
        	window.open("https://www.google.com");
        }, width: 100},
        {field: "fileLocation", visible:false},
        {field: "status", visible:false}
    ]
});

let requestHistoryTable = new Tabulator("#request-history-table-manager", {  ///total width 1230
    index: "id",
    placeholder:"<h1>Table is loading data</h1>",
    layout:"fitColumns",
    resizableColumns:false,
    height:"300px",
    groupBy:"author.name",
    groupStartOpen:false,
    groupToggleElement:"header",
    columns:[
        {title:"Status", field:"status", sorter:"string", width: 100},
        {title:"Date Submitted", field:"created", sorter:"date", width: 130},
        {title:"Date Resolved", field:"resolved", sorter:"date", width: 130},
        {title: "Author", field: "author.name", sorter: "string", width: 150}, 
        {title: "Resolved By", field: "resolver.name", sorter: "string", width: 150}, 
        {title:"Description", field:"description", sorter:"string", width: 350},
        {title:"Type", field:"type", sorter:"string", width: 100},
        {title:"Amount", field:"ammount", sorter:"number", formatter:"money", formatterParams:{
            decimal:".", thousand:",", symbol:"$ ", symbolBefore:"p",
            precision:false}, width: 120}
    ]
});

approveBtn.addEventListener('click', function () {
	let resolver = urlParams.get('fname') + " " + urlParams.get('lname')
    for (let number of setResolve.keys()) {
        pendingTable.deleteRow(number);
        requestHistoryTable.updateRow(number, 
                {status: "APPROVED", resolved: moment().format("MMM D YYYY"), "resolver.name": resolver});
    }
    resolvePending(setResolve,'/reqs?app=1')
    setResolve.clear();
    
})

denyBtn.addEventListener('click', function () {
	let resolver = urlParams.get('fname') + " " + urlParams.get('lname')
    for (let number of setResolve.keys()) {
        pendingTable.deleteRow(number);
        requestHistoryTable.updateRow(number, 
                {status: "DENIED", resolved: moment().format("MMM D YYYY"), "resolver.name": resolver});
    }
    resolvePending(setResolve,'/reqs?app=0')
    setResolve.clear();
})

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

closeEditBtn.addEventListener('click', function() {
	console.log(sessionStorage.getItem('editFormObject'));
	let formJson = JSON.parse( sessionStorage.getItem('editFormObject') );
	renderForm(formJson);
})

window.onload = function() {
	approveBtn.disabled = true;
	denyBtn.disabled = true;
	pdfBtn1.disabled = true;
	csvBtn1.disabled = true;
	pdfBtn2.disabled = true;
	csvBtn2.disabled = true;
	let buttons = [approveBtn, denyBtn, pdfBtn1, csvBtn1, pdfBtn2, csvBtn2];
	getFormDataFromServer("/user/info");
	tableLoadBytes(pendingTable, "/reqs?n=1", buttons);
	tableLoadBytes(requestHistoryTable, "/reqs?n=2", buttons);
}

//invoke /out when tab is closed
window.onunload = function() {
	endSessionOnCloseTab();
}

