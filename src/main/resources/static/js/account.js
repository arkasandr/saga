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
            timeout: 100000,
            success: function (data) {
                console.log("SUCCESS: ", data);
        //        $("account__chart__display").html("<img src="C:/Projects/saga/src/main/resources/png/01.png""/>");
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
