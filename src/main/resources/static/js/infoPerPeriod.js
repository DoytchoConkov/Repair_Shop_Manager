let startDate = $('#startDate');
let endDate = $('#endDate');
let tableBody = $('#tableBody');
let table = $('#table');
let error = $('#error');

function getDetails(sDate, enDate) {
    if (startDate[0].value < endDate[0].value) {
        fetch('http://localhost:8080/orders/income?starDate=' + sDate + '&endDate=' + enDate)
            .then((response) => response.json())
            .then((or) => {
                    tableBody.empty();
                    error[0].style.display = "none";
                    table[0].style.display = "block";
                    or.forEach((o) => {
                        let row = `<tr>
                                     <td>${o.leaveDate}</td>
                                     <td>${o.sparePartsTotalPrice}</td>
                                     <td>${o.totalPrice}</td>
                                   </tr>`;
                        tableBody.append(row);
                    })
                }
            )
    } else {
        error[0].style.display = "block";
        table[0].style.display = "none";
    }
}

startDate.change(() => {
    getDetails(startDate[0].value, endDate[0].value)
})
endDate.change(() => {
    getDetails(startDate[0].value, endDate[0].value);
})