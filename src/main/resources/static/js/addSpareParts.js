let brand=$('#brand');
let model=$('#model');
let addBtn=$('#addSpareParts');
let addBrandBtn=$('#addAllSparePartsForBrand');

addBtn.click(() => {
    sparePartsList.empty();
    fetch('http://localhost:8080/spare-parts/spare-parts-add?brandName=' + brand[0].value + '&modelName=' + model[0].value)
        .then((response) => response.json())
        .then((json) => json.forEach((sp) => {
            let option = `<div><option value="${sp}" text="${sp}"></option></div>"`
            sparePartsList.append(option);
        }))
    $('#sparePart-group')[0].style.display = "block";
    $('#details')[0].style.display = "none";
})

addBrandBtn.click(() => {
    sparePartsList.empty();
    fetch('http://localhost:8080/spare-parts/spare-parts-for-brand?brandName=' + brand[0].value)
        .then((response) => response.json())
        .then((json) => json.forEach((sp) => {
            let option = `<div><option value="${sp}" text="${sp}"></option></div>"`
            sparePartsList.append(option);
        }))
    $('#sparePart-group')[0].style.display = "block";
    $('#details')[0].style.display = "none";
})