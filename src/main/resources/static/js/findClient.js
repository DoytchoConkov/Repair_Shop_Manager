let input = $('#clientName');
let errorNotFound = $('#errorNotFound');
let errorTooMany = $('#errorTooMany');
let selectGroup = $('#select-group');
let select = $('#clientNameSelect');
let table = $('#table');
let tableBody = $('#tableBody');

input.keyup(() => {
    table[0].style.display = "none";
    if (input[0].value.length >= 3) {
        fetch('http://localhost:8080/client/find-client?clientName=' + input[0].value)
            .then((response) => response.json())
            .then((cl) => {
                if (cl.length == 0) {
                    errorNotFound[0].style.display = "block";
                    errorTooMany[0].style.display = "none";
                    selectGroup[0].style.display = "none";
                } else if (cl.length > 50) {
                    errorTooMany[0].style.display = "none";
                    errorNotFound[0].style.display = "block";
                    selectGroup[0].style.display = "none";
                } else {
                    errorTooMany[0].style.display = "none";
                    errorNotFound[0].style.display = "none";
                    selectGroup[0].style.display = "block";
                    select.empty();
                    select.append(`<option value="">Select client</option>`)
                    cl.forEach((c) => {
                        select.append(`<option value="${c.id}">${c.clientName} ${c.clientPhoneNumber}</option>`)
                    })
                }
            })
    }
})

select.change(() => {
    fetch('http://localhost:8080/orders/find-clientById?clientId=' + select[0].value)
        .then((response) => response.json())
        .then((or) => {
                tableBody.empty();
                table[0].style.display = "block";
                or.forEach((o) => {
                    let row = `<tr><td>${o.brandName}</td>
                                     <td>${o.model}</td>
                                     <td>${o.serialNumber}</td>
                                     <td>${o.damage}</td>
                                     <td>${o.receiveDate}</td>
                                     <td>${o.leaveDate}</td>
                                     <td>${o.totalRepairPrice}</td>
                                   </tr>`;
                    tableBody.append(row);
                })
        })
})