const OK = 200;
const BAD_REQUEST = 400;
const UNAUTHORIZED = 401;
const NOT_FOUND = 404;
const INTERNAL_SERVER_ERROR = 500;

let user;

let notLoggedInContentEl;
let loggedInContentEl;
let leftBarDivEl;
let cartTableEl;
let shopContentDivEl;

function newInfo(targetEl, message) {
    newMessage(targetEl, 'info', message);
}

function newError(targetEl, message) {
    newMessage(targetEl, 'error', message);
}

function newMessage(targetEl, cssClass, message) {
    clearMessages();

    const pEl = document.createElement('p');
    pEl.classList.add('message');
    pEl.classList.add(cssClass);
    pEl.textContent = message;

    targetEl.appendChild(pEl);
}

function clearMessages() {
    const messageEls = document.getElementsByClassName('message');
    for (let i = 0; i < messageEls.length; i++) {
        const messageEl = messageEls[i];
        messageEl.remove();
    }
}

function showContents(ids) {
    const contentEls = document.getElementsByClassName('content');
    for (let i = 0; i < contentEls.length; i++) {
        const contentEl = contentEls[i];
        if (ids.includes(contentEl.id)) {
            contentEl.classList.remove('hidden');
        } else {
            contentEl.classList.add('hidden');
        }
    }
}

function showContent(content) {
    content.style.display = 'block';
}

function hideContent(content) {
    content.style.display = 'none';
}

function removeAllChildren(el) {
    while (el.firstChild) {
        el.removeChild(el.firstChild);
    }
}

function onNetworkError(response) {
    document.body.remove();
    const bodyEl = document.createElement('body');
    document.appendChild(bodyEl);
    newError(bodyEl, 'Network error, please try reloaing the page');
}

function onOtherResponse(targetEl, xhr) {
    if (xhr.status === NOT_FOUND) {
        newError(targetEl, 'Not found');
        console.error(xhr);
    } else {
        const json = JSON.parse(xhr.responseText);
        if (xhr.status === INTERNAL_SERVER_ERROR) {
            newError(targetEl, `Server error: ${json.message}`);
        } else if (xhr.status === UNAUTHORIZED || xhr.status === BAD_REQUEST) {
            newError(targetEl, json.message);
        } else {
            newError(targetEl, `Unknown error: ${json.message}`);
        }
    }
}

function onLoad() {
    notLoggedInContentEl = document.getElementById('not-logged-in-content');
    leftBarDivEl = document.getElementById('left-bar');
    loggedInContentEl = document.getElementById('logged-in-content');
    cartTableEl = document.getElementById('cart-table');
    shopContentDivEl = document.getElementById('shop-content');

    const loginButtonEl = document.getElementById('login-button');
    const registerButtonEl = document.getElementById('register-button');
    const searchButtonEl = document.getElementById('search-button');
    const turnOnFilterButtonEl = document.getElementById('turn-on-filter-button');
    const submitOrderButtonEl = document.getElementById('submit-order');
    const cancelOrderButtonEl = document.getElementById('cancel-order');

    loginButtonEl.addEventListener('click', onLoginButtonClicked);
    registerButtonEl.addEventListener('click', onRegisterButtonClicked);
    /*searchButtonEl.addEventListener('click', onSearchButtonClicked);
    turnOnFilterButtonEl.addEventListener('click', onTurnOnFilterButtonClicked);
    submitOrderButtonEl.addEventListener('click', onSubmitOrderButtonClicked);
    cancelOrderButtonEl.addEventListener('click', onCancelOrderButtonClicked);*/

}

document.addEventListener('DOMContentLoaded', onLoad);
