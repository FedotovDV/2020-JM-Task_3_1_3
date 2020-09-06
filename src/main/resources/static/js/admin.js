addUserTable();

function addUserTable() {
    $.getJSON("http://localhost:8080/admin/list", function (data) {
        let content = '';
        let userId = '';

        $.each(data, function (index, user) {
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

    //
    // $("#showModal").on('show.bs.modal', function (event) {
    //     let userId = $(event.relatedTarget).data('user-id');
    //     let method = $(event.relatedTarget).data('method');
    //     const href = "/admin/" + method + "?id=" + userId;
    //     $.get(href, function (data) {
    //         $('#showModal').empty().html(data);
    //         // $('#showModal').html(data);
    //     });
    // });


    // $("#v-pills-users-tab").on('click', function () {
    //     addUserTable();
    // });

    // $("#userTable .btn-edit").on(
    //     'click', function (event) {
    // $(document).on("click", ".btn-edit", function (event) {
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


    // $("#saveButton").on(
    //     'click', function () {
    //         console.log('click-click');
    //         $('#showModal').modal('hide');
    //     });

    // $("#v-pills-users-tab").on('click', function () {
    //     const href = "/admin/role";
    //     $.get(href, function (data) {
    //         console.log(data);
    //
    //     });
    // });
    //
    // $("#v-pills-newuser-tab").on('click', function () {
    //     const href = "/admin/all";
    //     $.get(href, function (data) {
    //         console.log(data);
    //
    //     });
    // });


})