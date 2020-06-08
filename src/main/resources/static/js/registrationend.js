jQuery(document).ready(function ($) {

    $("#registrationend-card-add-form").submit(function (event) {
        enableAddNewUserButton(false);
        var employeeNumber = $("#registrationend-card-add-id").val();
        var surname = $("#registrationend-card-add-surname").val();
        var firstname = $("#registrationend-card-add-firstname").val();
        var middlename = $("#registrationend-card-add-middlename").val();
        var company = $("#registrationend-card-add-company").val();
        var department = $("#registrationend-card-add-department").val();
        var position = $("#registrationend-card-add-position").val();
        var employeeDto = {
            employeeNumber: employeeNumber,
            surname: surname,
            firstName: firstname,
            middleName: middlename,
            company: company,
            department: department,
            position: position
        };
        var resultJson = JSON.stringify(employeeDto);
        if (employeeNumber !== " " && surname !== "" && firstname !== "" && middlename !== "") {
            addEmployeeDataAjax(resultJson);
            event.preventDefault();
        } else {
            alert("Заполните, пожалуйста, все поля!");
            event.preventDefault();
        }
    });

    $("#file-input-choose2").on("change", (function (e) {
            var fileInput = document.getElementById("file-input-choose2");
            var file = fileInput.files[0];
            formData = new FormData();
            formData.append("file", file);
            $.ajax({
                type: "POST",
                url: "/registrationend/uploadfile",
                data: formData,
                cache: false,
                timeout: 100000,
                enctype: 'multipart/form-data',
                processData: false,
                contentType: false,
                success: function () {
                    console.log("SUCCESS: ", "ok222");
                    $("#button2").prop("disabled", false);
                    e.preventDefault();
                    window.location = "http://localhost:9090/accountpage.html";
                },
                error: function () {
                    alert("Не удалось загрузить файл!");
                    $("#button2").prop("disabled", false);
                    e.preventDefault();
                }
            });
        })
    );
});


function enableAddNewUserButton(flag) {
    $('#registrationend-card-add-btn').prop("disabled", flag);
}

function addEmployeeDataAjax(json) {
    $.ajax({
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        type: "POST",
        url: "http://localhost:9090/registrationend/adddata",
        data: json,
        timeout: 100000,
        success: function () {
            console.log("SUCCESS: ", "ok222");
            window.location = "http://localhost:9090/accountpage.html";
        },
        error: function () {
            $('#registrationend-card-add-btn').parent().append('<p>Сотрудник с такими данными уже существует!</p>');
            console.log("ERROR: ");
            event.preventDefault();

        },
        done: function (e) {
            console.log("DONE");
            enableAddNewUserButton(true);
        }
    });
}













