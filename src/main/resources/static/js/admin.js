addUserTable();

function addUserTable() {
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
            $.each(user.authorities, function (index, role) {
                content += '<a>' + role + '</a><br/>';
            });
            content += '</td>'
            content += '<td class="text-center"><a class="btn-edit btn-outline-info"  id="editButtonE' + user.id + '"   role="button" data-toggle="modal" data-target="#showModal">Edit</a></td>';
            content += '<td class="text-center"><a class="btn-edit btn-outline-danger" id="editButtonD' + user.id + '" role="button" data-toggle="modal" data-target="#showModal">Delete</a></td>';
            content += '</tr>';
        });
        $('#userTable').append(content);
    });


}

$('document').ready(function () {

    $("#showModal").on('show.bs.modal', function (event) {
        let button = event.relatedTarget;
        if (button != null) {
            let userId = button.id.substring(11);
            let method = button.text;

            const href = "/admin/user/" + userId;
            $.get(href, function (data) {
                $('#idEdit').val(data.id);
                $('#nameEdit').val(data.name);
                $('#surnameEdit').val(data.surname);
                $('#emailEdit').val(data.email);
                $('#ageEdit').val(data.age);
                $('#passwordEdit').val(data.password);
                $('#roleEdit').val(data.authorities);
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
        console.log(user);


        let userAdd = {
            name : $("#addName").val(),
            surname : $("#addSurname").val(),
            email : $("#addEmail").val(),
            age : $("#addAge").val(),
            password : $("#addPassword").val(),
            roles :  'USER'
        };

        userAdd  = JSON.stringify( userAdd  )
        // let userAdd = "{ name: \"" + $("#addName").val() +
        //     "\", surname: \"" + $("#addSurname").val() +
        //     "\", email: \"" + $("#addEmail").val() +
        //     "\", age: \"" + $("#addAge").val() +
        //     "\", password: \"" + $("#addPassword").val() +
        //     "\", roles:[" + $("#add-role-select").val()+"]}";

        // console.log(userAdd);
        $.ajax({
            type: 'POST',
            url: '/admin/save',
            data: userAdd,
            contentType: 'application/json; charset=utf-8',
            success: function () {
                clearAddForm();
                addUserTable();
            }
        })
    });

function clearAddForm(){
    $('#addForm').find('input, select').each(function () {
        $(this).val("");
    });
}
})