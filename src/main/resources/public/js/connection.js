$(function () {
    let butt = $('#submit');
    let textarea = $('textarea');

    butt.click(function (event) {
        let message = textarea.val().trim();
        if(message === "") {
            return;
        }

        $.ajax('/feedback', {
            method: "POST",
            contentType: 'application/json',
            data: JSON.stringify({message: message}),
            success: function (resp) {
                $('textarea, #button_div, #connection').remove();
                $('#center').addClass('answer_con').html('Ваше сообщение успешно отправлено!');
            },
            error: function (jqXHR, status, error) {
                console.log(jqXHR, status, error);
                $('textarea, #button_div, #connection').remove();
                $('#center').addClass('answer_con').html('Не удалось отправить сообщение!');
            },
            beforeSend: function () {
                butt.prop('disabled', true);
            }
        });
    })
});