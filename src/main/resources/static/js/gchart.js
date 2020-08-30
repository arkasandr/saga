jQuery(document).ready(function ($) {

    google.charts.load('current', {'packages': ['corechart'], 'language': 'ru'});
    google.charts.setOnLoadCallback(function () {
        searchLastEmployeePayslipAjax()
    });


    $('#account-chart-donut-form').submit(function (event) {
        enableSearchAllPayslipsButton(false);
        event.preventDefault();
        searchLastEmployeePayslipAjax()
    });


    function enableSearchAllPayslipsButton(flag) {
        $('#account-chart-donut-button').prop("disabled", flag);
    }

    function searchLastEmployeePayslipAjax() {
        var options = {
            chartArea: {width: '85%', left: 20, height: '90%'},
            is3D: false,
            pieHole: 0.4,
            colors: ['blue', 'orange'],
            title: 'Расчетный лист за последний месяц',
        };

        var chart = new google.visualization.PieChart(document.getElementById('piechart'));

        $.ajax({
            type: "GET",
            url: "/account/chart/lastpie",
            timeout: 100000,
            success: function (data) {
                var payslip = google.visualization.arrayToDataTable([
                    ['Категория', 'Сумма'],
                    ['Аванс', data.advance],
                    ['Зарплата', data.salary]
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

})