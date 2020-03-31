
$(document).ready(function(){

    $("button").on('click', function(event){
        closeSurvey(this.id);
    });

    $('.card').hover(
        function(){
            $(this).animate({
                marginTop: "-=1%",
            }, 200);
        },

        function(){
            $(this).animate({
                marginTop: "0%",
            }, 200);
        }
    );

    $.get("/login", function(data) {
        $("#user").html(data.name);

    });


});


// logout function
const logout = function() {
    $.post("/logout", function() {
        window.location.href ="/logout"
        $("#user").html('');
    });
    return true;
};

function closeSurvey(id) {
    apiUrl = window.location.origin + '/api/survey/'+id
    $.ajax({
        url: apiUrl,
        type: 'PATCH',
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({ closed: true })
    })
}