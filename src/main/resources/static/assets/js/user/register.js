var populateCombo = {
    getAllRole: function (idProvinsi, idKota) {
        $.ajax({
            url: '/user/role',
            method: 'get',
            contentType: 'application/json',
            success: function (res, status, xhr) {
                if (res.length > 0) {
                    console.log(res);
                    var dynamicSelect = document.getElementById("selectRole");
                    $('#selectRole').find('option').remove();
                    var firstOption = document.createElement("option");
                    firstOption.setAttribute("id", 0);
                    firstOption.text = "Role";
                    //dynamicSelect.add(firstOption);

                    res.forEach(element => {
                        var newOption = document.createElement("option");
                        newOption.setAttribute("id", element.id);
                        newOption.text = element.name;
                        dynamicSelect.add(newOption);
                    })
                } else {
                    autoAddRole();
                }
            },
            error: function (xhr) {
                console.log(JSON.parse(xhr.responseText));
            }
        });
    }
}

$('#btnRegister').click(function () {
    var biodata = {
        fullname: document.getElementById('register-fullname').value
    }

    var userRoleList = [];
    var userRole = {
        roleId: $("#selectRole option:selected").attr('id')
    }
    var userRoleId = {
        id: userRole
    }
    userRoleList.push(userRoleId);

    var user = {
        username: document.getElementById('register-username').value,
        email: document.getElementById('register-email').value,
        password: document.getElementById('register-password').value,
        userRole: userRoleList,
        biodata: biodata
    }
    console.log(JSON.stringify(user));

    $.ajax({
        url: '/user/reg',
        method: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(user),
        success: function (res, status, xhr) {
            console.log(res);
        },
        error: function (xhr) {
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
            location.reload();
            console.log("Role added succesfully")
        },
        error: function (xhr, status, error) {
            console.log(JSON.parse(xhr.responseText));
        }
    });
}

$(document).ready(function () {
    populateCombo.getAllRole();

    var password = document.getElementById("register-password");
    password.addEventListener('keyup', function () {
        var pwd = password.value

        // Reset if password length is zero
        if (pwd.length === 0) {
            document.getElementById("password-progressbar").setAttribute("style", "width:" + 0 + "%");
            document.getElementById("strength-progress").innerHTML = '0%';
            document.getElementById("strength-progresslabel").innerHTML = '\"I am a Chew Toy.\"';
            return;
        }

        // Check progress
        var prog = [/[$@$!%*#?&]/, /[A-Z]/, /[0-9]/, /[a-z]/].reduce((memo, test) => memo + test.test(pwd), 0);

        // Length must be at least 8 chars
        if (prog > 2 && pwd.length > 7) {
            prog++;
        }

        // Display it
        var color = "";
        var label = "";
        var strength = "";
        switch (prog) {
            case 0:
                label = '\"Baby poo\"';
                strength = "5%";
                color = "#fe0000";
                break;
            case 1:
                label = '\"I am a Chew Toy.\"'
                strength = "15%";
                color = "#fe0000";
                break;
            case 2:
                label = '\"Not so easy\"';
                strength = "25%";
                color = "#a18648";
                break;
            case 3:
                label = '\"High Octane\"';
                strength = "50%";
                color = "#028a03";
                break;
            case 4:
                label = '\"Impossible\"';
                strength = "75%";
                color = "#028a03";
                break;
            case 5:
                label = '\"Lunatic: Pray for IT data security\"';
                strength = "100%";
                color = "#000000";
                break;
        }
        document.getElementById("strength-progresslabel").setAttribute("style", "color: " + color);
        document.getElementById("strength-progresslabel").innerHTML = label;
        document.getElementById("strength-progress").innerHTML = strength;
        document.getElementById("password-progressbar").setAttribute("style", "width:" + strength);
    });
});