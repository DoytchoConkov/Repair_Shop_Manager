let brand = $('#brand');
let models = $('#model');
let spareParts = $('#sparePartName');
let container = $('#details');

brand.change(() => {
    models.empty();
    models.append('<option value="" selected>Select model</option>');
    fetch('http://localhost:8080/spare-parts/models?brandName=' + brand[0].value)
        .then((response) => response.json())
        .then((json) => json.forEach((ml) => {
            let option = `<option value="${ml}">${ml}</option>`
            models.append(option);
        }))
    $('#model-group')[0].style.display = "block";
    $('#sparePart-group')[0].style.display = "none";
    container[0].style.display = "none";
})

models.change(() => {
    spareParts.empty();
    spareParts.append('<option value="" selected>Select spare part</option>');
    fetch('http://localhost:8080/spare-parts/spare-parts?brandName=' + brand[0].value + '&modelName=' + models[0].value)
        .then((response) => response.json())
        .then((json) => json.forEach((sp) => {
            let option = `<option value="${sp.id}">${sp.sparePartName}</option>"`
            spareParts.append(option);
        }))
    $('#sparePart-group')[0].style.display = "block";
    $('#details')[0].style.display = "none";
})

spareParts.change(() => {
    fetch('http://localhost:8080/spare-parts/spare-part-by-id?id=' + spareParts[0].value)
        .then((response) => response.json())
        .then((sp) => {
            $('#pieces')[0].value = sp.pieces;
            $('#price')[0].value = sp.price;
        })
    $('#details')[0].style.display = "block";
})