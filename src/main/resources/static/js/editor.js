jQuery(document).ready(function ($) {


    $('#editor-payslip-all-search-form').submit(function (event) {
        enableSearchAllPayslipsButton(false);
        event.preventDefault();
        searchAllEmployeePayslipsAjax()
    });


    $('#editor-payslip-delete-form').submit(function (event) {
        enableDeletePaySlipButton(false);
        event.preventDefault();
        deletePaySlipAjax();
    });


    function enableSearchAllPayslipsButton(flag) {
        $('#getAllPayslips').prop("disabled", flag);
    }

    function enableDeletePaySlipButton(flag) {
        $('#editor-payslip-delete-btn').prop("disabled", flag);
    }


    function displayToTableAllPayslips(data) {
        $('tr:has(td)').remove();
        $.each(data, function (i, item) {
            $('<tr>').html("<td>" + data[i].id + "</td><td>" + data[i].year + "</td><td>" + data[i].month + "</td><td>"
                + data[i].advance + "</td><td>" + data[i].salary + "</td>")
                .appendTo('#editor-payslip-all');
        });
    }

    function searchAllEmployeePayslipsAjax() {
        $.ajax({
            type: "GET",
            url: "/editor/payslip/all",
            timeout: 100000,
            success: function (data) {
                console.log("SUCCESS: ", data);
                displayToTableAllPayslips(data);
            },
            error: function (e) {
                console.log("ERROR: ", e);
            },
            done: function () {
                console.log("DONE");
                enableSearchAllPayslipsButton(true);
            }
        });
    }


    function deletePaySlipAjax() {
        $.ajax({
            type: "DELETE",
            url: "/editor/payslip/" + document.getElementById('editor-payslip-delete-id').value + "/delete",
            timeout: 100000,
            success: function (data) {
                console.log("SUCCESS: ", data);
                window.location = "/editor.html";
            },
            error: function (e) {
                $('#editor-payslip-delete-btn').parent().append('<p>Расчетный лист не найден!</p>');
                console.log("ERROR: ", e);
                event.preventDefault();
            },
            done: function () {
                console.log("DONE");
                enableDeletePaySlipButton(true);
            }
        });
    }

    $("#editor-payslip-add-form").submit(function (event) {
        enableAddNewPaySlipButton(false);
        var year = $("#editor-payslip-add-year").val();
        var month = $("#editor-payslip-add-month").val();
        var advance = $("#editor-payslip-add-advance").val();
        var salary = $("#editor-payslip-add-salary").val();
        var paySlipDto = {
            year: year,
            month: month,
            advance: advance,
            salary: salary
        };
        var resultJson = JSON.stringify(paySlipDto);
        if (year !== " " && month !== "" && advance !== "" && salary !== "") {
            addPaySlipDataAjax(resultJson);
            event.preventDefault();
        } else {
            alert("Заполните, пожалуйста, все поля!");
            event.preventDefault();
        }
    });


    function enableAddNewPaySlipButton(flag) {
        $('#editor-payslip-add-btn').prop("disabled", flag);
    }


    function addPaySlipDataAjax(json) {
        $.ajax({
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            type: "POST",
            url: "/editor/payslip/add",
            data: json,
            timeout: 100000,
            success: function () {
                console.log("SUCCESS: ", "ok222");
                window.location = "http://localhost:9090/editor.html";
            },
            error: function () {
                $('#editor-payslip-add-btn').parent().append('<p>Расчетный лист за этот период уже существует!</p>');
                console.log("ERROR: ");
                event.preventDefault();
            },
            done: function (e) {
                console.log("DONE");
                enableAddNewUserButton(true);
            }
        });
    }
})


