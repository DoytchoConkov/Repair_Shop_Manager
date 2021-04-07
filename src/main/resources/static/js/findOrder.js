let input = $('#serialNumber');
let errorNotFound = $('#errorNotFound');
let errorTooMany = $('#errorTooMany');
let table = $('#table');
let tableBody = $('#tableBody');

input.keyup(() => {
    if (input[0].value.length >= 3) {
        fetch('http://localhost:8080/orders/find-order?serialNumber=' + input[0].value)
            .then((response) => response.json())
            .then((or) => {
                if (or.length == 0) {
                    errorNotFound[0].style.display = "block";
                    errorTooMany[0].style.display = "none";
                    table[0].style.display = "none";

                } else if (or.length > 50) {
                    errorTooMany[0].style.display = "none";
                    errorNotFound[0].style.display = "block";
                    table[0].style.display = "none";

                } else {
                    tableBody.empty();
                    errorTooMany[0].style.display = "none";
                    errorNotFound[0].style.display = "none";
                    table[0].style.display = "block";
                    let i = 1;
                    or.forEach((o) => {
                        let row = `<tr>
                                     <td>${o.brandName}</td>
                                     <td>${o.model}</td>
                                     <td>${o.serialNumber}</td>
                                     <td>${o.damage}</td>
                                     <td>${o.receiveDate}</td>
                                     <td>${o.leaveDate}</td>
                                     <td>${o.totalRepairPrice}</td>
                                     <td>${o.clientName}</td>
                                   </tr>`;
                        tableBody.append(row);
                    })
                }
            })
    }
})