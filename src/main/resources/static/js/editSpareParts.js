let brand = $('#brand');
let model = $('#model');
let modelsList = $('#modelsList');
let sparePart = $('#sparePartName');
let sparePartsList = $('#sparePartNameList')
let container = $('sparePartDetails');

brand.change(() => {
    modelsList.empty();
    fetch('http://localhost:8080/spare-parts/models?brandName=' + brand[0].value)
        .then((response) => response.json())
        .then((json) => json.forEach((ml) => {
            let option = `<div><option value="${ml}" text="${ml}"></option></div>"`
            modelsList.append(option);
        }))
    $('#model-group')[0].style.display = "block";
    $('#sparePart-group')[0].style.display = "none";
    $('#details')[0].style.display = "none";
})

model.change(() => {
    sparePartsList.empty();
    fetch('http://localhost:8080/spare-parts/spare-parts?brandName=' + brand[0].value + '&modelName=' + model[0].value)
        .then((response) => response.json())
        .then((json) => json.forEach((sp) => {
            let option = `<div><option value="${sp}" text="${sp}"></option></div>"`
            sparePartsList.append(option);
        }))
    $('#sparePart-group')[0].style.display = "block";
    $('#details')[0].style.display = "none";
})

sparePart.change(()=>{
    $('#details')[0].style.display = "block";
})