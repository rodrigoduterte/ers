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


function getFormData(json, url) {
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhttp.send(JSON.stringify(json));
}

function setFormData(formData, url) {
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", url, true);
    xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhttp.send(formData);
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