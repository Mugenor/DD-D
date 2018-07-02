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

        if(!usernameRegexp.test(username)) {
            errorDiv.html('Имя пользователя должно состоять только из цифр, букв!');
            $('#button_div').before(errorDiv);
            return;
        }

        if(username.length < 4 || username.length > 10) {
            errorDiv.html('Имя пользователя должно быть быть в промежутке от 4 до 10 букв!');
            $('#button_div').before(errorDiv);
            return;
        }

        if(!emailRegexp.test(email)) {
            errorDiv.html('Введите валидный email!');
            $('#button_div').before(errorDiv);
            return;
        }

        if(password.length <= 4) {
            errorDiv.html('Пароль должен быть больше 5 символов!');
            $('#button_div').before(errorDiv);
            return;
        }

        if(password !== repeatPassword) {
            errorDiv.html('Пароли не совпадают!');
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
                $('#center').addClass('answer_reg')
                    .html('Перейдите по ссылке в письме, которое было выслано вам на почту, чтобы закончить регистрацию.');
            },
            error: function (jqXHR, status, error) {
                console.log(jqXHR, status, error);
                errorDiv.html(jqXHR.responseJSON.message);
                $('#button_div').before(errorDiv);
                $('#registrationButton').prop('disabled', false);
            },
            beforeSend: function () {
                $('#registrationButton').prop('disabled', true);
            }
        });
    })
});