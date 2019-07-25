function onRefreshUserResponse() {
    if (this.status === OK) {
        debugger;
        user = JSON.parse(this.responseText);
        loginUser(user);
        onCancelCart();
    } else {
        onNetworkError(leftBarDivEl, this);
    }
}

function refreshUser() {
    const params = new URLSearchParams;
    params.append('user', user.id);

    const xhr = new XMLHttpRequest;
    xhr.addEventListener('load', onRefreshUserResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'update?' + params);
    xhr.send();
}

function updateData(newName, newPassword) {
    const params = new URLSearchParams;
    params.append('userId', user.id);
    params.append('name', newName);
    params.append('password', newPassword);

    const xhr = new XMLHttpRequest;
    xhr.addEventListener('load', onRefreshUserResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('PUT', 'update?' + params);
    xhr.send();
}

function onChangeDataClicked() {
    const updateDataFormEl = document.forms['update-data-form'];
    const newName = updateDataFormEl.querySelector('input[id="change-name"]').value;
    const newPassword = updateDataFormEl.querySelector('input[id="change-password"').value;
    const newPasswordConfirm = updateDataFormEl.querySelector('input[id="change-password-confirm"').value;

    if (newPassword === newPasswordConfirm) {
        updateData(newName, newPassword);
    } else {
        alert('passwords don,\t match!');
    }
}

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
    italicEl.innerHTML = 'Welcome ' + user.userName;
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