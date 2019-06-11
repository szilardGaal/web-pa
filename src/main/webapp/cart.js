function onCancelCart() {
    var rows = cartTableEl.rows;
    var i = rows.length;
    while (--i) {
        cartTableEl.deleteRow(i);
    }
    const cartTotalEl = document.getElementById('cart-total');
    cartTotalEl.innerHTML = 0;
}

function removeProduct() {
    const cartTotalEl = document.getElementById('cart-total');
    const rowToRemove = document.getElementById('product'+this.getAttribute('product-id'));
    const rowToRemoveId = rowToRemove.rowIndex;
    cartTotalEl.innerHTML = cartTotalEl.innerHTML - rowToRemove.cells[3].innerHTML;
    cartTableEl.deleteRow(rowToRemoveId);
}

function addProductToCart(product) {
    const cartTotalEl = document.getElementById('cart-total');
    const idTrEl = document.createElement('tr');
    idTrEl.setAttribute('id', 'product'+product.id);
    nameTdEl = document.createElement('td');
    nameTdEl.innerHTML = product.name;
    const pcsTdEl = document.createElement('td');
    pcsTdEl.innerHTML = document.getElementById('select'+product.id).value;
    const priceTdEl = document.createElement('td');
    priceTdEl.innerHTML = product.price;
    const totalTdEl = document.createElement('td');
    totalTdEl.innerHTML = pcsTdEl.innerHTML * priceTdEl.innerHTML;
    cartTotalEl.innerHTML = + cartTotalEl.innerHTML + + totalTdEl.innerHTML;
    const removeButtonTdEl = document.createElement('td');
    const removeButton = document.createElement('button');
    removeButton.setAttribute('product-id', product.id);
    removeButton.addEventListener('click', removeProduct);
    removeButton.innerHTML = 'X';
    removeButton.setAttribute('class', 'minimal-button');
    removeButtonTdEl.appendChild(removeButton);

    idTrEl.appendChild(nameTdEl);
    idTrEl.appendChild(pcsTdEl);
    idTrEl.appendChild(priceTdEl);
    idTrEl.appendChild(totalTdEl);
    idTrEl.appendChild(removeButtonTdEl);

    cartTableEl.appendChild(idTrEl);
}

function onAddToCartResponse() {
    if (this.status === OK) {
        product = JSON.parse(this.responseText);
        addProductToCart(product);
    } else {
        onOtherResponse(leftBarDivEl, this);
    }
}

function onAddToCartClicked() {
    const thisItemId = this.id.split('Id')[1];

    const params = new URLSearchParams;
    params.append('id', thisItemId);

    const xhr = new XMLHttpRequest;
    xhr.addEventListener('load', onAddToCartResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'products?' + params);
    xhr.send();
}