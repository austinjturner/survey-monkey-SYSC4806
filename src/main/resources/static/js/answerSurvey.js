
$(document).ready(() => {
    // remove the next button, automatically go to next on submit
    const nextQuestionLink = $(`#nextBtnLink`).attr(`href`);
    $(`#nextBtn`).remove();

    $("#submitBtn").click(function(event) {
        event.preventDefault();
        const form = $('#answerSurveyForm');
        const jsonData = {};
        $.each($(form).serializeArray(), function() {
            jsonData[this.name] = this.value;
        });
        const data = JSON.stringify(jsonData);
        console.log(data);
        $.ajax({
            url: `/api/response`,
            type: 'POST',
            contentType: "application/json",
            dataType: 'json',
            data: data,
            success: function (data) {
                // load next question
                window.location.href = nextQuestionLink;
            },
            error: function (error) {
                console.log(error)
                //reject(error)
            },
        })
    });
});
