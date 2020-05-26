jQuery(document).ready(function ($) {



    $('#routes-search-form').submit(function (event) {
        enableAllPathButton(false);
        event.preventDefault();
        ajaxGetTwo()
    });


    $("#lkemployee-card-add-form").submit(function (event) {
        enableAddNewUserButton(false);
        var id = $("#lkemployee-card-add-id").val();
        var surname = $("#lkemployee-card-add-surname").val();
        var firstname = $("#lkemployee-card-add-firstname").val();
        var middlename = $("#lkemployee-card-add-middlename").val();
        var company = $("#lkemployee-card-add-company").val();
        var department = $("#lkemployee-card-add-department").val();
        var position = $("#lkemployee-card-add-position").val();
        var employeeDto = {
            id: id,
            surname: surname,
            firstName: firstname,
            middleName: middlename,
            company: company,
            department: department,
            position: position
        };
        var resultJson = JSON.stringify(employeeDto);
        if (surname !== "" && firstname !== "" && middlename !== "") {
            addNewUserAjax(resultJson);
            event.preventDefault();
        } else {
            alert("All fields must be required");
            event.preventDefault();
        }
    });


    $('#lkemployee-card-loading').change(function(e){
        var fileName = e.target.files[0].name;
        alert('The file "' + fileName +  '" has been selected.');
    });


    // $('#lkemployee-card-load-form').submit(function (event) {
    //     enableLoadFileButton(false);
    //     event.preventDefault();
    //     uploadFileByAjax()
    // });
    //
    //
    // function enableLoadFileButton(flag) {
    //     $('#lkemployee-card-load-button').prop("disabled", flag);
    // }



    // $("lkemployee-card-load-form").submit(function(evt){
    //     evt.preventDefault();
    //     var formData = new FormData($(this)[0]);
    //     $.ajax({
    //         url: 'fileUpload',
    //         type: 'POST',
    //         data: formData,
    //         async: false,
    //         cache: false,
    //         contentType: false,
    //         enctype: 'multipart/form-data',
    //         processData: false,
    //         success: function (response) {
    //             alert(response);
    //         }
    //     });
    //     return false;
    // });

    // $('#button').on('click', function() {
    //     $('#file-input').trigger('click');
    // });

    $('#button').click(function() {
        $("#button").on('change', function () {
            var form = $('#lkemployee-card-load-form')[0];
            var formData = new FormData(form);
            $.ajax({
                type: "POST",
                url: "/lkemployee/uploadfile",
                data: formData,
                cache: false,
                timeout: 100000,
                enctype: 'multipart/form-data',
                processData: false,
                contentType: false,
                success: function () {
                    console.log("SUCCESS: ", "ok222");
                    $("#button").prop("disabled", false);
                },
                error: function () {
                    alert("File was not uploaded!");
                    $("#button").prop("disabled", false);
                }
            });
        }).click();
    });



        // $("#lkemployee-card-load-button").submit(function (event) {
        //     event.preventDefault();
        //     var form = $('#lkemployee-card-load-form')[0];
        //     var formData = new FormData(form);
        //     $("#lkemployee-card-load-button").prop("disabled", true);
        //     $.ajax({
        //         type: "POST",
        //         url: "/lkemployee/uploadfile",
        //         data: formData,
        //         cache: false,
        //         timeout: 100000,
        //         enctype: 'multipart/form-data',
        //         processData: false,
        //         contentType: false,
        //         success: function () {
        //             console.log("SUCCESS: ", "ok222");
        //             $("#lkemployee-card-load-button").prop("disabled", false);
        //         },
        //         error: function () {
        //             alert("File was not uploaded!");
        //             $("#lkemployee-card-load-button").prop("disabled", false);
        //         }
        //     });
        // });

            // } else {
            //     alert("Выберите, пожалуйста, pdf-файл");
            //     event.preventDefault();
            // }

});

// $(document).ready(function () {
//
//     $("#btnSubmit").click(function (event) {
//
//         //stop submit the form, we will post it manually.
//         event.preventDefault();

        // // Get form
        // var form = $('#fileUploadForm')[0];
        //
        // // Create an FormData object
        // var data = new FormData(form);

        // If you want to add an extra field for the FormData
//         data.append("CustomField", "This is some extra data, testing");
//
//         // disabled the submit button
//         $("#btnSubmit").prop("disabled", true);
//
//         $.ajax({
//             type: "POST",
//             enctype: 'multipart/form-data',
//             url: "/api/upload/multi",
//             data: data,
//             processData: false,
//             contentType: false,
//             cache: false,
//             timeout: 600000,
//             success: function (data) {
//
//                 $("#result").text(data);
//                 console.log("SUCCESS : ", data);
//                 $("#btnSubmit").prop("disabled", false);
//
//             },
//             error: function (e) {
//
//                 $("#result").text(e.responseText);
//                 console.log("ERROR : ", e);
//                 $("#btnSubmit").prop("disabled", false);
//
//             }
//         });
//
//     });
//
// });





function enableAddNewUserButton(flag) {
    $('#lkemployee-card-add-btn').prop("disabled", flag);
}

