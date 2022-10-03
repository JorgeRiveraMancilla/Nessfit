var modal = document.getElementById("modal");
var btn = document.getElementById("button-confirm");
var cancelBtn = document.getElementById("button-cancel");

btn.onclick = function() {
    modal.style.display = "flex";
}
cancelBtn.onclick = function() {
    modal.style.display = "none";
}