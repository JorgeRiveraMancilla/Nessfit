const modal = document.getElementById("modal");
const btn = document.getElementById("button-confirm");
const cancelBtn = document.getElementById("button-cancel");

btn.onclick = function() {
    modal.style.display = "flex";
}
cancelBtn.onclick = function() {
    modal.style.display = "none";
}