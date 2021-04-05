let brand = $('#brand');
let models = $('#modelsList');
let model = $('#model');
let spareParts = $('#sparePartNameList');
let sp = $('#sparePartName');

brand.change(() => {
    models.empty();
    models.append('<option value="">Select model</option>');
    fetch('http://localhost:8080/spare-parts/models?brandName=' + brand[0].value)
        .then((response) => response.json())
        .then((json) => json.forEach((ml) => {
            let option = `<option value="${ml}">${ml}</option>`;
            models.append(option);
        }))
})

model.change(() => {
    spareParts.empty();
    spareParts.append('<option value="">Select Spare Part</option>');
    fetch('http://localhost:8080/spare-parts/spare-parts?brandName=' + brand[0].value + '&modelName=' + model[0].value)
        .then((response) => response.json())
        .then((json) => json.forEach((sp) => {
            let spn = `<option value="${sp.sparePartName}">${sp.sparePartName}</option>`;
            spareParts.append(spn);
        }))
})

sp.change(() => {
    fetch('http://localhost:8080/spare-parts/spare-parts-name?brandName=' + brand[0].value + '&modelName=' + model[0].value + '&spName=' + sp[0].value)
        .then((response) => response.json())
        .then((sp) => {
            if (sp != null) {
                $('#currentPieces').text(sp.pieces);
                $('#currentPrice').text(sp.price);
            }
        })
})

sp.keyup(() => {
    if (sp[0].value.length >= 3) {
        fetch('http://localhost:8080/spare-parts/spare-parts-name?brandName=' + brand[0].value + '&modelName=' + model[0].value + '&spName=' + sp[0].value)
            .then((response) => response.json())
            .then((sp) => {
                if (sp != null) {
                    $('#currentPieces').text(sp.pieces);
                    $('#currentPrice').text(sp.price);
                }
            })
    }
})