jQuery(document).ready(function ($) {


    $('#account-chart-donut-form').submit(function (event) {
        enableSearchAllPayslipsButton(false);
        event.preventDefault();
        searchAllEmployeePayslipsAjax()
    });



    function enableSearchAllPayslipsButton(flag) {
        $('#account-chart-donut-button').prop("disabled", flag);
    }

    function searchAllEmployeePayslipsAjax() {
        var options = {
            // title: 'Здесь будет расчетный лист',
            is3D: false,
            pieHole: 0.4,
          //  backgroundColor: '#404251'
        };

        var chart = new google.visualization.PieChart(document.getElementById('piechart'));

        $.ajax({
            type: "GET",
            url: "/account/chart/lastpie",
            timeout: 100000,
            success: function (data) {
                var payslip = google.visualization.arrayToDataTable([
        ['Task', 'Hours per Day'],
        ['Аванс',     data.advance],
        ['Зарплата',      data.salary]
    ]);
                console.log("SUCCESS: ", payslip);
                chart.draw(payslip, options);
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

    google.charts.load('current', {'packages':['corechart']});

})