jQuery(document).ready(function ($) {

    google.charts.load('current', {'packages': ['corechart'], 'language': 'ru'});
    google.charts.setOnLoadCallback(function () {
        searchRecentYearEmployeePayslipsAjax()
    });


    $('#getRecentYearPayslipsChart').click(function () {
        searchRecentYearEmployeePayslipsAjax()

    });

    $('#getAllPayslipsChart').click(function () {
        searchAllEmployeePayslipsAjax()

    });

    $('#getLastYearPayslipsChart').click(function () {
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
        var options = {
            chartArea: {width: '85%', right: 20, top: 20, height: '80%'},
            seriesType: 'bars',
            series: {2: {type: 'line'}},
            hAxis: {title: "Расчетные месяцы", titleTextStyle: {color: "#404251"}, format: 'MM, y'},
            vAxis: {title: "Доход, руб.", titleTextStyle: {color: "#404251"}, gridlines: {interval: 0.5}},
            isStacked: true,
            legend: {position: 'bottom', maxLines: 1,},
            colors: ['blue', 'orange']
        };
        var chart = new google.visualization.ComboChart(document.getElementById('payslipchart'));
        var months = getRussianMonth();
        var payslipsarray = [['Месяц', 'Зарплата', 'Аванс']];

        $.ajax({
            type: "GET",
            url: "/account/chart/all",
            timeout: 100000,
            success: function (data) {
                console.log("SUCCESS: ", data);
                $.each(data, function (i) {
                    payslipsarray.push([new Date(data[i].year, months.get(data[i].month)), data[i].salary, data[i].advance]);
                });
                var payslips = google.visualization.arrayToDataTable(payslipsarray);
                chart.draw(payslips, options);
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
            chartArea: {width: '85%', right: 20, top: 20, height: '80%'},
            seriesType: 'bars',
            series: {2: {type: 'line'}},
            hAxis: {title: "Расчетные месяцы", titleTextStyle: {color: "#404251"}, format: 'MM, y'},
            vAxis: {title: "Доход, руб.", titleTextStyle: {color: "#404251"}, gridlines: {interval: 0.5}},
            isStacked: true,
            legend: {position: 'bottom', maxLines: 1,},
            colors: ['blue', 'orange']
        };
        var chart = new google.visualization.ComboChart(document.getElementById('payslipchart'));
        var months = getRussianMonth();
        var payslipsarray = [['Месяц', 'Зарплата', 'Аванс']];

        $.ajax({
            type: "GET",
            url: "/account/chart/recentyear",
            timeout: 100000,
            success: function (data) {
                $.each(data, function (i) {
                    payslipsarray.push([new Date(data[i].year, months.get(data[i].month)), data[i].salary, data[i].advance]);
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
        var options = {
            chartArea: {width: '85%', right: 20, top: 20, height: '80%'},
            seriesType: 'bars',
            series: {2: {type: 'line'}},
            hAxis: {title: "Расчетные месяцы", titleTextStyle: {color: "#404251"}, format: 'MM, y'},
            vAxis: {title: "Доход, руб.", titleTextStyle: {color: "#404251"}, gridlines: {interval: 0.5}},
            isStacked: true,
            legend: {position: 'bottom', maxLines: 1,},
            colors: ['blue', 'orange']
        };
        var chart = new google.visualization.ComboChart(document.getElementById('payslipchart'));
        var months = getRussianMonth();
        var payslipsarray = [['Месяц', 'Зарплата', 'Аванс']];

        $.ajax({
            type: "GET",
            url: "/account/chart/lastyear",
            timeout: 100000,
            success: function (data) {
                console.log("SUCCESS: ");
                $.each(data, function (i) {
                    payslipsarray.push([new Date(data[i].year, months.get(data[i].month)), data[i].salary, data[i].advance]);
                });
                var payslips = google.visualization.arrayToDataTable(payslipsarray);
                chart.draw(payslips, options);
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
        var options = {
            chartArea: {width: '85%', right: 20, top: 20, height: '80%'},
            seriesType: 'bars',
            series: {2: {type: 'line'}},
            hAxis: {title: "Расчетные месяцы", titleTextStyle: {color: "#404251"}, format: 'MM'},
            vAxis: {title: "Доход, руб.", titleTextStyle: {color: "#404251"}, gridlines: {interval: 0.5}},
            legend: {position: 'bottom', maxLines: 1,},
            colors: ['blue', 'orange'],
            bar: {groupWidth: "95%"}
        };
        var chart = new google.visualization.ComboChart(document.getElementById('payslipchart'));
        var months = getRussianMonth();
        var firstYear = $("#account-chart-compare-years-first").val();
        var secondYear = $("#account-chart-compare-years-second").val();

        $.ajax({
            contentType: "application/json; charset=utf-8",
            type: "POST",
            url: "/account/chart/compare",
            data: json,
            timeout: 100000,
            success: function (data) {
                var table = new google.visualization.DataTable();
                table.addColumn({type:'date', role: 'domain'}, firstYear + 'Месяц');
                table.addColumn('number', 'Доход за ' + firstYear + ' год');
                table.addColumn( 'number', 'Доход за ' + secondYear + ' год');
                table.addColumn({type: 'string', role: 'tooltip'});
                table.addColumn({type: 'string', role: 'style'});
                $.each(data, function (i) {
                    if (data[i].year === parseInt(firstYear, 10)) {
                        table.addRow([new Date(2020, months.get(data[i].month), 1), null, data[i].salary +
                        data[i].advance, 'Доход за ' + data[i].year + ', ' + (data[i].salary + data[i].advance)  + ' руб.', 'color: blue']);
                    } else {
                        table.addRow([new Date(2020, months.get(data[i].month), 10), null, data[i].salary +
                        data[i].advance, 'Доход за ' + data[i].year + ', ' + (data[i].salary + data[i].advance)  + ' руб.', 'color: orange']);
                    }
                });
                chart.draw(table, options);
                    console.log("SUCCESS: ", table);
                    $('input[type="text"],#account-chart-compare-years-first').val('');
                    $('input[type="text"],#account-chart-compare-years-second').val('');
            },
            error: function (e) {
                alert('Извините, данные для сравнения отсутствуют!');
                $("#comparePayslips").prop("disabled", false);
                e.preventDefault();
            },
            done: function () {
                console.log("DONE");
                enableComparePaySlipsButton(true);
            }
        });
    }

    function getRussianMonth() {
        var months = new Map();
        months.set("январь", new Date(2020, 0, 1).getMonth());
        months.set("февраль", new Date(2020, 1, 1).getMonth());
        months.set("март", new Date(2020, 2, 1).getMonth());
        months.set("апрель", new Date(2020, 3, 1).getMonth());
        months.set("май", new Date(2020, 4, 1).getMonth());
        months.set("июнь", new Date(2020, 5, 1).getMonth());
        months.set("июль", new Date(2020, 6, 1).getMonth());
        months.set("август", new Date(2020, 7, 1).getMonth());
        months.set("сентябрь", new Date(2020, 8, 1).getMonth());
        months.set("октябрь", new Date(2020, 9, 1).getMonth());
        months.set("ноябрь", new Date(2020, 10, 1).getMonth());
        months.set("декабрь", new Date(2020, 11, 1).getMonth());
        return months;
    }

});
