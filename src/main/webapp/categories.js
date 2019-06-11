function onCategoryClicked() {
    const id = this.id.split('Id')[1];
    loadProducts(id);
}

function createCategoriesList(categories) {
    const categoriesFormEl = document.getElementById('filter-products');
    categoriesFormEl.appendChild(document.createElement('br'));

    for (let i = 0; i < categories.ids.length; i++ ) {
        const linkEl = document.createElement('a');
        linkEl.href = '#';
        linkEl.innerHTML = categories.names[i];
        linkEl.setAttribute('id', 'categoriesId' + categories.ids[i]);
        linkEl.addEventListener('click', onCategoryClicked);

        const spanEl = document.createElement('span');
        spanEl.setAttribute('class', 'filter-hidden');

        const inputEl = document.createElement('input');
        inputEl.setAttribute('type', 'checkbox');
        inputEl.setAttribute('id', 'categoryId' + categories.ids[i]);
        inputEl.setAttribute('class', 'hidden');

        const labelEl = document.createElement('label');
        labelEl.appendChild(inputEl);
        labelEl.appendChild(spanEl);
        

        categoriesFormEl.appendChild(labelEl);
        categoriesFormEl.appendChild(linkEl);
        categoriesFormEl.appendChild(document.createElement('br'));
    }

    const linkToAllEl = document.createElement('a');
    linkToAllEl.href = '#';
    linkToAllEl.innerHTML = '<i>all</i>';
    linkToAllEl.setAttribute('id', 'showAll');
    linkToAllEl.addEventListener('click', onCategoryClicked);

    const filterButtonEl = document.createElement('button');
    filterButtonEl.innerHTML = 'filter';
    filterButtonEl.setAttribute('id', 'filter-submit-button');
    filterButtonEl.setAttribute('class', 'filter-hidden');
    filterButtonEl.addEventListener('click', onSubmitFilterButtonClicked);

    categoriesFormEl.appendChild(linkToAllEl);
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