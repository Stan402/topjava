var ajaxUrl = "ajax/admin/users/";
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();
    checkboxSetup();
});

function updateTable() {
    $.get(ajaxUrl, function (data) {
        datatableApi.clear().rows.add(data).draw();
    });
}

function checkboxSetup() {
    $(document).ready(function () {
        $('input[type="checkbox"]').change(function () {
            setUserStatus($(this).closest('tr').attr("id"), $(this).is(":checked"))
        });
    });

}

function setUserStatus(id, enabled) {
    $.post(ajaxUrl + id, {enabled: enabled}, function () {
        updateTable();
        successNoty("Status changed");
    });
}