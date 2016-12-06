// submitting form using ajax but out here now
// it does not refresh and sending back from
// back end a 400 if there is errors, else
// sending back a 200 and success is hit.
$editForm = $("#editStudentForm");
//callback handler for form submit
$editForm.submit(function (e) {
    var postData = $(this).serializeArray();
    var formURL = $(this).attr("action");
    e.preventDefault();
    e.stopImmediatePropagation();
    $.ajax(
        {
            url: formURL,
            type: "POST",
            data: postData,
            success: function (response) {
                $('#success_message').slideDown({opacity: "show"}, "slow");
                $('#failed_message').slideUp({opacity: "hide"}, "slow");
                $("#editStudentForm")[0].reset();
                //$form.data('bootstrapValidator').resetForm();
                return false;
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(errorThrown);
            }
        })
        .fail(function (e) {
            $('#failed_message').slideDown({opacity: "show"}, "slow");
            $("#editStudentForm")[0].reset();
            return false;
        });
    //response.preventDefault();
    e.stopImmediatePropagation();
    return false;
});

// submitting form using ajax but out here now
// it does not refresh and sending back from
// back end a 400 if there is errors, else
// sending back a 200 and success is hit.
$form = $("#addEmploymentForm");
//callback handler for form submit
$form.submit(function (e) {
    var postData = $(this).serializeArray();
    var formURL = $(this).attr("action");
    e.preventDefault();
    e.stopImmediatePropagation();
    $.ajax(
        {
            url: formURL,
            type: "POST",
            data: postData,
            success: function (response) {
                $('#success_message_employment').slideDown({opacity: "show"}, "slow");
                $('#failed_message_employment').slideUp({opacity: "hide"}, "slow");
                $("#addEmploymentForm")[0].reset();
                //$form.data('bootstrapValidator').resetForm();
                return false;
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(errorThrown);
            }
        })
        .fail(function (e) {
            $('#failed_message_employement').slideDown({opacity: "show"}, "slow");
            $("#addEmploymentForm")[0].reset();
            return false;
        });
    //response.preventDefault();
    e.stopImmediatePropagation();
    return false;
});

/*
 Load the header and add class of current page
 to active.
 */
$(function () {
    $("#header").load("header.html", function () {
        $('.active').removeClass('active');
        $('li[name=search-student]').addClass('active');
    });
});

$("#employment-plus").click(function () {
    $("#employment-container, #employment-button").slideToggle("slow", function () {
        // Do stuff here if needed maybe validation and change plus to minus?
    });
});