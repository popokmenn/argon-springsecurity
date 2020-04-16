

const inputUsername = document.getElementById('username');
const inputPassword = document.getElementById('password');
var btnSignIn = document.getElementById("btn-signin");

const inputHandler = function (e) {

    if ($('#username').val().length > 4 && $('#password').val().length > 4) {
        document.getElementById("btn-login").disabled = false;
    } else {
        document.getElementById("btn-login").disabled = true;
    }
}

$('#btn-loginn').click(function () {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    // MD5 encrypt
    var encrptedText = CryptoJS.MD5(password)

    $.ajax({
        url: '/user/login?password=' + encrptedText.toString().toUpperCase() + '&username=' + username,
        method: 'post',
        contentType: 'application/json',
        success: function (res, status, xhr) {
            if (res) {
                window.open("/", "_self")
            }
        },
        error: function (xhr, status, error) {
            console.log(JSON.parse(xhr.responseText));
        }
    });
})

$(document).ready(function () {
    inputUsername.addEventListener('input', inputHandler);
    inputUsername.addEventListener('propertychange', inputHandler);
    inputPassword.addEventListener('input', inputHandler);
    inputPassword.addEventListener('propertychange', inputHandler);
});