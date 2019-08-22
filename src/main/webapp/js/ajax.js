function tableLoad(table, url, processor) {
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let json = JSON.parse(xhttp.responseText);
            //processTableData(table, json);
        }
    };
    xhttp.open("GET", url, true);
    xhttp.send();
}

function processTableData(json) {
    table.bootstrapTable({ data: json });
}

//done
function getFormDataFromServer(uri) {
	let json = {};
	let xhttp = new XMLHttpRequest();
	console.log('getFormDataFromServer');
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let jso = JSON.parse(xhttp.responseText);
            sessionStorage.setItem('editFormObject', JSON.stringify(jso));
            renderForm(jso);
        }
    };
    
    xhttp.open("GET", "http://localhost:8081/ers"+uri, true);
    xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhttp.send();
    console.log("getFormDataFromServer  "+JSON.stringify(json));
    return json;
}

function renderForm(jso) {
	Object.entries(jso).forEach(x=>{
		// x[0]  input name
		// x[1]  input value
		document.querySelector("input[name='" + x[0] + "']").value = x[1];
	});
}

function endSessionOnCloseTab() {
	 var xhttp = new XMLHttpRequest();
	    xhttp.open("POST", "/ers/out", true);
	    xhttp.send();
}

function saveFormData(json, url) {
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhttp.send(json);
}

function serializeArray(form) {
    var objects = [];
    if (typeof form == 'object' && form.nodeName.toLowerCase() == "form") {
        var fields = form.getElementsByTagName("input");
        for (var i = 0; i < fields.length; i++) {
            objects[objects.length] = { name: fields[i].getAttribute("name"), value: fields[i].getAttribute("value") };
        }
    }
    return objects;
} 