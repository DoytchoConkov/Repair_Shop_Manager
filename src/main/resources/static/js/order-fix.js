let brand = $('#brandName');
let model = $('#modelName');
let addBtn = $('#addSpareParts');
let addBrandBtn = $('#addAllSparePartsForBrand');
let sparePartsList = document.getElementById('spareParts');

addBrandBtn.click(() => {
    let select = document.createElement("select")
    select.addEventListener("select", calculateSparePartTotalPrice);
    sparePartsList.appendChild(select);
    select.setAttribute("name", "sparePartName");
    select.setAttribute("id", "sparePartName");
    select.classList.add("custom-select");
    let option1 = document.createElement("option");
    option1.setAttribute("value", "");
    option1.text = "Select spare part";
    select.appendChild(option1);
       fetch('http://localhost:8080/spare-parts/spare-parts-for-brand?brandName=' + brand[0].innerText)
           .then((response) => response.json())
           .then((json) => json.forEach((sp) => {
               let option = document.createElement("option");
               option.setAttribute("value", sp.id);
               option.text = sp.sparePartName;
               select.appendChild(option);
           })).then(() => {

       })
})


addBtn.click(() => {
    let select = document.createElement("select")
    select.addEventListener("select", calculateSparePartTotalPrice);
    sparePartsList.appendChild(select);
    select.setAttribute("name", "sparePartName");
    select.setAttribute("id", "sparePartName");
    select.classList.add("custom-select");
    let option1 = document.createElement("option");
    option1.setAttribute("value", "");
    option1.text = "Select spare part";
    select.appendChild(option1);
    fetch('http://localhost:8080/spare-parts/spare-parts?brandName=' + brand[0].innerText + '&modelName=' + model[0].innerText)
        .then((response) => response.json())
        .then((json) => json.forEach((sp) => {
            let option = document.createElement("option");
            option.setAttribute("value", sp.id);
            option.text = sp.sparePartName;
            select.appendChild(option);
        })).then(() => {

    })
})


function calculateSparePartTotalPrice() {
    console.log("Hello")
}