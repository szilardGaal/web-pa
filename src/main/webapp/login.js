function createOrdersTable(orders, ordersTable) {

    for (let i = 0; i < orders.length; i ++) {
        let nextRow = document.createElement('tr');
        let serialEl = document.createElement('td');
        serialEl.innerHTML = orders[i].id;
        let dateEl = document.createElement('td');
        dateEl.innerHTML = orders[i].date.toString().slice(0,10).replace(/-/g,"");
        let totalEl = document.createElement('td');
        totalEl.innerHTML = orders[i].total;

        let viewButtonCell = document.createElement('td');
        let viewButton = document.createElement('button');
        viewButton.setAttribute('class', 'minimal-button');
        viewButton.setAttribute('data-order-id', orders[i].id);
        viewButtonCell.appendChild(viewButton);

        nextRow.appendChild(serialEl);
        nextRow.appendChild(dateEl);
        nextRow.appendChild(totalEl);
        nextRow.appendChild(viewButtonCell);

        ordersTable.appendChild(nextRow);
    }
}

function loadProfile(user) {

    const userNameFieldEl = document.getElementById('user-name-field');
    const italicEl = document.createElement('i');
    italicEl.innerHTML = user.userName;
    userNameFieldEl.appendChild(italicEl);

    const updateDataFormEl = document.forms['update-data-form'];
    const nameFieldEl = updateDataFormEl.querySelector('input[id="change-name"]');
    const emailFieldEl = updateDataFormEl.querySelector('input[id="change-email"');
    const passwordFieldEl = updateDataFormEl.querySelector('input[id="change-password"');
    const passwordConfirmFieldEL = updateDataFormEl.querySelector('input[id="change-password-confirm"');

    nameFieldEl.setAttribute('placeholder', user.userName);
    emailFieldEl.setAttribute('placeholder', user.email);
    passwordFieldEl.setAttribute('placeholder', 'password');
    passwordConfirmFieldEL.setAttribute('placeholder', 'confirm password');

    const ordersTable = document.getElementById('order-table');
    if (user.orders.length == 0) {
        hideContent(document.getElementById('order-table-header'));
        let textEl = document.createElement('p');
        textEl.innerHTML = 'you have no orders yet';
        ordersTable.appendChild(textEl);
    } else {
        createOrdersTable(user.orders, ordersTable);
    }
}

function loginUser(user) {
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
