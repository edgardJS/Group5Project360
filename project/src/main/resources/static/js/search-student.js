$(document).ready(function () {
    $('#searchStudentForm').bootstrapValidator({
        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            studentId: {
                validators: {
                    stringLength: {
                        min: 7,
                        max:7,
                        message: "Please enter a 7 digit Student ID"
                    },
                    notEmpty: {
                        message: "Please enter a Student ID to search for"
                    }
                }
            }
        }
    });
});

// submitting form using ajax but out here now
// it does not refresh and sending back from
// back end a 400 if there is errors, else
// sending back a 200 and success is hit.
$searchForm = $("#searchStudentForm");
//callback handler for form submit
$searchForm.submit(function (e) {
    var postData = $(this).serializeArray();
    var formURL = $(this).attr("action");
    //e.preventDefault();
    //e.stopImmediatePropagation();
    $.ajax(
        {
            url: formURL,
            type: "POST",
            data: postData,
            success: function (response) {
                $('#success_message').slideDown({opacity: "show"}, "slow");
                $('#failed_message').slideUp({opacity: "hide"}, "slow");
                $("#searchStudentForm")[0].reset();
                //$form.data('bootstrapValidator').resetForm();
                //return false;
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(errorThrown);
            }
        })
        .fail(function (e) {
            $('#failed_message').slideDown({opacity: "show"}, "slow");
            $('#success_message').slideUp({opacity: "hide"}, "slow");
            $("#searchStudentForm")[0].reset();
            return false;
        });
    //response.preventDefault();
    //e.stopImmediatePropagation();
    //return false;
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
