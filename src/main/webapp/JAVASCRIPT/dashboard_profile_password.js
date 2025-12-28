/**
 * 
 */
function passValidate() {
    var newPassword = document.getElementById("newPass").value;
    var confirmPassword = document.getElementById("confirmPass").value;
    var passwordError = document.getElementById("passwordError");
    passwordError.innerHTML = "";

    if (newPassword.length > 15) {
        passwordError.innerHTML = "Password should not exceed 15 characters.";
    }else if (newPassword.length < 8) {
        passwordError.innerHTML = "Password should be atleast 8 characters.";
    } else if (newPassword.includes(" ")) {
        passwordError.innerHTML = "Password should not contain spaces.";
    }
}

function validate() {
    var newUserId = document.getElementById("newUser").value;
    var userIdError = document.getElementById("userError");
    var userButton = document.getElementById("formBtnUser");
    userIdError.innerHTML = "";

    if (newUserId.length > 10) {
        userIdError.innerHTML = "Username should not exceed 10 characters.";
        userButton.disabled = true;
    } else if (newUserId.length < 6) {
        userIdError.innerHTML = "Username should be atleast 6 characters.";
        userButton.disabled = true;
    } else if (newUserId.includes(" ")) {
        userIdError.innerHTML = "Username should not contain spaces.";
        userButton.disabled = true;
    } else {
        userButton.disabled = false;
    }
}



function passCheck() {
    var newPassword = document.getElementById("newPass").value;
    var confirmPassword = document.getElementById("confirmPass").value;
    var passwordError2 = document.getElementById("passwordError2");
    var updateButton = document.getElementById("formBtnPass");
    passwordError2.innerHTML = "";

    if (newPassword !== confirmPassword) {
        passwordError2.innerHTML = "Passwords do not match.";
        passwordError2.style.color = 'red';
        
    } else {
        
        passwordError2.innerHTML = "Passwords matched.";
        passwordError2.style.color = 'green';
    }
    
    if (newPassword.length >= 8 && newPassword.length <= 15) {
        updateButton.disabled = false;
    } else {
        updateButton.disabled = true;
    }
}

var newPassInput = document.getElementById("newPass");
newPassInput.addEventListener("input", passValidate);

var newUserInput = document.getElementById("newUser");
newUserInput.addEventListener("input", idValidate);
