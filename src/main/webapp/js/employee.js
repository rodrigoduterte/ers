let dateRequest = document.getElementById('date-request');
let today = new Date();

dateRequest.setAttribute("placeholder", today.getMonth() + "/" + today.getDate() + "/" + today.getFullYear()) 