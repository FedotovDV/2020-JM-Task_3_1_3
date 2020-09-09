addUserTable();

function addUserTable() {

    console.log("addUserTable()");
    $.get("http://localhost:8080/admin/list", function (data) {
        let content = '';
        let userId = '';

        $.each(data, function (index, user) {
            console.log(user);
            userId = user.id;
            content += '<tr>';
            content += '<td class="text-center">' + user.id + '</td>>';
            content += '<td class="text-center">' + user.name + '</td>>';
            content += '<td class="text-center">' + user.surname + '</td>>';
            content += '<td class="text-center">' + user.age + '</td>>';
            content += '<td class="text-center">' + user.email + '</td>>';
            content += '<td class="text-center">'
            $.each(user.roles, function (index, role) {
                content += '<a>' + role + '</a><br/>';
            });
            content += '</td>'
            content += '<td class="text-center"><a class="btn-edit btn-outline-info"  id="editButtonE' + user.id + '"   role="button" data-toggle="modal" data-target="#showModal">Edit</a></td>';
            content += '<td class="text-center"><a class="btn-edit btn-outline-danger" id="editButtonD' + user.id + '" role="button" data-toggle="modal" data-target="#showModal">Delete</a></td>';
            content += '</tr>';
        });

        $('#userTable').empty().append(content);
    });


}

function clearAddForm() {
    console.log("clearAddForm(");
    $('#addForm').find('input, select').each(function () {
        $(this).val("");
    });
}

function clearModalForm() {
    console.log("clearModalForm()");
    $('#idEdit').val('');
    $('#nameEdit').val('');
    $('#surnameEdit').val('');
    $('#emailEdit').val('');
    $('#ageEdit').val('');
    $('#passwordEdit').val('');
    $('#roleEdit').val('');
}


$('document').ready(function () {

    $("#showModal").on('show.bs.modal', function (event) {

        console.log("#showModal");
        let button = event.relatedTarget;
        if (button != null) {
            let userId = button.id.substring(11);
            let method = button.text;
            clearModalForm();
            const href = "/admin/user/" + userId;
            $.get(href, function (data) {
                $('#idEdit').val(data.id);
                $('#nameEdit').val(data.name);
                $('#surnameEdit').val(data.surname);
                $('#emailEdit').val(data.email);
                $('#ageEdit').val(data.age);
                $('#passwordEdit').val(data.password);
                $('#roleEdit').val(data.roles);
                $('#edit-button').text(method);
                $('#showModal').modal('show');
            });
        }
    });

    $('#addButton').on('click', function (event) {
        event.preventDefault();
        let user = {};
        $('#addForm').find('input, select').each(function () {
            user[this.name] = $(this).val();
        });
        userAdd = JSON.stringify(user);
        $.ajax({
            type: 'POST',
            url: '/admin/save',
            data: userAdd,
            contentType: 'application/json; charset=utf-8',
            success: function () {
                $('#pills-users-tab, #pills-users').addClass('active');
                $('#pills-users').addClass('show');
                $('#pills-newuser-tab, #pills-newuser').removeClass('active');
                $('#pills-newuser').removeClass('show');
                clearAddForm();
                addUserTable();
            }
        })
    });

    $("body").on("click", "#edit-button", function (event) {
    // $('#edit-button').on('click', function (event) {
        event.preventDefault();
        let url = "";
        let method = $(this).text();
        if (method == 'Delete') {
            console.log('Delete');
            url = '/admin/delete/' + $('#idEdit').val();
            method = "DELETE";
            $.ajax({
                type: method,
                url: url,
                success: function () {
                    $('#showModal').modal('hide');
                    clearModalForm();
                    addUserTable();
                }
            })
        } else {
            console.log('Edit');
            let userEdit = {
                id: $('#idEdit').val(),
                name: $("#nameEdit").val(),
                surname: $("#surnameEdit").val(),
                email: $("#emailEdit").val(),
                age: $("#ageEdit").val(),
                password: $("#passwordEdit").val(),
                roles: $("#roleEdit").val()
            };
            userEdit = JSON.stringify(userEdit);
            url = '/admin/update/' + $('#idEdit').val();
            method = "PUT";
            $.ajax({
                type: method,
                url: url,
                data: userEdit,
                contentType: 'application/json; charset=utf-8',
                success: function () {
                    $('#showModal').modal('hide');
                    clearModalForm();
                    addUserTable();
                }
            })
        }

    });

})

