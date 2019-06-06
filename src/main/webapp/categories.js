function onSelectCategoryResponse() {
    if (this.status === OK) {
        products = JSON.parse(this.responseText);
        displayProducts(products);
    } else {
        onOtherResponse(notLoggedInContentEl, this);
    }
}

function onCategoryClicked() {
    const id = this.firstChild.id.split('Id')[1];

    const xhr = new XMLHttpRequest();
    const params = new URLSearchParams();

    params.append('id', id);
    xhr.addEventListener('load', onSelectCategoryResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'products');
    xhr.send(params);
}

function createCategoriesList(categories) {
    const categoriesFormEl = document.getElementById('filter-products');

    for (let i = 0; i < categories.ids.length; i++ ) {
        const linkEl = document.createElement('a');
        linkEl.innerHTML = categories.names[i];

        const spanEl = document.createElement('span');
        spanEl.setAttribute('class', 'filter-hidden');

        const inputEl = document.createElement('input');
        inputEl.setAttribute('type', 'checkbox');
        inputEl.setAttribute('id', 'categoriesId' + categories.ids[i]);
        inputEl.setAttribute('class', 'filter-hidden');

        const labelEl = document.createElement('label');
        labelEl.appendChild(inputEl);
        labelEl.appendChild(spanEl);
        labelEl.appendChild(linkEl);
        labelEl.addEventListener('click', onCategoryClicked);

        categoriesFormEl.appendChild(labelEl);
        categoriesFormEl.appendChild(document.createElement('br'));
    }

    const filterButtonEl = document.createElement('button');
    filterButtonEl.setAttribute('id', 'filter-submit-button');
    filterButtonEl.setAttribute('class', 'filter-hidden');

    categoriesFormEl.appendChild(document.createElement('br'));
    categoriesFormEl.appendChild(filterButtonEl);
}

function onCategoriesResponse() {
    if (this.status === OK) {
        categories = JSON.parse(this.responseText);
        createCategoriesList(categories);
    } else {
        onOtherResponse(leftBarDivEl, this);
    }
}

function loadCategories() {
    const xhr = new XMLHttpRequest;
    xhr.addEventListener('load', onCategoriesResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'categories');
    xhr.send();
}