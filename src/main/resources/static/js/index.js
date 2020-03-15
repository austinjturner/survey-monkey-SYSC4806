
$(document).ready(() => {
    // Setup login request
    $.get("/login", function(data) {
        $("#user").html(data.name);
//        $(".unauthenticated").hide();
//        $(".authenticated").show()
        window.location.href ="/home"
    });
});

// logout function
const logout = function() {
    $.post("/logout", function() {
        $("#user").html('');
        $(".unauthenticated").show();
        $(".authenticated").hide();
    });
    return true;
};

