setTimeout(function () {
  var successAlert = document.getElementById("successAlert");
  var errorAlert = document.getElementById("errorAlert");

  if (successAlert) {
    successAlert.style.display = "none";
  }
  if (errorAlert) {
    errorAlert.style.display = "none";
  }
}, 2000);
