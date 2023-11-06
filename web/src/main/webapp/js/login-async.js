$(document).ready(function() {
    $('#loginForm').submit(function(event) {
        event.preventDefault();
        var formData = {
            username: $('input[name="username"]').val(),
            password: $('input[name="password"]').val()
        };
        $.ajax({
            async: true,
            crossDomain: true,
            method: $('#loginForm').attr("method"),
            url:  "/loginAsync",
            dataType: 'json',
            data: formData,
            complete: function(response){
                var data = $.parseJSON(response.responseText)
                console.log(data.result);
                if (data.result == false){
                    alert("Ervenytelen belepesi adatok!") //update in box TODO
                }else {
                    window.location.href = "secured/profile.html";

                }
            }
        })
    })
});





