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
        <c:url value="/profile.js" var="profileScriptUrl"/>
        <c:url value="/shop.js" var="shopScriptUrl"/>
        <c:url value="/shops.js" var="shopsScriptUrl"/>
        <c:url value="/coupon.js" var="couponScriptUrl"/>
        <c:url value="/coupons.js" var="couponsScriptUrl"/>
        <c:url value="/back-to-profile.js" var="backToProfileScriptUrl"/>
        <c:url value="/logout.js" var="logoutScriptUrl"/>
        <link rel="stylesheet" type="text/css" href="${styleUrl}">
        <script src="${indexScriptUrl}"></script>
        <script src="${loginScriptUrl}"></script>
        <script src="${profileScriptUrl}"></script>
        <script src="${shopScriptUrl}"></script>
        <script src="${shopsScriptUrl}"></script>
        <script src="${couponScriptUrl}"></script>
        <script src="${couponsScriptUrl}"></script>
        <script src="${backToProfileScriptUrl}"></script>
        <script src="${logoutScriptUrl}"></script>
        <title>App</title>
    </head>
<body>
<div id="top-bar">
    <div id="logo-container">

    </div>
    <div id="profile-content">

    </div>
</div>
<div id="left-bar">

</div>
<div id="shop-content">
    <p>Selected category</p>
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
    <div class="item-container">
        
    </div>

</div>

</body>
</html>
