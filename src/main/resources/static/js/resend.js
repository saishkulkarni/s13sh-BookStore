document.addEventListener("DOMContentLoaded", function () {
  var seconds = 30;
  var timerElement = document.getElementById("timer");
  var resendButton = document.getElementById("resendButton");
  var timerContainer = document.getElementById("timer-container");

  function updateTimer() {
    timerElement.innerText = "Resend in " + seconds + " seconds";
    seconds--;

    if (seconds < 0) {
      clearInterval(timerInterval);
      timerContainer.classList.add("hidden");
      resendButton.classList.remove("hidden");
    }
  }

  var timerInterval = setInterval(updateTimer, 1000);

  setTimeout(function () {
    timerContainer.classList.remove("hidden");
  }, 30000);
});

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
