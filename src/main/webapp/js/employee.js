let dateRequest = document.getElementById('date-request');
let today = new Date();

dateRequest.setAttribute("placeholder", today.getMonth() + "/" + today.getDate() + "/" + today.getFullYear()) 

let $employeeRequestTbl = $('emp-req-table');

/// do ajax for loading employee history requests that go to table

tableLoad($employeeRequestTbl, "https://localhost:8070/employees/info/", processTableData);

// do ajax for sending employee request from the table

let requestModal = document.getElementById('requestButton');

requestModal.addEventListener('click', function(){
    getFormData(json, url) 
});
