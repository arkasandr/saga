jQuery(document).ready(function ($) {


    $('#account-payslip-all-search-form').submit(function (event) {
        enableSearchAllPayslipsButton(false);
        event.preventDefault();
        searchAllEmployeePayslipsAjax()
    });


    $('#account-payslip-delete-form').submit(function (event) {
        enableDeletePaySlipButton(false);
        event.preventDefault();
        deletePaySlipAjax();
    });


    function enableSearchAllPayslipsButton(flag) {
        $('#getAllPayslips').prop("disabled", flag);
    }

    function enableDeletePaySlipButton(flag) {
        $('#account-payslip-delete-btn').prop("disabled", flag);
    }


    function displayToTableAllPayslips(data) {
        $('tr:has(td)').remove();
        $.each(data, function (i, item) {
            $('<tr>').html("<td>" + data[i].id + "</td><td>"+ data[i].year + "</td><td>" + data[i].month + "</td><td>"
                + data[i].advance + "</td><td>" + data[i].salary + "</td>")
                .appendTo('#account-payslip-all');
        });
    }

    function searchAllEmployeePayslipsAjax() {
        $.ajax({
            type: "GET",
            url: "/account/payslip/all",
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
            url: "/account/payslip/" + document.getElementById('account-payslip-delete-id').value +"/delete",
            timeout: 100000,
            success: function (data) {
                console.log("SUCCESS: ", data);
                window.location = "/account.html";
            },
            error: function (e) {
                $('#account-payslip-delete-btn').parent().append('<p>Расчетный лист не найден!</p>');
                console.log("ERROR: ", e);
                event.preventDefault();
            },
            done: function () {
                console.log("DONE");
                enableDeletePaySlipButton(true);
            }
        });
    }

    $("#account-payslip-add-form").submit(function (event) {
        enableAddNewPaySlipButton(false);
        var year = $("#account-payslip-add-year").val();
        var month = $("#account-payslip-add-month").val();
        var advance = $("#account-payslip-add-advance").val();
        var salary = $("#account-payslip-add-salary").val();
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
        $('#account-payslip-add-btn').prop("disabled", flag);
    }


    function addPaySlipDataAjax(json) {
        $.ajax({
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            type: "POST",
            url: "/account/payslip/add",
            data: json,
            timeout: 100000,
            success: function () {
                console.log("SUCCESS: ", "ok222");
                window.location = "http://localhost:9090/account.html";
            },
            error: function () {
                $('#account-payslip-add-btn').parent().append('<p>Расчетный лист за этот период уже существует!</p>');
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


