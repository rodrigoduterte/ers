/// form operations
let editForm = document.forms['edit-form'];
let requestForm = document.forms['request-form'];
let editBtn = document.getElementById('edit-form-button');
let requestBtn = document.getElementById('request-form-button');

// edit form
editBtn.addEventListener('click', function() {
    if(editForm.reportValidity()) {
        /// send form json to server via ajax
        let json = JSON.stringify( Object.fromEntries( new FormData(editForm) ) );
        //setFormData(json, "http://localhost:8081/ers/user/info");
        console.log(json);
        $('#infoModal').modal('hide')
    } else {
        console.log('Form not valid')
    }
})

editForm.addEventListener("submit", function (ev) {
    // fire this to prevent page from refreshing
    ev.preventDefault();
})

// request form
requestBtn.addEventListener('click', function() {
    if(requestForm.reportValidity()) {
        /// send form json to server via ajax
        let json = JSON.stringify( Object.fromEntries( new FormData(requestForm) ) );
        //setFormData(json, "http://localhost:8081/ers/user/info");
        console.log(json);
        $('#requestModal').modal('hide')
    } else {
        console.log('Form not valid')
    }
})

requestForm.addEventListener('submit', function (ev) {
    // fire this to prevent page from refreshing
    ev.preventDefault();
})

/// input operations
let dateRequest = document.getElementById('date-request');
let today = new Date();

dateRequest.setAttribute("placeholder", today.getMonth() + "/"
	+ today.getDate() + "/" + today.getFullYear())