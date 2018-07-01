$(function () {
    let registrationButton = $('#registrationButton');
    registrationButton.click(function (event) {
        let usernameRegexp = /[a-zа-я0-9/i;
        let username = $('#userName').val();
        let email = $('#email').val();
        let password = $('#password');
        let repeatPassword = $('#repeat_password');

        if(!usernameRegexp.test(username)) {

            return;
        }



        let userData = {
            username: username,
            email: email,
            password: password
        };
        $.ajax('/registration/user',{
            contentType: 'application/json',
            method: "POST",
            data: JSON.stringify(userData),
            success: function (resp) {

            },
            error: function (jqXHR, status, error) {

            }
        });
    })
});