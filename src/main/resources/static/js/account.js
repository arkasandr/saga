jQuery(document).ready(function ($) {


    $('#account-chart-all-search-form').ready(function (event) {
        searchRecentYearEmployeePayslipsAjax()
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


    $('#getAllPayslipsChart').click(function (event) {
        searchAllEmployeePayslipsAjax()

    });


    function enableSearchRecentYearPayslipsButton(flag) {
        $('#getRecentYearPayslipsChart').prop("disabled", flag);
    }


    function searchRecentYearEmployeePayslipsAjax() {
        $.ajax({
            type: "GET",
            url: "/account/chart/recentyear",
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
                enableSearchRecentYearPayslipsButton(true);
            }
        });
    }


    $('#getLastYearPayslipsChart').click(function (event) {
        searchLastYearEmployeePayslipsAjax()
    });


    function enableSearchLastYearPayslipsButton(flag) {
        $('#getLastYearPayslipsChart').prop("disabled", flag);
    }


    function searchLastYearEmployeePayslipsAjax() {
        $.ajax({
            type: "GET",
            url: "/account/chart/lastyear",
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
                enableSearchLastYearPayslipsButton(true);
            }
        });
    }

})
