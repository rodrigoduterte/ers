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

function resolvePending(set,uri) {
    var xhttp = new XMLHttpRequest();
	 sessionStorage.clear();
     xhttp.open("POST", uri, true);
     xhttp.setRequestHeader("Content-type", "application/json");
	 xhttp.send(  JSON.stringify(  Array.from( set.values() )  )  );
}

function tableLoadBytes(table, uri, buttons) {
	var xhttp = new XMLHttpRequest();

	xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
        	let msgpjo = msgpack.decode(new Uint8Array(xhttp.response));
        	console.log(JSON.stringify(msgpjo))
            	table.updateOrAddData(msgpjo);
            	table.setFilter("author", "!=", null);
            	table.redraw(true);
            buttons.forEach((cv, idx, arr)=> {
            	arr[idx].disabled = false;
            })
        }
    };

    xhttp.open("GET", uri, true);
    xhttp.responseType = "arraybuffer";
    xhttp.send();
}

function tableLoad(table, uri, buttons) {
	if (uri.includes("type=m")) {
		tableLoadBytes(table, uri, buttons)
	} else {
		var xhttp = new XMLHttpRequest();
	    console.log(uri);
	    xhttp.onreadystatechange = function () {
	        if (this.readyState == 4 && this.status == 200) {
	            let json = JSON.parse(xhttp.responseText);
	            table.updateOrAddData(json);
	            table.redraw(true);
	            buttons.forEach((cv, idx, arr)=> {
	            	arr[idx].disabled = false;
	            })
	        }
	    };
	    xhttp.open("GET", uri, true);
	    xhttp.send();
	}
}

//done
function getFormDataFromServer(uri) {
	let json = {};
	let xhttp = new XMLHttpRequest();
	
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let json = xhttp.responseText;
            sessionStorage.setItem('editFormObject', json);
            renderForm(json);
        }
    };
    xhttp.open("GET", uri, true);
    xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhttp.send();
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
	xhttp.open("POST","/ers/reqg", true);
	xhttp.send(formdata);
}

function saveFormData(jso, uri) {
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", uri, true);
    xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhttp.send(jso);
    
    sessionStorage.setItem('editFormObject', JSON.stringify(jso));
    renderForm(jso);
}