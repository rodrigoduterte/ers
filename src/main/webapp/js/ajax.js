function preLoad(url) {
	var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let json = JSON.parse(xhttp.response);
            sessionStorage.setItem('managerTables', JSON.stringify(json));
        }
    };
    xhttp.open("GET", url, true);
    xhttp.send();
}

function resolvePending(set,url) {
    var xhttp = new XMLHttpRequest();
	 sessionStorage.clear();
     xhttp.open("POST", "http://localhost:8081/ers" + url, true);
     xhttp.setRequestHeader("Content-type", "application/json");
	 xhttp.send(  JSON.stringify(  Array.from( set.values() )  )  );
}

function tableLoadBytes(table, url, buttons) {
	var xhttp = new XMLHttpRequest();

	xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            //let msgpjo = msgpack.decode( new Uint8Array(xhttp.response));
            
        	let msgpjo = msgpack.decode(new Uint8Array(xhttp.response));
            console.log(msgpjo);
            	table.setData(msgpjo);
            	table.redraw(true);
            buttons.forEach((cv, idx, arr)=> {
            	arr[idx].disabled = false;
            })
        }
    };
    xhttp.open("GET", "http://localhost:8081/ers"+url, true);
    xhttp.responseType = "arraybuffer";
    xhttp.send();
}


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
	 var xhttp = new XMLHttpRequest();
	 sessionStorage.clear();
	 xhttp.open("POST", "/ers/out", true);
	 xhttp.send();
}

function inputValueExists(url, validating) {
	let xhttp = new XMLHttpRequest();
	
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let text = xhttp.responseText;
            fieldExists(text, validating);
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