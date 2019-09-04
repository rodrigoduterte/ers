//// get dom elements
let editForm = document.forms['edit-form'];

let editBtn = document.getElementById('edit-form-button');
let approveBtn = document.getElementById("approve-button");
let denyBtn = document.getElementById("deny-button");
let closeEditBtn = document.getElementById('close-edit-button');
let welcome = document.getElementById('welcome');

let pdfBtns = document.getElementsByClassName('pdfbtn');
let csvBtns = document.getElementsByClassName('csvbtn');

let urlParams = new URLSearchParams(window.location.search);
let mapResolve = new Set();


/// define functions
function printIcon(cell, formatterParams, onRendered){
	return "<i class='far fa-file-alt'></i>"
}

/// define variables
let setResolve = new Set();

let pendingTable = new Tabulator('#approval-table', {
	initialFilter:[
        {field:"author", type:"!=", value:null}
    ],
    index: "id",
	placeholder:"<h1>Table is loading data</h1>",
	printAsHtml:true,
	selectable:true,
    height:"300px",
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
        {title:"Date Submitted", field:"created", sorter:"date", width: 170},
        {title:"Description", field:"description", formatter:"textarea", sorter: "string", width: 400},
        {title: "Author", field: "author", sorter: "string", width: 150, visible: false}, 
        {title: "Author", field: "author.name", sorter: "string", width: 150, visible: false},
        {title:"Type", field:"type", sorter:"string", width: 100},
        {title:"Amount", field:"ammount", sorter:"number", formatter:"money", formatterParams:{
            decimal:".", thousand:",", symbol:"$ ", symbolBefore:"p",
            precision:false}, width: 120},
        {title: "Receipts", field:"receipt", cellClick: function(e, cell) {
        	let row = cell.getRow();
        	window.open
        	("https://devonvirdenprojects.s3.us-east-2.amazonaws.com/"+cell.getValue());
        	row.toggleSelect();
        }, width: 140}
    ]
});

let requestHistoryTable = new Tabulator("#request-history-table-manager", {  ///total width 1230
	initialFilter:[
        {field:"author", type:"!=", value:null}
    ],
    index: "id",
    placeholder:"<h1>Table is loading data</h1>",
    layout:"fitColumns",
    printAsHtml:true,
    height:"300px",
    groupBy:"author.name",
    groupStartOpen:false,
    groupToggleElement:"header",
    columns:[
        {title:"Status", field:"status", sorter:"string", width: 100},
        {title:"Date Submitted", field:"created", sorter:"date", width: 180},
        {title:"Date Resolved", field:"resolved", sorter:"date", width: 180},
        {title: "Author", field: "author", sorter: "string", width: 150, visible: false}, 
        {title: "Author", field: "author.name", sorter: "string", width: 150, visible: false}, 
        {title: "Resolved By", field: "resolver.name", sorter: "string", width: 150}, 
        {title:"Description", field:"description", sorter:"string", width: 400},
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
    resolvePending(setResolve,'/ers/reqs?app=1')
    setResolve.clear();
    
})

denyBtn.addEventListener('click', function () {
	let resolver = urlParams.get('fname') + " " + urlParams.get('lname')
    for (let number of setResolve.keys()) {
        pendingTable.deleteRow(number);
        requestHistoryTable.updateRow(number, 
                {status: "DENIED", resolved: moment().format("MMM D YYYY"), "resolver.name": resolver});
    }
    resolvePending(setResolve,'/ers/reqs?app=0')
    setResolve.clear();
})

editBtn.addEventListener('click', function() {
    if(editForm.reportValidity()) {
        /// send form json to server via ajax
        let json = JSON.stringify( Object.fromEntries( new FormData(editForm) ) );
        saveFormData(json, "/ers/user/info");
        $('#infoModal').modal('hide')
    } else {
    	alert('Values entered are not valid')
    }
})

closeEditBtn.addEventListener('click', function() {
	console.log(sessionStorage.getItem('editFormObject'));
	let formJson = JSON.parse( sessionStorage.getItem('editFormObject') );
	renderForm(formJson);
})


window.onload = function() {
	welcome.innerHTML = "Welcome " + urlParams.get('fname') + " " + urlParams.get('lname');
	approveBtn.disabled = true;
	denyBtn.disabled = true;
	getFormDataFromServer("/ers/user/info");
	tableLoad(pendingTable, "/ers/reqs?n=1&type=m", []);
	tableLoad(requestHistoryTable, "/ers/reqs?n=2&type=m", [approveBtn, denyBtn]);
}

//invoke /out when tab is closed
window.onunload = function() {
	endSessionOnCloseTab();
}

