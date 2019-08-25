function tableLoad(table, url) {
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let json = JSON.parse(xhttp.responseText);
            table.updateOrAddData(json);
            table.redraw(true);
            pdfBtn.disabled = false;
            csvBtn.disabled = false;
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
	
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
//            let jso = JSON.parse(xhttp.responseText);
            let json = xhttp.responseText;
            console.log(json);
            sessionStorage.setItem('editFormObject', json);
            renderForm(json);
        }
    };
    
    xhttp.open("GET", "http://localhost:8081/ers"+uri, true);
    xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhttp.send();
    console.log("getFormDataFromServer  "+JSON.stringify(json));
    return json;
}

function renderForm(json) {
	let jso = JSON.parse(json);
	Object.entries(jso).forEach(x=>{
		// x[0]  input name
		// x[1]  input value
		document.querySelector("input[name='" + x[0] + "']").value = x[1];
	});
}

function endSessionOnCloseTab() {
	console.log('session close')
	 var xhttp = new XMLHttpRequest();
	 sessionStorage.clear();
	 xhttp.open("POST", "/ers/out", true);
	 xhttp.send();
}

function inputValueExists(url) {
	let xhttp = new XMLHttpRequest();
	
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let text = xhttp.responseText;
            console.log(text);
            fieldExists(text);
            //sessionStorage.setItem('fieldValue', text);
        }
    };
    
    xhttp.open("GET", url, true);
    xhttp.setRequestHeader("Content-Type", "text/plain;charset=UTF-8");
    xhttp.send();
}

function saveFormDataWithFile(formdata) {
	var xhttp = new XMLHttpRequest();       
	xhttp.open("POST","/ers/employee/req", true);
	xhttp.send(formdata);
}

function saveFormData(jso, url) {
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhttp.send(jso);
    
    sessionStorage.setItem('editFormObject', JSON.stringify(jso));
    renderForm(jso);
}

//function serializeArray(form) {
//    var objects = [];
//    if (typeof form == 'object' && form.nodeName.toLowerCase() == "form") {
//        var fields = form.getElementsByTagName("input");
//        for (var i = 0; i < fields.length; i++) {
//            objects[objects.length] = { name: fields[i].getAttribute("name"), value: fields[i].getAttribute("value") };
//        }
//    }
//    return objects;
//} 