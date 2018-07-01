$(function () {
    let errorDiv = $('<div/>', {
        id: 'error',
        class: 'alert alert-danger',
    });
    let errorMessage = $('<p/>').html('Неправильный логин или пароль!');
    errorDiv.append(errorMessage.html());
    $("#loginButton").click(function () {
        let userCredentials = {username: $("#userName").val(), password: $("#password").val()};
        $.ajax({
            url: "/auth/login",
            contentType: "application/json",
            method: "POST",
            data: JSON.stringify(userCredentials),
            success: function (resp) {
                Cookies.set('iplanetDirectoryPro', resp.tokenId);
                window.location.href = '//localhost:8080/game.html';
            },
            error: function (jqXHR, textStatus, errorThrown) {
                errorDiv.appendTo('#password-div');
            }
        });
    });

    console.log($("#registrationButton"));
    $("#registrationButton").click(function (event) {
        console.log(event);
        window.location.href = '//localhost:8080/registration.html';
    });
});