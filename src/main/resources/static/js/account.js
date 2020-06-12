jQuery(document).ready(function ($) {


    $('#account-chart-all-search-form').submit(function (event) {
        enableSearchAllPayslipsButton(false);
        event.preventDefault();
        searchAllEmployeePayslipsAjax()
    });


    function enableSearchAllPayslipsButton(flag) {
        $('#getAllPayslipsChart').prop("disabled", flag);
    }


    function searchAllEmployeePayslipsAjax() {
        $.ajax({
            type: "GET",
            url: "/account/chart/all",
            contentType: "blob",
            timeout: 100000,
            success: function (data) {
                console.log("SUCCESS: ", data);
                $("#account-chart-display").html('<img src="data:image/png;base64,' + data + '" />');
            },
            error: function (e) {
                console.log("ERROR: ");
            },
            done: function () {
                console.log("DONE");
                enableSearchAllPayslipsButton(true);
            }
        });
    }

})
