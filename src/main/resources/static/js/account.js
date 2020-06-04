jQuery(document).ready(function ($) {


    $('#account-payslip-all-search-form').submit(function (event) {
        enableSearchAllPayslipsButton(false);
        event.preventDefault();
        searchAllEmployeePayslipsAjax()
    });


    function enableSearchAllPayslipsButton(flag) {
        $('#getAllPayslips').prop("disabled", flag);
    }

    function displayToTableAllPayslips(data) {
        $('tr:has(td)').remove();
        $.each(data, function (i, item) {
            $('<tr>').html("<td>" + data[i].year + "</td><td>" + data[i].month + "</td><td>" + data[i].advance + "</td><td>" + data[i].salary + "</td>")
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
            done: function (e) {
                console.log("DONE");
                enableSearchAllPayslipsButton(true);
            }
        });
    }
})