jQuery(document).ready(function ($) {

    google.charts.load('current', {'packages': ['corechart'], 'language': 'ru'});
    google.charts.setOnLoadCallback(function () {
        checkEmployeeData();
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

    function checkEmployeeData() {
        $.ajax({
            type: "GET",
            url: "/editor/payslip/all",
            timeout: 100000,
            success: function (data) {
                console.log("SUCCESS DATA: ", data);
                    if (data.length === 0) {
                        window.location = "/editor.html";
                    }
            },
            error: function (e) {
                console.log("ERROR: ", e);
            },
            done: function () {
                console.log("DONE");
            }
        });
    }

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
            chartArea: {width: '85%', right: 20, top: 20, height: '75%'},
            seriesType: 'bars',
            series: {2: {type: 'line'}},
            hAxis: {title: "Расчетные месяцы", titleTextStyle: {color: "#404251"}, format: 'MM, y'},
            vAxis: {title: "Доход, руб.", titleTextStyle: {color: "#404251"}, gridlines: {interval: 0.5}},
            isStacked: true,
            legend: {position: 'bottom', maxLines: 1,},
            colors: ['blue', 'orange', 'red']
        };
        var chart = new google.visualization.ComboChart(document.getElementById('payslipchart'));
        var months = getRussianMonth();

        $.ajax({
            type: "GET",
            url: "/account/chart/all",
            timeout: 100000,
            success: function (data) {
                var table = new google.visualization.DataTable();
                table.addColumn({type:'date', role: 'domain'}, 'Месяц');
                table.addColumn('number', 'Зарплата');
                table.addColumn( 'number', 'Аванс');
                table.addColumn( 'number', 'Средний доход');
                table.addColumn({type: 'string', role: 'style'});
                table.addColumn({type: 'string', role: 'tooltip'});
                var sum = 0;
                var count = 0;
                $.each(data, function (i) {
                    var average = data[i].salary + data[i].advance;
                    sum = sum + average;
                    count = count + 1;
                });
                $.each(data, function (i) {
                    table.addRow([new Date(data[i].year, months.get(data[i].month)), data[i].salary, data[i].advance,
                        sum/count, 'color: red', 'средний доход ' + (sum/count).toPrecision(7) + ' руб.']);
                });
                chart.draw(table, options);
                searchMinEmployeePayslip(data);
                searchMaxEmployeePayslip(data);
                console.log("SUCCESS: ", table);
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
            chartArea: {width: '85%', right: 20, top: 20, height: '75%'},
            seriesType: 'bars',
            series: {2: {type: 'line'}},
            hAxis: {title: "Расчетные месяцы", titleTextStyle: {color: "#404251"}, format: 'MM, y'},
            vAxis: {title: "Доход, руб.", titleTextStyle: {color: "#404251"}, gridlines: {interval: 0.5}},
            isStacked: true,
            legend: {position: 'bottom', maxLines: 1,},
            colors: ['blue', 'orange', 'red']
        };
        var chart = new google.visualization.ComboChart(document.getElementById('payslipchart'));
        var months = getRussianMonth();

        $.ajax({
            type: "GET",
            url: "/account/chart/recentyear",
            timeout: 100000,
            success: function (data) {
                var table = new google.visualization.DataTable();
                table.addColumn({type:'date', role: 'domain'}, 'Месяц');
                table.addColumn('number', 'Зарплата');
                table.addColumn( 'number', 'Аванс');
                table.addColumn( 'number', 'Средний доход');
                table.addColumn({type: 'string', role: 'style'});
                table.addColumn({type: 'string', role: 'tooltip'});
                var sum = 0;
                var count = 0;
                $.each(data, function (i) {
                    var average = data[i].salary + data[i].advance;
                    sum = sum + average;
                    count = count + 1;
                });
                $.each(data, function (i) {
                    table.addRow([new Date(data[i].year, months.get(data[i].month)), data[i].salary, data[i].advance,
                        sum/count, 'color: red', 'средний доход ' + (sum/count).toPrecision(7) + ' руб.']);
                });
                chart.draw(table, options);
                searchMinEmployeePayslip(data);
                searchMaxEmployeePayslip(data);
                console.log("SUCCESS: ", table);
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
            chartArea: {width: '85%', right: 20, top: 20, height: '75%'},
            seriesType: 'bars',
            series: {2: {type: 'line'}},
            hAxis: {title: "Расчетные месяцы", titleTextStyle: {color: "#404251"}, format: 'MM, y'},
            vAxis: {title: "Доход, руб.", titleTextStyle: {color: "#404251"}, gridlines: {interval: 0.5}},
            isStacked: true,
            legend: {position: 'bottom', maxLines: 1,},
            colors: ['blue', 'orange', 'red']
        };
        var chart = new google.visualization.ComboChart(document.getElementById('payslipchart'));
        var months = getRussianMonth();

        $.ajax({
            type: "GET",
            url: "/account/chart/lastyear",
            timeout: 100000,
            success: function (data) {
                var table = new google.visualization.DataTable();
                table.addColumn({type:'date', role: 'domain'}, 'Месяц');
                table.addColumn('number', 'Зарплата');
                table.addColumn( 'number', 'Аванс');
                table.addColumn( 'number', 'Средний доход');
                table.addColumn({type: 'string', role: 'style'});
                table.addColumn({type: 'string', role: 'tooltip'});
                var sum = 0;
                var count = 0;
                $.each(data, function (i) {
                    var average = data[i].salary + data[i].advance;
                    sum = sum + average;
                    count = count + 1;
                });
                $.each(data, function (i) {
                    table.addRow([new Date(data[i].year, months.get(data[i].month)), data[i].salary, data[i].advance,
                        sum/count, 'color: red', 'средний доход ' + (sum/count).toPrecision(7) + ' руб.']);
                });
                chart.draw(table, options);
                searchMinEmployeePayslip(data);
                searchMaxEmployeePayslip(data);
                console.log("SUCCESS: ", table);
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
            series: {2: {type: 'line'}, 3: {type: 'line'}},
            hAxis: {title: "Расчетные месяцы", titleTextStyle: {color: "#404251"}, format: 'MM'},
            vAxis: {title: "Доход, руб.", titleTextStyle: {color: "#404251"}, gridlines: {interval: 0.5}},
            legend: {position: 'bottom', maxLines: 2,},
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
                table.addColumn( 'number', 'Средний доход за ' + firstYear + ' год');
                table.addColumn({type: 'string', role: 'style'});
                table.addColumn( 'number', 'Средний доход за ' + secondYear + ' год');
                table.addColumn({type: 'string', role: 'style'});
                var sumFirst = 0;
                var countFirst = 0;
                var sumSecond = 0;
                var countSecond = 0;

                $.each(data, function (i) {
                    if (data[i].year === parseInt(firstYear, 10)) {
                        var averageFirst = data[i].salary + data[i].advance;
                        sumFirst = sumFirst + averageFirst;
                        countFirst = countFirst + 1;
                    } else {
                        var averageSecond = data[i].salary + data[i].advance;
                        sumSecond = sumSecond + averageSecond;
                        countSecond = countSecond + 1;
                    }
                });
                $.each(data, function (i) {
                    if (data[i].year === parseInt(firstYear, 10)) {
                        table.addRow([new Date(2020, months.get(data[i].month), 1), null, data[i].salary +
                        data[i].advance, 'Доход за ' + data[i].year + ', ' + (data[i].salary + data[i].advance)  +
                        ' руб.', 'color: blue', (sumFirst/countFirst), "color: blue", null, null]);
                    } else {
                        table.addRow([new Date(2020, months.get(data[i].month), 10), null, data[i].salary +
                        data[i].advance, 'Доход за ' + data[i].year + ', ' + (data[i].salary + data[i].advance)  +
                        ' руб.', 'color: orange', null, null, (sumSecond/countSecond), "color: orange"]);
                    }
                });
                chart.draw(table, options);
                searchMinEmployeePayslip(data);
                searchMaxEmployeePayslip(data);
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



    function searchMinEmployeePayslip(data) {
        var pieOptions = {
            chartArea: {width: '85%', left: 20, height: '90%'},
            is3D: false,
            pieHole: 0.4,
            colors: ['blue', 'orange'],
            legend: {alignment: 'center', textStyle: {fontSize: 16}}
        };
        var pieChart = new google.visualization.PieChart(document.getElementById('pieChartMin'));
        var lowestSal = Number.POSITIVE_INFINITY;
        var lowestAdv = Number.POSITIVE_INFINITY;
        var tmpSal;
        var tmpAdv;
        for (var i= data.length-1; i>=0; i--) {
            tmpSal = data[i].salary;
            tmpAdv = data[i].advance;
            if (tmpAdv < lowestAdv) lowestAdv = tmpAdv;
            if (tmpSal < lowestSal) lowestSal = tmpSal;
        }
        var payslip = google.visualization.arrayToDataTable([
            ['Категория', 'Сумма'],
            ['Зарплата', lowestSal],
            ['Аванс', lowestAdv]

        ]);
                console.log("SUCCESS: ", pieChart);
                pieChart.draw(payslip, pieOptions);
    }

    function searchMaxEmployeePayslip(data) {
        var pieOptions = {
            chartArea: {width: '85%', left: 20, height: '90%'},
            is3D: false,
            pieHole: 0.4,
            colors: ['blue', 'orange'],
            legend: {alignment: 'center', textStyle: {fontSize: 16}}
        };
        var pieChart = new google.visualization.PieChart(document.getElementById('pieChartMax'));
        var highestSal = Number.NEGATIVE_INFINITY;
        var highestAdv = Number.NEGATIVE_INFINITY;
        var tmpSal;
        var tmpAdv;
        for (var i= data.length-1; i>=0; i--) {
            tmpSal = data[i].salary;
            tmpAdv = data[i].advance;
            if (tmpAdv > highestAdv) highestAdv = tmpAdv;
            if (tmpSal > highestSal) highestSal = tmpSal;
        }
        var payslip = google.visualization.arrayToDataTable([
            ['Категория', 'Сумма'],
            ['Зарплата', highestSal],
            ['Аванс', highestAdv]
        ]);
        console.log("SUCCESS: ", pieChart);
        pieChart.draw(payslip, pieOptions);
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
