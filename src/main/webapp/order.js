function onSendOrderResponse() {
    if (this.status === OK) {
        refreshUser();
    } else {
        onOtherResponse(leftBarDivEl, this);
    }
}

function sendOrder(itemIds, pcs) {
    const params = new URLSearchParams;
    params.append('ids', itemIds);
    params.append('pcs', pcs);
    
    const xhr = new XMLHttpRequest;
    xhr.addEventListener('load', onSendOrderResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'orders');
    xhr.send(params);
}

function onSubmitOrder() {
    if (user == null) {
        alert('Please sign in to submit your order!');
    } else {
        let itemIds = new Array();
        let pcs = new Array();
        for (let i = 1; i < cartTableEl.rows.length; i++) {
            itemIds.push(cartTableEl.rows[i].id.split('ct')[1]);
            pcs.push(cartTableEl.rows[i].cells[1].innerHTML);
        }
        sendOrder(itemIds, pcs);
    }  
}