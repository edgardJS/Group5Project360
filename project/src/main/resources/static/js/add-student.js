$(document).ready(function () {
    $('#addStudentForm').bootstrapValidator({
        // To use feedback icons, ensure that you use Bootstrap v3.1.0 or later
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            id: {
                validators: {
                    stringLength: {
                        min: 7,
                    },
                    notEmpty: {
                        message: 'Please supply your student ID'
                    }
                }
            },
            firstName: {
                validators: {
                    stringLength: {
                        min: 2,
                    },
                    notEmpty: {
                        message: 'Please supply your first name'
                    }
                }
            },
            lastName: {
                validators: {
                    stringLength: {
                        min: 2,
                    },
                    notEmpty: {
                        message: 'Please supply your last name'
                    }
                }
            },
            uwEmail: {
                validators: {
                    notEmpty: {
                        message: 'Please supply your email address'
                    },
                    emailAddress: {
                        message: 'Please supply a valid email address'
                    }
                }
            },
            email: {
                validators: {
                    notEmpty: {
                        message: 'Please supply your email address'
                    },
                    emailAddress: {
                        message: 'Please supply a valid email address'
                    }
                }
            },
            gpa: {
                validators: {
                    stringLength: {
                        min: 3
                    },
                    between: {
                      min: 0.0,
                        max: 4.0,
                        message: 'GPA must be between 0.0 and 4.0'
                    },
                    notEmpty: {
                        message: 'Please supply your GPA'
                    },
                    double: {
                        message: 'Please supply a valid GPA'
                    }
                }
            },
            degreeLevel: {
                validators: {
                    notEmpty: {
                        message: 'Please supply your degree level'
                    }
                }
            },
            program: {
                validators: {
                    notEmpty: {
                        message: 'Please supply your degree program'
                    }
                }
            }
        }
    });
        // .on('success.form.bv', function (e) {
        //     $('#success_message').slideDown({opacity: "show"}, "slow");// Do something ...
        //     $('#addStudentForm').data('bootstrapValidator').resetForm();
        // });
});

/*
 Load the header and add class of current page
 to active.
 */
$(function () {
    $("#header").load("header.html", function () {
        $('.active').removeClass('active');
        $('li[name=add-student]').addClass('active');
    });
});

/* Datepicker config */
$('#addStudentForm .input-group.date').datepicker({
    clearBtn: true

});

$("#employment-plus").click(function() {
    $( "#employment-container" ).slideToggle( "slow", function() {
        // Do stuff here if needed maybe validation and change plus to minus?
    });
});

// submitting form using ajax but out here now
// it does not refresh and sending back from
// back end a 400 if there is errors, else
// sending back a 200 and success is hit.
$form = $("#addStudentForm");
//callback handler for form submit
$form.unbind('submit').submit(function (e) {
    e.preventDefault();
    var postData = $(this).serializeArray();
    var formURL = $(this).attr("action");
    var response = $.ajax(
        {
            url: formURL,
            type: "POST",
            data: postData,
            success: function (response) {
                $('#success_message').slideDown({opacity: "show"}, "slow");
                $("#addStudentForm")[0].reset();
                $form.data('bootstrapValidator').resetForm();
                return false;
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(errorThrown);
            }
        })
        .fail(function(e) {
            $('#failed_message').slideDown({opacity: "show"}, "slow");
            return false;
        });
    //response.preventDefault();
    e.stopImmediatePropagation();
    return false;
});

