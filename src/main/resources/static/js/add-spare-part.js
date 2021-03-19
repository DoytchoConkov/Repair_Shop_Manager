let brand = $('#brand');
let models = $('#modelsList');
let spareParts = $('#sparePartNameList');

brand.change(() => {
    models.empty();
    models.append('<option value="" selected>Select model</option>');
    fetch('http://localhost:8080/spare-parts/models?brandName=' + brand[0].value)
        .then((response) => response.json())
        .then((json) => json.forEach((ml) => {
            let option = `<option value="${ml}">${ml}</option>`;
            models.append(option);
        }))
})
$('#model').change(() => {
    spareParts.empty();
    spareParts.append('<option value="" selected>Select spare part</option>');
    fetch('http://localhost:8080/spare-parts/spare-parts?brandName=' + brand[0].value + '&modelName=' + $('#model')[0].value)
        .then((response) => response.json())
        .then((json) => json.forEach((sp) => {
            let name=sp.sparePartName;
            let option = `<option >${name}</option>`;
            spareParts.append(option);
        }))
})