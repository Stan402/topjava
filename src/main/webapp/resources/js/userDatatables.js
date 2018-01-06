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
    $.get(ajaxUrl, function(data){showData(data)});
}

function checkboxSetup() {
    $(document).ready(function () {
        $('input[type="checkbox"]').change(function () {
            var row = $(this).closest('tr');
            var enabled = $(this).is(":checked");
            $.post(ajaxUrl + row.attr("id"), {enabled: enabled}, function () {
                enabled ? row.removeClass('disabled') : row.addClass('disabled');
                successNoty("Status changed");
            });
        });
    });

}
