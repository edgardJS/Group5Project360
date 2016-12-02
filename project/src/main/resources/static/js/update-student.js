
// submitting form using ajax but out here now
// it does not refresh and sending back from
// back end a 400 if there is errors, else
// sending back a 200 and success is hit.
$form = $("#searchStudentForm");
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
                $("#searchStudentForm")[0].reset();
                $form.data('bootstrapValidator').resetForm();
            },
            error: function (jqXHR, textStatus, errorThrown) {
            }
        });
    e.preventDefault();
});

/*
 Load the header and add class of current page
 to active.
 */
$(function () {
    $("#header").load("header.html", function () {
        $('.active').removeClass('active');
        $('li[name=update-student]').addClass('active');
    });
});