function addNewUserAjax(json) {
    $.ajax({
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        type: "POST",
        url: "http://localhost:9090/lkemployee/addnew",
        data: json,
        timeout: 100000,
        success: function () {
            console.log("SUCCESS: ", "ok222");
            window.location = "http://localhost:9090/lkemployee.html";
        },
        error: function () {
            $('#lkemployee-card-add-btn').parent().append('<p>Username has been already exists!</p>');
            console.log("ERROR: ", e);
            event.preventDefault();

        },
        done: function (e) {
            console.log("DONE");
            enableAddNewUserButton(true);
        }
    });
}


// $(document).ready(function () {
//     $("#login-username-form").submit(function (event) {
//         var username = $("#username").val();
//         var password = $("#password").val();
//         var userDTO = {
//             username: username,
//             password: password
//         };
//         var resultJson = JSON.stringify(userDTO);
//         if (username !== "" && password !== "") {
//             sentUserData(resultJson);
//             event.preventDefault();
//         } else {
//             alert("Both fields must be required");
//             event.preventDefault();
//         }
//     });
//
// });

//
//
//
//     $("#ingredient_file").on("change", function (e) {
//         var file = $(this)[0].files[0];
//         var upload = new Upload(file);
//
//         // maby check size or type here with upload.getSize() and upload.getType()
//
//         // execute upload
//         upload.doUpload();
//     });
//
//
//
//
//
//
//
//     $('#info-min-form').submit(function (event) {
//         enableSearchMinButton(false);
//         event.preventDefault();
//         searchMinLengthAjax()
//     });
//
//

