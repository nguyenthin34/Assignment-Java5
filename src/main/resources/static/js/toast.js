var toastTrigger = document.getElementById("liveToastBtn");
var toastLiveExample = document.getElementById("liveToast");
function load() {
    var toast = new bootstrap.Toast(toastLiveExample);
    var today = new Date();
    var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
    document.getElementById("hvn").innerHTML = time;
    toast.show();
}
