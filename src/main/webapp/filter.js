function searchByName() {
    const searchFieldVal = document.getElementById('search-field').value;
    const params = new URLSearchParams;
    params.append('name', searchFieldVal);

    const xhr = new XMLHttpRequest;
    xhr.addEventListener('load', onLoadProductsResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'filter?' + params);
    xhr.send();
}

function loadFilteredProducts(ids, includeOrExclude) {
    const params = new URLSearchParams;
    params.append('inOrEx', includeOrExclude);
    params.append('ids', ids);

    const xhr = new XMLHttpRequest;
    xhr.addEventListener('load', onLoadProductsResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'filter');
    xhr.send(params);
}

function onSubmitFilterButtonClicked() {
    const includeOrExclude = document.querySelector('[id^="filter-select"]:checked').value;
    const selectedCategories = document.querySelectorAll('[id^="categoryId"]:checked');
    let ids = new Array;
    for (let i = 0; i < selectedCategories.length; i ++) {
        ids.push(selectedCategories[i].id.split('Id')[1]);
    }
    loadFilteredProducts(ids, includeOrExclude);
}

function onTurnOnFilterButtonClicked() {
    let filterObjects = document.querySelectorAll('[class="filter-hidden"]');
    let showAllButton = document.getElementById('showAll');
    if (showAllButton.style.display != 'none') {
        hideContent(showAllButton);
    } else {
        showContent(showAllButton);
    }

    for (let i = 0; i < filterObjects.length; i ++) {
        if (filterObjects[i].style.display === 'inline-block') {
            hideContent(filterObjects[i]);
        } else {
            showContent(filterObjects[i]);
        }
    }
}

function selectOnlyThis(id) {
    for (let i = 1; i <= 2; i++)
    {
        document.getElementById("filter-select" + i).checked = false;
    }
    document.getElementById(id).checked = true;
}