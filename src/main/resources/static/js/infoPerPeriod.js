let startDate = $('#startDate');
let endDate = $('#endDate');
let tableBody = $('#tableBody');
let table = $('#table');
let error = $('#error');

function getDetails(sDate, enDate) {
    if (startDate[0].value <= endDate[0].value) {
        fetch('http://localhost:8080/orders/income?starDate=' + sDate + '&endDate=' + enDate)
            .then((response) => response.json())
            .then((or) => {
                    tableBody.empty();
                    let totalSparePartsPrice = 0;
                    let totalPrice = 0;
                    error[0].style.display = "none";
                    table[0].style.display = "block";
                    or.forEach((o) => {
                        totalSparePartsPrice += o.totalSparePartsPrice;
                        totalPrice += o.totalRepairPrice;
                        let row = `<tr>
                                     <td>${o.leaveDateString}</td>
                                     <td>${o.totalSparePartsPrice.toFixed(2)}</td>
                                     <td>${o.totalRepairPrice.toFixed(2)}</td>
                                     <td>${(o.totalRepairPrice-o.totalSparePartsPrice).toFixed(2)}</td>
                                     <td><a class="nav-link" href="/back-office/order/{leaveDate}(leaveDate=${o.leaveDate})">Details</a></td>
                                   </tr>`;
                        tableBody.append(row);
                    })
                    $('#totalSparePartPerPeriod')[0].innerText = "Total Spare Parts Price: "+totalSparePartsPrice.toFixed(2);
                    $('#totalPerPeriod')[0].innerText ="Total Price Per Period: "+ totalPrice.toFixed(2);
                    $('#incomePerPeriod')[0].innerText ="Income Per Period: "+ (totalPrice-totalSparePartsPrice).toFixed(2);
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