//
//     $('#route-one-search-form').submit(function (event) {
//         enableOnePathButton(false);
//         event.preventDefault();
//         ajaxGetPathById();
//         ajaxGetPathLength()
//     });
//
//     $('#editor-one-search-form').submit(function (event) {
//         enableOnePathButton(false);
//         event.preventDefault();
//         ajaxGetPathByIdEditor();
//     });
//
//     $('#editor-patch-form').submit(function (event) {
//         enableChangeNumberButton(false);
//         event.preventDefault();
//         ajaxChangeNumberEditor();
//     });
//
//     $('#editor-delete-form').submit(function (event) {
//         enableDeleteButton(false);
//         event.preventDefault();
//         ajaxDeleteEditor();
//     });
//
//     $('#editor-coors-search-form').submit(function (event) {
//         enableFindCoorsButton(false);
//         event.preventDefault();
//         ajaxGetCoorsById();
//     });
//
//     $('#login-username-form').submit(function (event) {
//         enableLoginButton(false);
//         event.preventDefault();
//         ajaxLogin();
//     });
// });
//
// function enableSearchMaxButton(flag) {
//     $('#info-max-form-search').prop("disabled", flag);
// }
//
// function enableSearchMinButton(flag) {
//     $('#info-min-form-search').prop("disabled", flag);
// }
//
//
// function searchMaxLengthAjax() {
//     $.ajax({
//         type: "GET",
//         url: "http://localhost:9090/cyclepath/maxlength",
//         timeout: 100000,
//         success: function (data) {
//             console.log("SUCCESS: ", data);
//             displayMaxLength(data);
//         },
//         error: function (e) {
//             console.log("ERROR: ", e);
//             displayMaxLength(e);
//         },
//         done: function (e) {
//             console.log("DONE");
//             enableSearchMaxButton(true);
//         }
//     });
// }
//
// function searchMinLengthAjax() {
//     $.ajax({
//         type: "GET",
//         url: "http://localhost:9090/cyclepath/minlength",
//         timeout: 100000,
//         success: function (data) {
//             console.log("SUCCESS: ", data);
//             displayMinLength(data);
//         },
//         error: function (e) {
//             console.log("ERROR: ", e);
//             displayMinLength(e);
//         },
//         done: function (e) {
//             console.log("DONE");
//             enableSearchMinButton(true);
//         }
//     });
// }
//
// function displayMaxLength(data) {
//     var json =
//         +JSON.stringify(data, null, 4);
//     $('#maxlength').val(json);
// }
//
// function displayMinLength(data) {
//     var json =
//         +JSON.stringify(data, null, 4);
//     $('#minlength').val(json);
// }
//
function enableAllPathButton(flag) {
    $('#getALlRoutes').prop("disabled", flag);
}
//
// function enableOnePathButton(flag) {
//     $('#getOneRoute').prop("disabled", flag);
// }
//
// function enableChangeNumberButton(flag) {
//     $('#editor-patch-number-btn').prop("disabled", flag);
// }
//
// function enableDeleteButton(flag) {
//     $('#editor-delete-btn').prop("disabled", flag);
// }
//
// function enableFindCoorsButton(flag) {
//     $('#editor-getRouteCoors').prop("disabled", flag);
// }
//
// function enableLoginButton(flag) {
//     $('#login-username-form-login').prop("disabled", flag);
// }
//
function displayToTableAll(data) {
    $('tr:has(td)').remove();
    $.each(data, function (i, item) {
        $('<tr>').html("<td>" + data[i].id + "</td><td>" + data[i].surname  + "</td>")
            .appendTo('#routes-all');
    });
}
//
// function displayToTableOne(data) {
//     $('#route-one').find("tr:gt(0)").remove();
//     $('<tr>').html("<td>" + data.id + "</td><td>" + data.width + "</td><td>" + data.location + "</td><td id='length'>" + "</td>")
//         .appendTo('#route-one');
// }
//
// function displayToTableOneEditor(data) {
//     $('#editor-one').find("tr:gt(0)").remove();
//     $('<tr>').html("<td>" + data.id + "</td><td>" + data.number + "</td><td>" + data.name + "</td><td>" + data.objectPhone + "</td>")
//         .appendTo('#editor-one');
// }
//
// function displayToTableCoors(data) {
//     $('#editor-coors').find("tr:gt(0)").remove();
//     $.each(data, function (i, item) {
//         $('<tr>').html("<td>" + data[i].coorX + "</td><td>" + data[i].coorY + "</td>")
//             .appendTo('#editor-coors');
//     });
// }
//
// function ajaxGetPathLength() {
//     $.ajax({
//         type: "GET",
//         url: "http://localhost:9090/lkemployee/" + document.getElementById('route-one-searching').value + "/length",
//         timeout: 100000,
//         success: function (data) {
//             console.log("SUCCESS: ", data);
//             document.getElementById('length').innerHTML = data
//         },
//         error: function (e) {
//             console.log("ERROR: ", e);
//             display(e);
//         },
//         done: function (e) {
//             console.log("DONE");
//             enableOnePathButton(true);
//         }
//     });
// }
//
//
function ajaxGetTwo() {
    $.ajax({
        type: "GET",
        url: "/lkemployee/all",
        timeout: 100000,
        success: function (data) {
            console.log("SUCCESS: ", data);
            displayToTableAll(data);
        },
        error: function (e) {
            console.log("ERROR: ", e);
        },
        done: function (e) {
            console.log("DONE");
            enableAllPathButton(true);
        }
    });
}
//
// function ajaxGetPathById() {
//     $.ajax({
//         type: "GET",
//         url: "http://localhost:9090/cyclepath/" + document.getElementById('route-one-searching').value,
//         timeout: 100000,
//         success: function (data) {
//             console.log("SUCCESS: ", data);
//             displayToTableOne(data);
//         },
//         error: function (e) {
//             console.log("ERROR: ", e);
//             display(e);
//         },
//         done: function (e) {
//             console.log("DONE");
//             enableOnePathButton(true);
//         }
//     });
// }
//
//
// function ajaxGetPathByIdEditor() {
//     $.ajax({
//         type: "GET",
//         url: "http://localhost:9090/cyclepath/" + document.getElementById('editor-one-searching').value,
//         timeout: 100000,
//         success: function (data) {
//             console.log("SUCCESS: ", data);
//             displayToTableOneEditor(data);
//         },
//         error: function (e) {
//             console.log("ERROR: ", e);
//             display(e);
//         },
//         done: function (e) {
//             console.log("DONE");
//             enableOnePathButton(true);
//         }
//     });
// }
//
//
// function ajaxChangeNumberEditor() {
//     var newNumber = {number: document.getElementById('editor-patch-change-number').value}
//     $.ajax({
//         dataType: 'json',
//         contentType: "application/json; charset=utf-8",
//         type: "PATCH",
//         url: "http://localhost:9090/cyclepath/" + document.getElementById('editor-one-searching').value,
//         data: JSON.stringify(newNumber),
//         timeout: 100000,
//
//         success: function (newNumber) {
//             console.log("SUCCESS: ", newNumber);
//         },
//         error: function (e) {
//             console.log("ERROR: ", e);
//         },
//         done: function (e) {
//             console.log("DONE");
//             enableOnePathButton(true);
//         }
//     });
// }
//
// function ajaxDeleteEditor() {
//     var deletedId = {}
//     $.ajax({
//         dataType: 'json',
//         contentType: "application/json; charset=utf-8",
//         type: "DELETE",
//         url: "http://localhost:9090/cyclepath/" + document.getElementById('editor-delete-route').value,
//         data: JSON.stringify(deletedId),
//         timeout: 100000,
//
//         success: function (deletedId) {
//             console.log("SUCCESS: ", deletedId);
//         },
//         error: function (e) {
//             console.log("ERROR: ", e);
//             display(e);
//         },
//         done: function (e) {
//             console.log("DONE");
//             enableOnePathButton(true);
//         }
//     });
// }
//
// function ajaxGetCoorsById() {
//     $.ajax({
//         type: "GET",
//         url: "http://localhost:9090/cyclepath/" + document.getElementById('editor-coors-searching').value + "/coordinates",
//         timeout: 100000,
//         success: function (data) {
//             console.log("SUCCESS: ", data);
//             displayToTableCoors(data);
//         },
//         error: function (e) {
//             console.log("ERROR: ", e);
//             display(e);
//         },
//         done: function (e) {
//             console.log("DONE");
//             enableFindCoorsButton(true);
//         }
//     });
//
// }






