
$(document).ready(function(){

    $.get("/login", function(data) {
        $("#user").html(data.name);
    });

    $(".survey").find("button").on('click', function(event){
        closeSurvey(this.id);
    });

    $(".survey").find("button").each(checkClosed(this.id));

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
    alert("Survey Closed!");
}

function checkClosed(id){
    apiUrl = window.location.origin + '/api/survey/'+id

    $.getJSON(apiURL, function(data) {
        if(data.closed){
            $('#'+id).attr("disabled", true);
            $('#'+id).html("Survey Closed");
            $('#'+id).attr('class',"btn btn-warning");
        }
    });

    $.get(apiURL, function(data) {
        if(data.closed){
            $('#'+id).attr("disabled", true);
            $('#'+id).html("Survey Closed");
            $('#'+id).attr('class',"btn btn-warning");
        }
    });
}