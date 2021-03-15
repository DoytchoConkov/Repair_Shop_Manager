let brand = $('#brandName');
let model = $('#modelName');
let sparePartsList = $('#spareParts');
let addBtn = $('#addSpareParts');
let addBrandBtn = $('#addAllSparePartsForBrand');

addBrandBtn.click(() => {
    let select = `<select name="sparePartName" id="sparePartName" class="custom-select">\n<option value="" selected>Select spare part</option>\n`;
    fetch('http://localhost:8080/spare-parts/spare-parts-for-brand?brandName=' + brand[0].innerText)
        .then((response) => response.json())
        .then((json) => json.forEach((sp) => {
            select += `<option value="${sp.id}">${sp.sparePartName}</option>\n`;
        })).then(() => {
        select += "</select>";
        console.log(select)
        sparePartsList.append(select);
    })
})

addBtn.click(() => {
    let select = `<select name="sparePartName" id="sparePartName" class="custom-select">\n<option value="" selected>Select spare part</option>\n`;
    fetch('http://localhost:8080/spare-parts/spare-parts?brandName=' + brand[0].innerText + '&modelName=' + model[0].innerText)
        .then((response) => response.json())
        .then((json) => json.forEach((sp) => {
            select += `<option value="${sp.id}">${sp.sparePartName}</option>\n`;
        })).then(() => {
        select += "</select>";
        console.log(select)
        sparePartsList.append(select);
    })
})
