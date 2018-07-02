$(function () {
    let errorDiv = $('<div/>', {
        id: 'error_login',
    });
    $("#loginButton").click(function () {
        let userCredentials = {username: $("#userName").val(), password: $("#password").val()};
        if(userCredentials.username.length === 0 || userCredentials.password.length === 0) {
            $('#error_login').remove();
            errorDiv.html('Вы забыли ввести логин или пароль!');
            $('#login').after(errorDiv);
            return;
        }
        $.ajax({
            url: "/auth/login",
            contentType: "application/json",
            method: "POST",
            data: JSON.stringify(userCredentials),
            success: function (resp) {
                debugger;
                Cookies.set('iplanetDirectoryPro', resp.tokenId);
                window.location.href = '//localhost:8080/game.html';
            },
            error: function (jqXHR, textStatus, errorThrown) {
                $('#error_login').remove();
                errorDiv.html('Неверный логин или пароль!');
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