$(document).ready(function () {
    $("#registrationstart-add-form").submit(function (event) {
        var username = $("#registrationstart-add-login").val();
        var password = $("#registrationstart-add-password").val();
        var email = $("#registrationstart-add-email").val();
        var employeeDto = {
            username: username,
            password: password,
            email: email,

        };
        var resultJson = JSON.stringify(employeeDto);
        if (username !== "" && password !== ""&& email !== "") {
            sentEmployeeData(resultJson);
            event.preventDefault();
        } else {
            alert("Все поля должны быть заполнены!");
            event.preventDefault();
        }
    });

    function sentEmployeeData(json) {
        $.ajax({
            dataType : "json",
            contentType: "application/json; charset=utf-8",
            type: "POST",
            url: "/registrationstart/continue",
            data: json,
            timeout: 100000,

            success: function () {

                console.log("SUCCESS: ", "ok222");

                var username = $("#registrationstart-add-login").val();
                var password = $("#registrationstart-add-password").val();
                var employeeDto = {
                    username: username,
                    password: password,
                }
                var json = JSON.stringify(employeeDto);

                $.ajax({
                    dataType: "json",
                    contentType: "application/json; charset=utf-8",
                    type: "POST",
                    url: "/login",
                    data: json,
                    timeout: 100000,

                    success: function () {
                        console.log("SUCCESS: ", "ok222");
                        window.location = "/registrationend.html";
                    },
                    error: function () {
                        alert("Авторизация не выполнена!");
                    }

                });
            },
            error: function () {
                alert("Ошибка регистрации!");
            }

        });
    }

});

