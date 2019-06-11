<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <c:url value="/style.css" var="styleUrl"/>
        <c:url value="/index.js" var="indexScriptUrl"/>
        <c:url value="/login.js" var="loginScriptUrl"/>
        <c:url value="/register.js" var="registerScriptUrl"/>
        <c:url value="/logout.js" var="logoutScriptUrl"/>
        <c:url value="/categories.js" var="categoriesScriptUrl"/>
        <c:url value="/products.js" var="productsScriptUrl"/>
        <c:url value="/filter.js" var="filterScriptUrl"/>
        <c:url value="/cart.js" var="cartScriptUrl"/>
        <c:url value="/order.js" var="orderScriptUrl"/>
        
        <link rel="stylesheet" type="text/css" href="${styleUrl}">
        <script src="${indexScriptUrl}"></script>
        <script src="${loginScriptUrl}"></script>
        <script src="${registerScriptUrl}"></script>
        <script src="${logoutScriptUrl}"></script>
        <script src="${categoriesScriptUrl}"></script>
        <script src="${productsScriptUrl}"></script>
        <script src="${filterScriptUrl}"></script>
        <script src="${cartScriptUrl}"></script>
        <script src="${orderScriptUrl}"></script>
        
        <title>App</title>
    </head>
<body>
<div id="top-bar">
    <div id="logo-container">
        <img src="img/logo.png">
    </div>
    <div id="profile-content">
        <div id="not-logged-in-content">
            <p><i>you are not logged in.</i></p>
            <button id="register-button" class="minimal-button" onclick="onRegisterButtonClicked()">register</button>
            <button id="login-button" class="minimal-button" onclick="onLoginButtonClicked()">login</button>
        </div>
        <div id="logged-in-content" style="display: none;">
            <p id="user-name-field"></p>
            <div class="dropdown">
                <button id="my-profile" class="minimal-button">my profile</button>
                <div class="dropdown-content">
                    <form id="update-data-form">
                        <input type="text" id="change-name"><br>
                        <input type="email" id="change-email" readonly><br>
                        <input type="password" id="change-password"><br>
                        <input type="password" id="change-password-confirm"><br>
                        <br>
                        <button id="cancel-change" class="minimal-button">cancel</button>
                        <button id="submit-change-button" class="minimal-button">change</button>
                    </form>
                </div>
            </div>
            <div class="dropdown">
                <button id="my-orders" class="minimal-button">my orders</button>
                <div class="dropdown-content">
                    <table id="order-table">
                        <tr id="order-table-header">
                            <th>#</th>
                            <th>date</th>
                            <th>total ($)</th>
                            <th></th>
                        </tr>
                    </table>
                </div>
            </div>
            <div style="display: inline-block">
                <button id="logout-button" class="minimal-button" onclick="onLogoutButtonClicked()">logout</button>
            </div>
        </div>
       
        <form id="login-form" class="login-hidden" onsubmit="onLoginForm(); return false;">
            <p><i>login</i></p>
            <input type="email" placeholder="e-mail" id="login-email" required>
            <input type="password" placeholder="password" id="login-password" required>

            <button type="reset" id="cancel-login" class="minimal-button">cancel</button>
            <button type="submit" id="submit-login-button" class="minimal-button">login</button>
        </form>
            
        <form id="register-form" class="register-hidden" onsubmit="onRegisterForm(); return false;">
            <p><i>register</i></p>
            <div id="validate-status"></div>
            <input type="text" placeholder="name" id="register-name" required>
            <input type="email" placeholder="e-mail" id="register-email" required>
            <input type="password" placeholder="password" id="register-password" onkeyup="validatePassword()" required>
            <input type="password" placeholder="password again" id="confirm-password" onkeyup="validatePassword()" required>

            <button type="reset" id="cancel-register" class="minimal-button">cancel</button>
            <button type="submit" id="submit-register-button" class="minimal-button">register</button>
        </form>
    </div>
</div>
<div id="left-bar">
    <form id="search-field-form" onsubmit="searchByName(); return false;">
        <input id="search-field" type="text" placeholder="search by name">
        <button id="search-button" type="submit" class="minimal-button">search</button> 
    </form>
    <p><strong>categories</strong><button id="turn-on-filter-button" class="minimal-button" onclick="onTurnOnFilterButtonClicked()">filter</button></p>
    <form id="filter-products">
        <div id="filter-options" class="filter-hidden">
            <label><input type="checkbox" id="filter-select1" value="include" onclick="selectOnlyThis(this.id)" checked><span class="filter-hidden"></span> include <br></label>
            <label><input type="checkbox" id="filter-select2" value="exclude" onclick="selectOnlyThis(this.id)"><span class="filter-hidden"></span> exclude <br></label>
            <hr>
            <br>
        </div>
    </form>
    <p><strong>cart</strong></p>
        <table id="cart-table">
            <tr>
                <th>product</th>
                <th>pc</th>
                <th>$/pc</th>
                <th>subtotal</th>
                <th></th>
            </tr>
        </table>
        total: <strong id="cart-total"></strong>
        <button id="submit-order" onclick="onSubmitOrder()">submit order</button><br>
        <button id="cancel-cart" class="minimal-button" onclick="onCancelCart()">cancel</button>
</div>
<div id="shop-content">

</div>

</body>
</html>
