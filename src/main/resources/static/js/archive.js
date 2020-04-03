
$(document).ready(function(){

    $.get("/login", function(data) {
        $("#user").html(data.name);
    });

    $(".survey").find("button").on('click', function(event){
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
    checkClosed(id);
}

async function checkClosed(id){
    getSurvey(id).then(async function(val){
       if(val.closed){
            await $('#'+id).attr("disabled", true);
            await $('#'+id).html("Survey Closed");
            await $('#'+id).attr('class',"btn btn-warning");
        }
    });
}

function getSurvey(id) {
    return new Promise((resolve, reject) => {
        apiUrl = window.location.origin + '/api/survey/' + id
        $.ajax({
            url: apiUrl,
            type: 'GET',
            success: function(data) {
                resolve(data)
            },
            error: function(error) {
                reject(error)
            },
        })
    })
}

