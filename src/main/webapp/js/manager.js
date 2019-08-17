//let table1 = $('#open-req-table');
//let table2 = $('#req-hist-table');  

let $mgOpenReqTable =  $('#open-req-table');
let $mgClosedReqTable = $('#req-hist-table');

/// do ajax for all open and closed requests that go to the tables

tableLoad($mgOpenReqTable, "https://localhost:8070/managers/reim/open", processTableData);
tableLoad($mgClosedReqTable, "https://localhost:8070/managers/reim/closed", processTableData);