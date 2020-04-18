

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
    //var encrptedText = CryptoJS.MD5(password)

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

function autoAddRole() {
    $.ajax({
        url: '/user/auto-add-role',
        method: 'get',
        contentType: 'application/json',
        success: function (res, status, xhr) {
            console.log("Role added succesfully")
        },
        error: function (xhr, status, error) {
            console.log(JSON.parse(xhr.responseText));
        }
    });
}

function checkIsErrorTrue() {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const isLoginError = urlParams.get('error')
    if (isLoginError) {
        console.log(isLoginError);
        $('#modal-error').modal('show');
    } else {
        console.log("asdsadas");
    }
}

function modalUtil() {
    $('#modal-error').modal('hide');
    window.history.pushState({}, document.title, "/" + "login");
}

$('#modal-error').on('hidden.bs.modal', function () {
    $('#modal-error').modal('hide');
    window.history.pushState({}, document.title, "/" + "login");
})

function loadToForm() {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    const passwordParam = urlParams.get('password')
    var decrypted = CryptoJS.AES.decrypt(passwordParam, "Secret Passphrase");
    console.log(CryptoJS.enc.Utf8.stringify(decrypted));
    document.getElementById("password").value = CryptoJS.enc.Utf8.stringify(decrypted);
}

$(document).ready(function () {
    //autoAddRole();
    checkIsErrorTrue();
    loadToForm();
    inputUsername.addEventListener('input', inputHandler);
    inputUsername.addEventListener('propertychange', inputHandler);
    inputPassword.addEventListener('input', inputHandler);
    inputPassword.addEventListener('propertychange', inputHandler);
});