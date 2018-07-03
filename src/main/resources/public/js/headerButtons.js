$(function () {
    let loginCookie = Cookies.get('iplanetDirectoryPro');
    let divWithButtons = $('<div/>', {
        id: 'header_person'
    });
    if (loginCookie) {
        let logoutButton = $('<div/>', {
            id: "login_person",
        }).html('Выйти');
        logoutButton.click(function (event) {
            Cookies.remove('iplanetDirectoryPro');
            window.location.href = '//localhost:8080/login.html';
        });
        let logoutRef = $('<a/>');
        logoutRef.append(logoutButton);
        divWithButtons.append(logoutRef);
    } else {
        divWithButtons.html('<a href="login.html"><div id="login_person">Войти</div></a>')
    }

    $('header').prepend(divWithButtons);
});