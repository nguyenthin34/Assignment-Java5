var password = document.getElementById("password"),
    confirm_password = document.getElementById("confirmPassword");
var username = document.getElementById("username");
document.getElementById("signupLogo").src =
    "https://s3-us-west-2.amazonaws.com/shipsy-public-assets/shipsy/SHIPSY_LOGO_BIRD_BLUE.png";

function checkvalue() {
    if (username.value == null) {
        confirm_password.setCustomValidity("No Null");
        return false;
    }
}
function validatePassword() {
    if (password.value != confirm_password.value) {
        confirm_password.setCustomValidity("Passwords Don't Match");
        return false;
    } else {
        confirm_password.setCustomValidity("");
        return true;
    }
}
