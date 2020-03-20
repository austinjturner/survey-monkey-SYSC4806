
$(document).ready(function(){

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
