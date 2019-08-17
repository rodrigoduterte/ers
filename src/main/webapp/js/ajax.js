function tableLoad(table, url, processor) {
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let json = JSON.parse(xhttp.responseText);
            processTableData(table, json);
        }
    };
    xhttp.open("GET", url, true);
    xhttp.send();
}

function processTableData(json) {
    table.bootstrapTable( { data: json } );
}

// tableLoad($managersOpenTbl, "https://localhost:8070/managers/reim/open", processTableData);

function getFormData(json, url) {
    xhttp.open("POST", url, true);
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhttp.send( JSON.stringify(json) );
}