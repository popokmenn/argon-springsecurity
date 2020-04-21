var roleList = [];
var idUserForDelete;

const searchFullname = document.getElementById('search-fullname');
const searchUsername = document.getElementById('search-username');
const searchRoles = document.getElementById('search-roles');
const searchAccess = document.getElementById('search-accessprivilege');

const inputUsername = document.getElementById('username');
const inputPassword = document.getElementById('password');
var pwInput = document.getElementById('pwDiv')

//suatu fungsi yang dijalankan ketika inputan username dan password diketik
const inputHandler = function (e) {
    if ($('#username').val().length > 4 && $('#password').val().length > 4) { //jika input password dan username length nya lebih dari 4 maka, button sign up sudah bisa di klik (disable = false)
        document.getElementById("btn-signup").disabled = false;
    } else {
        document.getElementById("btn-signup").disabled = true;
    }
}

const buildURLQuery = obj =>
    Object.entries(obj)
        .map(pair => pair.map(encodeURIComponent).join('='))
        .join('&');

const searchHandler = function () {
    var queryObj = {}
    if (searchFullname.value.length > 0) {
        queryObj.fullname = searchFullname.value

    }

    if (searchUsername.value.length > 0) {
        queryObj.username = searchUsername.value

    }

    if (searchRoles.value.length > 0) {
        queryObj.role = searchRoles.value

    }

    $("#table-user-body tr").remove();
    console.log("?" + buildURLQuery(queryObj))
    populateTable.getAllUsers("?" + buildURLQuery(queryObj))

    if (searchUsername.value.length > 3 || searchUsername.value.length > 3 || searchFullname.value.length > 3) {


    }
}

//dipanggil di attribut element button html
function addNewUser() {
    populateCombo.getAllRole({ id: 0, roles: "" })
    pwInput.style.display = 'inline'
    $("#user-form").closest('form').find("input[type=text], input[type=email], input[type=password]").val("");
    $('#modal-form').modal('show');
}

function deleteUser(idUser) {
    idUserForDelete = idUser
    $('#modal-notification').modal('show');
}

function reloadTabel() {
    $("#table-user-body tr").remove();
    populateTable.getAllUsers('');
}

function loadToForm(idUser) {
    pwInput.style.display = 'none' //untuk meng hide inputan password
    $.ajax({
        url: 'user/get/' + idUser,
        method: 'get',
        contentType: 'application/json',
        success: function (res, status, xhr) {
            $('#modal-form').modal('show');
            $("#idUser").val(res.id);
            $("#username:text").val(res.username);
            $("#fullname:text").val(res.fullname);
            $("#email").val(res.email);
            populateCombo.getAllRole(res);
            document.getElementById("btn-signup").disabled = false;
        },
        error: function (xhr) {
            console.log(JSON.parse(xhr.responseText));
        }
    });

}

function deleteUserOnBtnModal() {
    $.ajax({
        url: '/user/' + idUserForDelete,
        method: 'delete',
        contentType: 'application/json',
        success: function (res, status, xhr) {
            $('#modal-notification').modal('hide');
        },
        error: function (xhr) {
            console.log(JSON.parse(xhr.responseText));
        }
    });
}

// untuk menggenerate row table secara dinamis
function generateRow(userObj) {
    var outerRow = document.createElement('tr');

    // column for fullname
    var colFullname = document.createElement('th');
    colFullname.setAttribute('scope', 'row');
    var divFullname = document.createElement('div');
    divFullname.className = 'media align-items-center';
    var spanFullname = document.createElement('span');
    spanFullname.className = 'name mb-0 text-sm';
    spanFullname.textContent = userObj.fullname;
    divFullname.appendChild(spanFullname);
    colFullname.appendChild(divFullname);
    outerRow.appendChild(colFullname);

    //column for username 
    var colUsername = document.createElement('td');
    colUsername.textContent = userObj.username;
    outerRow.appendChild(colUsername);

    var roleArr = [];
    for (const key in userObj.roles) {
        roleArr.push(" " + userObj.roles[key].name)
    }

    //column for roles
    var colRoles = document.createElement('td');
    colRoles.textContent = roleArr
    outerRow.appendChild(colRoles);

    //column for branch access
    var colAccess = document.createElement('td');
    colAccess.textContent = "Cabang C"
    outerRow.appendChild(colAccess);

    //column for button
    var colBtn = document.createElement("td")
    colBtn.className = 'text-right'

    //btn sign user
    var btnSign = document.createElement('button');
    btnSign.setAttribute("data-toggle", "tooltip");
    btnSign.setAttribute("data-placement", "top");
    btnSign.setAttribute("title", "Sign and modify user");
    btnSign.setAttribute("type", "button");
    btnSign.setAttribute("onclick", "loadToForm(" + userObj.id + ")")
    btnSign.className = "btn btn-info btn-sm";
    var spanIconSign = document.createElement('span')
    spanIconSign.className = "btn-inner--icon";
    var iconSign = document.createElement('i')
    iconSign.className = "ni ni-briefcase-24"
    spanIconSign.appendChild(iconSign);
    btnSign.appendChild(spanIconSign);

    //button delete user
    var btnDelete = document.createElement('button');
    btnDelete.className = "btn btn-danger btn-sm";
    btnDelete.setAttribute("data-toggle", "tooltip");
    btnDelete.setAttribute("data-placement", "top");
    btnDelete.setAttribute("title", "Delete user")
    btnDelete.setAttribute("onclick", "deleteUser(" + userObj.id + ")")
    var spanIconDelete = document.createElement('span')
    spanIconDelete.className = "btn-inner--icon";
    var iconDelete = document.createElement('i')
    iconDelete.className = "ni ni-fat-delete"
    spanIconDelete.appendChild(iconDelete);
    btnDelete.appendChild(spanIconDelete);

    colBtn.appendChild(btnSign)
    colBtn.appendChild(btnDelete)
    outerRow.appendChild(colBtn)

    document.getElementById("table-user-body").appendChild(outerRow);
}


