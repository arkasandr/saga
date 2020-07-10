$(window).on('load', function () {
    $.ajax({
        type: "GET",
        url: "/registrationstart/current",
        timeout: 100,
        success: function (data) {
            console.log("SUCCESS DATA: ", data);
            if (data !== "anonymousUser") {
                $('#login-wrapper').html(data);

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