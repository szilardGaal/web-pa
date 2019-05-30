function createOrdersTable(orders, ordersTable) {

    for (let i = 0; i < orders.length; i ++) {
        let nextRow = document.createElement('tr');
        let serialEl = document.createElement('td');
        serialEl.innerHTML = orders[i].id;
        let dateEl = document.createElement('td');
        dateEl.innerHTML = orders[i].date;
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
    italicEl.innerHTML = user.name;
    userNameFieldEl.appendChild(italicEl);

    const updateDataFormEl = document.forms['update-data-form'];
    const nameFieldEl = updateDataFormEl.querySelector('input[id="change-name"]');
    const emailFieldEl = updateDataFormEl.querySelector('input[id="change-email"');
    const passwordFieldEl = updateDataFormEl.querySelector('input[id="change-password"');
    const passwordConfirmFieldEL = updateDataFormEl.querySelector('input[id="change-password-confirm"');

    nameFieldEl.setAttribute('placeholder', user.name);
    emailFieldEl.setAttribute('placeholder', user.emai);
    passwordFieldEl.setAttribute('placeholder', 'password');
    passwordConfirmFieldEL.send('placeholder', 'confirm password');

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
    debugger;
    hideContent(document.getElementById('login-form'));
    hideContent(document.getElementById('register-form'));
    loadProfile(user);
    showContent(loggedInContentEl);
}

function onLoginResponse() {
    debugger;
    if (this.status === OK) {
        //const user = JSON.parse(this.responseText);
        user = '<%= Session["user"] %>';
        loginUser(user);
    } else {
        onOtherResponse(loginContentDivEl, this);
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

    const submitLoginButtonEl = document.getElementById('submit-login-button');
    submitLoginButtonEl.addEventListener('click', onLoginForm);
    const cancelLoginButtonEl = document.getElementById('cancel-login');
    cancelLoginButtonEl.addEventListener('click', () => {
        hideContent(loginFormEl);
        showContent(notLoggedInContentEl);
    });
}
