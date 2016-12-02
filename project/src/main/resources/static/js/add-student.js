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
                        min: 6,
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
                        min: 3,
                    },
                    double: {
                        message: 'Please supply a valid GPA'
                    }
                }
            },
            degreeLevel: {
                validators: {
                    stringLength: {
                        min: 2,
                    },
                    notEmpty: {
                        message: 'Please supply your degree level'
                    }
                }
            },
            program: {
                validators: {
                    stringLength: {
                        min: 2,
                    },
                    notEmpty: {
                        message: 'Please supply your degree program'
                    }
                }
            },
            graduationTerm: {
                validators: {
                    stringLength: {
                        min: 4,
                    },
                    notEmpty: {
                        message: 'Please supply your graduation term'
                    }
                }
            },
            graduationYear: {
                validators: {
                    stringLength: {
                        min: 4,
                    },
                    notEmpty: {
                        message: 'Please supply your graduation year'
                    }
                }
            }
        }
    })
        .on('success.form.bv', function (e) {
            $('#success_message').slideDown({opacity: "show"}, "slow");// Do something ...
            $('#addStudentForm').data('bootstrapValidator').resetForm();
        });

    // submitting form using ajax but out here now
    // it does not refresh and sending back from
    // back end a 400 if there is errors, else
    // sending back a 200 and success is hit.
    $form = $("#addStudentForm");
    //callback handler for form submit
    $form.submit(function (e) {
        var postData = $(this).serializeArray();
        var formURL = $(this).attr("action");
        $.ajax(
            {
                url: formURL,
                type: "POST",
                data: postData,
                success: function (data, textStatus, jqXHR) {
                    $('#success_message').slideDown({opacity: "show"}, "slow");
                    $("#addStudentForm")[0].reset();
                    $form.data('bootstrapValidator').resetForm();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                }
            });
        e.preventDefault();
    });
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

