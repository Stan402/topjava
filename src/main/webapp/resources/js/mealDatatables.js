var ajaxUrl = "ajax/profile/meals/";
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
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
                "desc"
            ]
        ]
    });
    makeEditable();
});

function updateTable() {
    var startTime = $("#startTime").val();
    var endTime = $("#endTime").val();
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    $.get(ajaxUrl + "filter", {
        startTime: startTime,
        startDate: startDate,
        endTime: endTime,
        endDate: endDate
    }, function (data) {
        datatableApi.clear().rows.add(data).draw();
    });
}

function clearFilter() {
    $("#startTime").val("");
    $("#endTime").val("");
    $("#startDate").val("");
    $("#endDate").val("");
    updateTable();
}