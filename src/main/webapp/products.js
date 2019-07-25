function createProductsPage(products) {

    const shopContentDiv = document.getElementById('shop-content');
    removeAllChildren(shopContentDiv);
    
    for (let i = 0; i < products.length; i ++) {
        const itemContainerDiv = document.createElement('div');
        itemContainerDiv.setAttribute('id', 'divItemId' + products[i].id);
        itemContainerDiv.setAttribute('class', 'item-container');
        const imageContainerDiv = document.createElement('div');
        imageContainerDiv.setAttribute('class', 'item-image-container');
        const imgElement = document.createElement('img');
        imgElement.src = products[i].imgLink;
        imageContainerDiv.appendChild(imgElement);

        const itemNameDiv = document.createElement('div');
        itemNameDiv.setAttribute('class', 'item-name');
        const strongItemName = document.createElement('strong');
        strongItemName.innerHTML = products[i].manufacturer + ' ' + products[i].name;
        const strongItemP = document.createElement('p');
        strongItemP.appendChild(strongItemName);
        itemNameDiv.appendChild(strongItemP);

        const itemPriceDiv = document.createElement('div');
        itemPriceDiv.setAttribute('class', 'item-price-container');
        const strongItemPrice = document.createElement('strong');
        strongItemPrice.innerHTML = products[i].price + '$';
        const strongItemPriceP = document.createElement('p');
        strongItemPriceP.appendChild(strongItemPrice);
        itemPriceDiv.appendChild(strongItemPriceP);
        
        const addToCartFormDiv = document.createElement('div');
        addToCartFormDiv.setAttribute('class', 'add-to-cart');
        const addToCartForm = document.createElement('form');
        addToCartForm.setAttribute('class', 'buy-item-form');
        addToCartForm.setAttribute('onsubmit', 'return false;');
        const selectEl = document.createElement('select');
        selectEl.setAttribute('id', 'select' + products[i].id);
        for (let k = 1; k < 11; k++) {
            const optionEl = document.createElement('option');
            optionEl.innerHTML = k;
            selectEl.appendChild(optionEl);
        }
        const addToCartButton = document.createElement('button');
        addToCartButton.setAttribute('id', 'itemId' + products[i].id);
        addToCartButton.addEventListener('click', onAddToCartClicked);
        addToCartButton.innerHTML = 'add to cart';
        addToCartForm.appendChild(selectEl);
        addToCartForm.appendChild(addToCartButton);
        addToCartFormDiv.appendChild(addToCartForm);

        itemContainerDiv.appendChild(imageContainerDiv);
        itemContainerDiv.appendChild(itemNameDiv);
        itemContainerDiv.appendChild(itemPriceDiv);
        itemContainerDiv.appendChild(addToCartFormDiv);

        shopContentDiv.appendChild(itemContainerDiv);
    }
}

function onLoadProductsResponse() {
    if (this.status === OK) {
        products = JSON.parse(this.responseText);
        createProductsPage(products);
    } else {
        onOtherResponse(leftBarDivEl, this);
    }
}

function loadProducts(id) {

    const params = new URLSearchParams;
    params.append('id', id);

    const xhr = new XMLHttpRequest;
    xhr.addEventListener('load', onLoadProductsResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'products');
    xhr.send(params);
}