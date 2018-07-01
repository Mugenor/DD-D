$(function () {
    let loginCookie = Cookies.get('iplanetDirectoryPro');
    let divWithButtons = $('<div/>', {
        id: 'header_person'
    });
    if (loginCookie) {
        divWithButtons.html('<a><img id="img_person" src="img/person5.png" alt="Person"></a><br>');
        let logoutButton = $('<div/>', {
            id: "login_person",
        }).html('Выйти');
        logoutButton.click(function (event) {
            Cookies.remove('iplanetDirectoryPro');
            window.location.href = '//localhost:8080/login.html';
            console.log(Cookies.remove('iplanetDirectoryPro'));
        });
        let logoutRef = $('<a/>');
        logoutRef.append(logoutButton);
        divWithButtons.append(logoutRef);
    } else {
        divWithButtons.html('<a href="login.html"><div id="login_person">Войти</div></a>')
    }

    $('header').prepend(divWithButtons);
});