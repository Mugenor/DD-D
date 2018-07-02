$(function () {
    let registrationButton = $('#registrationButton');
    let errorDiv = $('<div/>', {
        class: 'error_reg'
    });
    registrationButton.click(function (event) {
        $('.error_reg').remove();
        let usernameRegexp = /^[a-zа-я0-9]+$/i;
        let emailRegexp = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        let username = $('#userName').val();
        let email = $('#email').val();
        let password = $('#password').val();
        let repeatPassword = $('#repeat_password').val();

        if(!usernameRegexp.test(username) || username.length < 4 || username.left > 10) {
            errorDiv.html('Имя пользователя должно состоять только из цифр, букв и должна быть в промежутке от 4 до 10 букв!');
            $('#button_div').before(errorDiv);
            return;
        }

        if(!emailRegexp.test(email)) {
            errorDiv.html('Введите валидный email!');
            $('#button_div').before(errorDiv);
            return;
        }

        if(password !== repeatPassword || password.length <=4) {
            errorDiv.html('Пароли не совпадают и пароль должен быть больше 5 символов!');
            $('#button_div').before(errorDiv);
            return
        }


        let userData = {
            username: username,
            mail: email,
            password: password
        };

        $.ajax('/register/user',{
            contentType: 'application/json',
            method: "POST",
            data: JSON.stringify(userData),
            success: function (resp) {
                $('#registration, .error_reg, #button_div').remove();
                errorDiv.removeClass('error_reg').html('Проверьте вашу почту!')
                    .appendTo($('#center'));
            },
            error: function (jqXHR, status, error) {
                console.log(jqXHR, status, error);
                $('#registrationButton').prop('disabled', false);
            },
            beforeSend: function () {
                $('#registrationButton').prop('disabled', true);
            }
        });
    })
});