//buar get data yang ditampilin di tabel
var populateTable = {
    getAllUsers: function (searchQuery) {
        $.ajax({
            url: '/user' + searchQuery,
            method: 'get',
            contentType: 'application/json',
            success: function (res, status, xhr) {
                console.log('asdad')
                $("#table-user-body tr").remove();
                for (const key in res) {
                    generateRow(res[key]);
                }
            },
            error: function (xhr) {
                console.log(JSON.parse(xhr.responseText));
            }
        });
    }
}

// get role buat dropdown
var populateCombo = {
    getAllRole: function (obj) {
        $.ajax({
            url: '/user/role',
            method: 'get',
            contentType: 'application/json',
            success: function (res, status, xhr) {
                var dynamicSelect = document.getElementById("selectRole");
                $('#selectRole').find('option').remove();

                res.forEach(element => {
                    var newOption = document.createElement("option");
                    newOption.setAttribute("id", element.id);
                    newOption.text = element.name;
                    dynamicSelect.add(newOption);
                })

                if (obj.roles.length > 0) {
                    for (let index = 0; index < obj.roles.length; index++) {
                        var opt = document.getElementById(obj.roles[index].id);
                        opt.setAttribute('selected', 'selected')
                        roleList = obj.roles;
                    }
                }
                $("#selectRole").trigger("change");

            },
            error: function (xhr) {
                console.log(JSON.parse(xhr.responseText));
            }
        });
    }
}

// onClick button sign up
$('#btn-signup').click(function () {
    var user = {
        id: document.getElementById('idUser').value,
        email: document.getElementById('email').value,
        fullname: document.getElementById('fullname').value,
        password: document.getElementById('password').value,
        username: document.getElementById('username').value,
        roles: roleList
    }
    console.log(JSON.stringify(user))

    $.ajax({
        url: '/user/reg',
        method: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify(user),
        success: function (res, status, xhr) {
            console.log(res);
            $('#modal-form').modal('hide');
            $('#modal-success').modal('show');
        },
        error: function (xhr) {
            console.log(xhr.responseJSON);
            $('#modal-error').modal('show');
            document.getElementById("error-message").textContent = xhr.responseJSON.message
        }
    });
})

// document ready, fungsi yang dijalankan ketika page sudah siap atau page sudah terload seluruhnya (ready)
$(document).ready(function () {
    $('[data-toggle="tooltip"]').tooltip();
    reloadTabel()

    //default dari select2 nya
    $('#selectRole').select2({
        dropdownAutoWidth: true,
        multiple: true,
        placeholder: "Select",
        allowClear: true
    });

    // funsi yang dijalankan ketika dropdown select2 yang id nya SelectRole di pilih salah satu option nya
    $("#selectRole").on("select2:select select2:unselect", function (e) {
        //console.log(e)
        var items = $(this).val();
        var lastSelectedItem = e.params.data.id;

        roleList = []
        for (let index = 0; index < items.length; index++) {
            var role = {
                id: $("#selectRole").select2('data')[index].element.id,
                name: lastSelectedItem
            };
            roleList.push(role)
        }
        console.log(roleList)
    })

    // fundsi yang dijalankan ketika modal success di close, umumnya namanya onModalHide
    $('#modal-notification,#modal-form').on('hidden.bs.modal', function () {
        //karena gw pake tabel html biasa, maka untuk me refresh tabelnya pake 2 step. 1. delete semua row nya (destroy); 2. buat lagi tabel nya dari awal dengan data yg sudah ter update
        reloadTabel();
        $("#idUser").val(-99);
    })

    inputUsername.addEventListener('input', inputHandler);
    inputUsername.addEventListener('propertychange', inputHandler);
    inputPassword.addEventListener('input', inputHandler);
    inputPassword.addEventListener('propertychange', inputHandler);
    searchAccess.addEventListener('input', searchHandler);
    searchAccess.addEventListener('propertychange', searchHandler);
    searchFullname.addEventListener('input', searchHandler);
    searchFullname.addEventListener('propertychange', searchHandler);
    searchRoles.addEventListener('input', searchHandler);
    searchRoles.addEventListener('propertychange', searchHandler);
    searchUsername.addEventListener('input', searchHandler);
    searchUsername.addEventListener('propertychange', searchHandler);

});