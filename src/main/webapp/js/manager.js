//// get dom elements
let editForm = document.forms['edit-form'];
let editBtn = document.getElementById('edit-form-button');
let pdfBtn1 = document.getElementById('pdf-button-1');
let csvBtn1 = document.getElementById('csv-button-1');
let pdfBtn2 = document.getElementById('pdf-button-2');
let csvBtn2 = document.getElementById('csv-button-2');
let closeEditBtn = document.getElementById('close-edit-button');

/// define functions
function tickToggle(e, cell, value, data){
	cell.trigger("editval", !value);
}

function resolvePending() {
	
}

function printIcon(cell, formatterParams, onRendered){
	return "<i class='far fa-file-alt'></i>"
}

/// define variables
let pendingTable = new Tabulator("#approval-table", {
	placeholder:"<h1>Table is loading data</h1>",
	layout:"fitData",
    height:"300px",
    layoutColumnsOnNewData:true,
    dataTreeChildField:"childRows", //look for the child row data array in the childRows field
    groupBy:"author.name",
    columns:[
        {field:"approve", editor:true, align:"center", titleFormatter:"rowSelection", 
        	formatter:"rowSelection", headerSort:false, cellClick:function(e, cell){
        		let row = cell.getRow()
                row.toggleSelect();
            }, align:"center"},
        {title:"Date Submitted", field:"created", sorter:"date"},
        {title:"Description", field:"description", sorter: "string"},
        {title: "Author", field: "author.name", sorter: "string"},
        {title:"Type", field:"type", sorter:"string"},
        {title:"Amount", field:"ammount", sorter:"number", formatter:"money", formatterParams:{
            decimal:".",
            thousand:",",
            symbol:"$",
            symbolBefore:"p",
            precision:false}},
        {title: "Receipts", field:"file", formatter:printIcon, cellClick: function(e, cell) {
        	/// get url of file
        	let row = cell.getRow()
            console.log(row.getCells());
        	window.open("https://www.google.com");
        }},
        {field: "fileLocation", visible:false},
        {field: "status", visible:false}
    ]
});

let requestHistoryTable = new Tabulator("#request-history-table-manager", {
	placeholder:"<h1>Table is loading data</h1>",
    layout:"fitData",
    height:"300px",
    groupBy:"author.name",
    columns:[
        {title:"Request Status", field:"status", sorter:"string"},
        {title:"Date Submitted", field:"created", sorter:"date"},
        {title:"Date Resolved", field:"resolved", sorter:"date"},
        {title: "Author", field: "author.name", sorter: "string"}, 
        {title: "Resolved By", field: "resolver.name", sorter: "string"}, 
        {title:"Description", field:"description", sorter:"string"},
        {title:"Type", field:"type", sorter:"string"},
        {title:"Amount", field:"ammount", sorter:"number", formatter:"money", formatterParams:{
            decimal:".",
            thousand:",",
            symbol:"$",
            symbolBefore:"p",
            precision:false,
        }}
    ]
});

//hookup event listeners
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
	pdfBtn1.disabled = true;
	csvBtn1.disabled = true;
	pdfBtn2.disabled = true;
	csvBtn2.disabled = true;
	let buttons = [pdfBtn1, csvBtn1, pdfBtn2, csvBtn2];
	getFormDataFromServer("/user/info");
	tableLoadBytes([pendingTable], "/reqs?n=1", buttons);
	tableLoadBytes([requestHistoryTable], "/reqs?n=2", buttons);
	//getSession("table",pendingTable);
	//getSession("table",requestHistoryTable);
}

//invoke /out when tab is closed
window.onunload = function() {
	endSessionOnCloseTab();
}

