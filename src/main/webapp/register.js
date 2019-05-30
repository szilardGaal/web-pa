function sendRegisterData() {
    
    const registerFormEl = document.forms['register-form'];

    const userNameInputEl = registerFormEl.querySelector('input[name="register-name"]');
    const emailInputEl = registerFormEl.querySelector('select[name="register-email"]');
    const passwordInputEl = registerFormEl.querySelector('input[name="register-password"]');


    const username = userNameInputEl.value;
    const email = emailInputEl.value;
    const password = passwordInputEl.value;

    const params = new URLSearchParams();
    params.append('username', username);
    params.append('password', password);
    params.append('email', email);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onLoginResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'register');
    xhr.send(params);
}

function validatePassword() {

    const registerFormEl = document.forms['register-form'];
    var password1 = registerFormEl.querySelector('input[id="register-password"]').value;
    var password2 = registerFormEl.querySelector('input[id="confirm-password"]').value;
    var validateStatusEl = document.getElementById('validate-status');

    if(password1 == "" && password2 == "") {
        validateStatusEl.innerHTML = "<br>";
    } else if (password1 == password2) {
        validateStatusEl.innerHTML = "valid";
        validateStatusEl.style.color = "green";
    } else {
        validateStatusEl.innerHTML = "passwords don't match!";
        validateStatusEl.style.color = "red";
    }  
}

function onRegisterForm() {
    var validateStatusEl = document.getElementById('validate-status');

    if (validateStatusEl.innerHTML = "valid") {
        sendRegisterData();
    }
}

function onRegisterButtonClicked() {
    const registerFormEl = document.getElementById('register-form');
    hideContent(notLoggedInContentEl);
    showContent(registerFormEl);
    const submintRegisterButton = document.getElementById('submit-register-button');
    submintRegisterButton.addEventListener('click', onRegisterForm);
    const cancelRegisterButton = document.getElementById('cancel-register');
    cancelRegisterButton.addEventListener('click', () => {
        hideContent(registerFormEl);
        showContent(notLoggedInContentEl);
    });
}
