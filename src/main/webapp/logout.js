function clearProfile() {
    const userNameFieldEl = document.getElementById('user-name-field');
    removeAllChildren(userNameFieldEl);
    const orderTableEl = document.getElementById('order-table');
    var rows = orderTableEl.rows;
    var i = rows.length;
    while (--i) {
        orderTableEl.deleteRow(i);
    }
}

function onLogoutResponse() {
    if (this.status === OK) {
        localStorage.removeItem('user');
        user = null;
        clearMessages();
        clearProfile();
        hideContent(loggedInContentEl);
        showContent(notLoggedInContentEl);
    } else {
        onOtherResponse(logoutContentDivEl, this);
    }
}

function onLogoutButtonClicked() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onLogoutResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'protected/logout');
    xhr.send();
}
