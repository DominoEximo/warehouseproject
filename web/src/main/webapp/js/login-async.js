
    $('#loginForm').submit(function (event){
        event.preventDefault();
        $.ajax({
            async: true,
            crossDomain: true,
            method: $().attr("method"),
            url: $('#loginForm').attr("action"),
            dataType: 'json',
            success: function (response){

                console.log("success")

            },
            error: function (error){
                console.log("error")
            },
            complete: function (){
                console.log("complete")
            }
        })
    })





