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
        <!--<c:url value="/shop.js" var="shopScriptUrl"/>
        <c:url value="/coupon.js" var="couponScriptUrl"/>
        <c:url value="/coupons.js" var="couponsScriptUrl"/>
        <c:url value="/back-to-profile.js" var="backToProfileScriptUrl"/>
        -->
        <link rel="stylesheet" type="text/css" href="${styleUrl}">
        <script src="${indexScriptUrl}"></script>
        <script src="${loginScriptUrl}"></script>
        <script src="${registerScriptUrl}"></script>
        <script src="${logoutScriptUrl}"></script>
        <script src="${categoriesScriptUrl}"></script>
        <!--<script src="${shopScriptUrl}"></script>
        <script src="${couponScriptUrl}"></script>
        <script src="${couponsScriptUrl}"></script>
        <script src="${backToProfileScriptUrl}"></script>
        -->
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
            <button id="register-button" class="minimal-button">register</button>
            <button id="login-button" class="minimal-button">login</button>
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
    <form id="search-field">
        <input type="text" placeholder="search by name">
        <button id="search-button" class="minimal-button">search</button> 
    </form>
    <p><strong>categories</strong><button id="turn-on-filter-button" class="minimal-button">filter</button></p>
    <form id="filter-products">
        <div id="filter-options" class="filter-hidden">
            <label><input type="checkbox" id="filter-include"><span class="filter-hidden"></span> include <br></label>
            <label><input type="checkbox" id="filter-exclude"><span class="filter-hidden"></span> exclude <br></label>
            <hr>
            <br>
        </div>
        <!-- adding onclilck events needed! 
        <label><input type="checkbox" id="electric guitars" class="filter-hidden"><span class="filter-hidden"></span><a> electric guitars</a></label><br>
        <label><input type="checkbox" id="acoustic guitars" class="filter-hidden"><span class="filter-hidden"></span><a> acoustic guitars</a></label><br>
        <label><input type="checkbox" id="electric basses" class="filter-hidden"><span class="filter-hidden"></span><a> electric basses</a></label><br>
        <label><input type="checkbox" id="acoustic basses" class="filter-hidden"><span class="filter-hidden"></span><a> acoustic basses</a></label><br>
        <label><input type="checkbox" id="filter-exclude" class="filter-hidden"><span class="filter-hidden"></span><a> valve amplifiers</a></label><br>
        <label><input type="checkbox" id="filter-exclude" class="filter-hidden"><span class="filter-hidden"></span><a> tube amplifiers</a></label><br>
        <label><input type="checkbox" id="filter-exclude" class="filter-hidden"><span class="filter-hidden"></span><a> hybrid amplifiers</a></label><br>
        <label><input type="checkbox" id="filter-exclude" class="filter-hidden"><span class="filter-hidden"></span><a> string sets</a></label><br>
        <label><input type="checkbox" id="filter-exclude" class="filter-hidden"><span class="filter-hidden"></span><a> cables</a></label><br>
        <br>
        <button id="filter-submit-button" class="filter-hidden">filter</button>-->
    </form>
    <p><strong>cart</strong></p>
        <table id="cart-table">
            <tr>
                <th>product</th>
                <th>pc</th>
                <th>$/pc</th>
                <th>total</th>
                <th></th>
            </tr>
            <tr id="orderId">
                <td>Fender Stratocaster</td>
                <td>3</td>
                <td>700</td>
                <td>2100</td>
                <td><button class="minimal-button">X</button></td>
            </tr>
            <tr id="orderId">
                <td>Gibson SG</td>
                <td>1</td>
                <td>850</td>
                <td>850</td>
                <td><button class="minimal-button">X</button></td>
            </tr>
        </table>
        
        <button id="submit-order">submit order</button><br>
        <button id="cancel-order" class="minimal-button">cancel</button>
</div>
<div id="shop-content">
    <p>Selected category</p>
    <div class="item-container">
        <div class="item-image-container"><img src="img/fenderstrat.jpg"></div>
        <div class="item-name"><p><strong>Fender Stratocaster</strong></p></div>
        <div class="item-price-container">
            <p><strong>700$</strong></p>
        </div>
        <div class="add-to-cart">
            <form id="buy-item-form">
                <select>
                    <option>1</option>
                    <option>2</option>
                    <option>3</option>
                </select>
                <button>add to cart</button> <!-- data-product-id -->
            </form>
        </div>
    </div>
    <div class="item-container">
        
    </div>
    <div class="item-container">
        
    </div>
    <div class="item-container">
        
    </div>
    <div class="item-container">
        
    </div>
    <div class="item-container">
        
    </div>
    <div class="item-container">
        
    </div>
    <div class="item-container">
        
    </div>
    <div class="item-container">
        
    </div>
    <div class="item-container">
        
    </div>
    <div class="item-container">
        
    </div>
    <div class="item-container">
        
    </div>

</div>

</body>
</html>
