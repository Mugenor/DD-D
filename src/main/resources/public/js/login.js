$(function () {
    let errorDiv = $('<div/>', {
        id: 'error_login',
    }).html('Неверный логин или пароль!');
    $("#loginButton").click(function () {
        let userCredentials = {username: $("#userName").val(), password: $("#password").val()};
        if(userCredentials.username.length === 0 || userCredentials.password.length === 0) {
            return;
        }
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
                $('#login').after(errorDiv);
            }
        });
    });

    console.log($("#registrationButton"));
    $("#registrationButton").click(function (event) {
        console.log(event);
        window.location.href = '//localhost:8080/registration.html';
    });
});