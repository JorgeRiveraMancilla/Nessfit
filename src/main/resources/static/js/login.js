var btn = document.getElementById("login");

var modal = document.getElementById("modal");
var span = document.getElementsByClassName("modal-close")[0];

var alert = document.getElementById("alert");
var span_alert = document.getElementsByClassName("alert-close")[0];

// When the user clicks the button, open the modal
btn.onclick = function() {
    modal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
}

// When the user click on <span> (x), close the alert
span_alert.onclick = function() {
    alert.style.display = "none";
}

// When the user clicks anywhere outside the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}