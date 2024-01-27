function handleSortChange() {
    var selectedOption = document.getElementById('sort').value;
    window.location.href = '/listings?sort=' + selectedOption;
}