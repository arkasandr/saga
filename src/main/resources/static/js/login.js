$(window).on('load', function () {
    $.ajax({
        type: "GET",
        url: "/registrationstart/current",
        timeout: 100000,
        success: function (data) {
            console.log("SUCCESS DATA: ", data);
            if (data !== "anonymousUser") {
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

});

$(document).ready(function () {
    $("#login-username-form").submit(function (event) {
        var username = $("#username").val();
        var password = $("#password").val();
        var userDTO = {
            username: username,
            password: password
        };
        var resultJson = JSON.stringify(userDTO);
        if (username !== "" && password !== "") {
            registerNewEmployee(resultJson);
            event.preventDefault();
        } else {
            alert("Заполните, пожалуйста, оба поля!");
            event.preventDefault();
        }
    });

    function registerNewEmployee(json) {
        $.ajax({
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            type: "POST",
            url: "/login",
            data: json,
            timeout: 100000,

            success: function () {
                window.location = "/editor.html";
                console.log("SUCCESS: ", "ok222");
            },
            error: function () {
                alert("Ошибка регистрации!");
            }

        });
    }

});
