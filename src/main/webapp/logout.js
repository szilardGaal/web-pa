function onLogoutResponse() {
    if (this.status === OK) {
        localStorage.removeItem('user');
        user = null;
        clearMessages();
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
