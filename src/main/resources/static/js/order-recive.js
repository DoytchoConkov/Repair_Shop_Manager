let brand = $('#brand');
let models = $('#modelsList');

brand.change(()=>{
    models.empty();
    models.append('<option value="">Select model</option>');
    fetch('http://localhost:8080/order/models?brandName=' + brand[0].value)
        .then((response) => response.json())
        .then((json) => json.forEach((ml) => {
            let option = `<option value="${ml}">${ml}</option>`;
            models.append(option);
        }))
})