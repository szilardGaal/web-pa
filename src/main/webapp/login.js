function loginUser(user) {
    clearProfile();
    localStorage.setItem('user', JSON.stringify(user));
    hideContent(document.getElementById('login-form'));
    hideContent(document.getElementById('register-form'));
    loadProfile(user);
    hideContent(notLoggedInContentEl);
    showContent(loggedInContentEl);
}

function onLoginResponse() {
    if (this.status === OK) {
        user = JSON.parse(this.responseText);
        loginUser(user);
    } else {
        onOtherResponse(notLoggedInContentEl, this);
    }
}

function onLoginForm() {
    const loginFormEl = document.forms['login-form'];

    const emailInputEl = loginFormEl.querySelector('input[id="login-email"]');
    const passwordInputEl = loginFormEl.querySelector('input[id="login-password"]');

    const email = emailInputEl.value;
    const password = passwordInputEl.value;

    const params = new URLSearchParams();
    params.append('email', email);
    params.append('password', password);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onLoginResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'login');
    xhr.send(params); 
}

function onLoginButtonClicked() {
    const loginFormEl = document.getElementById('login-form');
    hideContent(notLoggedInContentEl);
    showContent(loginFormEl);

    const cancelLoginButtonEl = document.getElementById('cancel-login');
    cancelLoginButtonEl.addEventListener('click', () => {
        hideContent(loginFormEl);
        showContent(notLoggedInContentEl);
    });
}
