jQuery(document).ready(function ($) {

    google.charts.load('current', {'packages':['corechart'], 'language': 'ru'});
    google.charts.setOnLoadCallback(function(){searchRecentYearEmployeePayslipsAjax()});


    $('#getRecentYearPayslipsChart').click(function (event) {
        searchRecentYearEmployeePayslipsAjax()

    });

    $('#getAllPayslipsChart').click(function (event) {
        searchAllEmployeePayslipsAjax()

    });

    $('#getLastYearPayslipsChart').click(function (event) {
        searchLastYearEmployeePayslipsAjax()
    });


    function enableSearchAllPayslipsButton(flag) {
        $('#getAllPayslipsChart').prop("disabled", flag);
    }


    function enableSearchRecentYearPayslipsButton(flag) {
        $('#getRecentYearPayslipsChart').prop("disabled", flag);
    }


    function enableSearchLastYearPayslipsButton(flag) {
        $('#getLastYearPayslipsChart').prop("disabled", flag);
    }

    function enableComparePaySlipsButton(flag) {
        $('#comparePayslips').prop("disabled", flag);
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
            error: function () {
                console.log("ERROR: ");
            },
            done: function () {
                console.log("DONE");
                enableSearchAllPayslipsButton(true);
            }
        });
    }




    function searchRecentYearEmployeePayslipsAjax() {
        var options = {
            tooltip: { trigger: 'none' },
            chartArea: {width: '85%', right: 20, top: 20, height: '80%'},
            seriesType: 'bars',
            series: {2: {type: 'line'}},
            hAxis: {title: "Расчетные месяцы", titleTextStyle: {color: "#404251"}, format: 'MM, y'},
            vAxis: {title: "Доход, руб.", titleTextStyle: {color: "#404251"}, gridlines: {interval: 0.5}},
            isStacked: true,
            legend: {position: 'bottom', maxLines: 1,}
        };
        var chart = new google.visualization.ComboChart(document.getElementById('payslipchart'));
        var months = new Map();
        months.set("январь", new Date(2020, 1).getMonth());
        months.set("февраль", new Date(2020, 2).getMonth());
        months.set("март", new Date(2020, 3).getMonth());
        months.set("апрель", new Date(2020, 4).getMonth());
        months.set("май", new Date(2020, 5).getMonth());
        months.set("июнь", new Date(2020, 6).getMonth());
        months.set("июль", new Date(2020, 7).getMonth());
        months.set("август", new Date(2020, 8).getMonth());
        months.set("сентябрь", new Date(2020, 9).getMonth());
        months.set("октябрь", new Date(2020, 10).getMonth());
        months.set("ноябрь", new Date(2020, 11).getMonth());
        months.set("декабрь", new Date(2020, 12).getMonth());
        var payslipsarray = [['Месяц', 'Зарплата', 'Аванс']];

        $.ajax({
            type: "GET",
            url: "/account/chart/recentyear",
            timeout: 100000,
            success: function (data) {
                console.log("SUCCESS: ", data);
                $.each(data, function (i) {
                    payslipsarray.push([new Date(data[i].year, months.get(data[i].month) - 1), data[i].salary, data[i].advance]);
                });
                var payslips = google.visualization.arrayToDataTable(payslipsarray);
                chart.draw(payslips, options);
            },
            error: function () {
                console.log("ERROR: ");
            },
            done: function () {
                console.log("DONE");
                enableSearchRecentYearPayslipsButton(true);
            }
        });
    }

    function searchLastYearEmployeePayslipsAjax() {
        $.ajax({
            type: "GET",
            url: "/account/chart/lastyear",
            contentType: "blob",
            timeout: 100000,
            success: function (data) {
                console.log("SUCCESS: ");
                $("#account-chart-display").html('<img src="data:image/png;base64,' + data + '" />');
            },
            error: function () {
                console.log("ERROR: ");
            },
            done: function () {
                console.log("DONE");
                enableSearchLastYearPayslipsButton(true);
            }
        });
    }


    $("#account-chart-compare-form").submit(function (event) {
        enableComparePaySlipsButton(false);
        var firstYear = $("#account-chart-compare-years-first").val();
        var secondYear = $("#account-chart-compare-years-second").val();
        var resultJson = JSON.stringify([firstYear, secondYear]);
        if (firstYear !== " " && secondYear !== "") {
            compareYearsChartForPaySlipsAjax(resultJson);
            event.preventDefault();
        } else {
            alert("Заполните, пожалуйста, все поля!");
            event.preventDefault();
        }
    });


    function compareYearsChartForPaySlipsAjax(json) {
        $.ajax({
            contentType: "application/json; charset=utf-8",
            type: "POST",
            url: "/account/chart/compare",
            data: json,
            timeout: 100000,
            success: function (data) {
                console.log("SUCCESS: ", data);
                $("#account-chart-display").html('<img src="data:image/png;base64,' + data + '" />');
            },
            error: function () {
                // $('#editor-payslip-add-btn').parent().append('<p>Расчетный лист за этот период уже существует!</p>');
                console.log("ERROR: ");
                event.preventDefault();
            },
            done: function () {
                console.log("DONE");
                enableComparePaySlipsButton(true);
            }
        });
    }







})
