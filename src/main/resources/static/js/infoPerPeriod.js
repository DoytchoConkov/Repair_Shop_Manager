let startDate = $('#startDate');
let endDate = $('#endDate');
let technician = $('#technician');
let tableBody = document.getElementById("tableBody")
let table = $('#tableInfo');
let error = $('#error');
let tableDetails = $('#tableDetails');

function getDetails(sDate, enDate, techic) {
    if (startDate[0].value <= endDate[0].value) {
        fetch('http://localhost:8080/orders/income?starDate=' + sDate + '&endDate=' + enDate + '&technician=' + techic)
            .then((response) => response.json())
            .then((or) => {
                    tableBody.innerHTML = "";
                    let totalSparePartsPrice = 0;
                    let totalPrice = 0;
                    error[0].style.display = "none";
                    table[0].style.display = "block";
                    let r=1;
                    or.forEach((o) => {
                        totalSparePartsPrice += o.totalSparePartsPrice;
                        totalPrice += o.totalRepairPrice;
                        let row = document.createElement("tr");
                        let d1 = document.createElement("td")
                        d1.innerText = o.leaveDateString;
                        let d2 = document.createElement("td")
                        d2.innerText = o.totalSparePartsPrice.toFixed(2);
                        let d3 = document.createElement("td")
                        d3.innerText = o.totalRepairPrice;
                        let d4 = document.createElement("td")
                        d4.innerText = (o.totalRepairPrice - o.totalSparePartsPrice).toFixed(2);
                        let d5 = document.createElement("td");
                        let btn = document.createElement("button");
                        btn.value = o.leaveDateString;
                        btn.innerText = "Details";
                        btn.addEventListener("click", () => {
                            $('#info')[0].style.display = "none";
                            $('#details')[0].style.display = "block";
                            $('#headTitle')[0].innerText = "Details for " + btn.value;
                            tableDetails.empty();
                            fetch('http://localhost:8080/orders/orderByDate?date=' + btn.value)
                                .then((response) => response.json())
                                .then((json) => json.forEach((o) => {
                                    let row = `<tr><td>${o.brandName}</td>
                                     <td>${o.model}</td>
                                     <td>${o.serialNumber}</td>
                                     <td>${o.damage}</td>
                                     <td>${o.totalRepairPrice}</td>
                                     <td>${o.clientName}</td>
                                   </tr>`;
                                    tableDetails.append(row);
                                }))
                        });
                        d5.appendChild(btn);
                        row.appendChild(d1);
                        row.appendChild(d2);
                        row.appendChild(d3);
                        row.appendChild(d4);
                        row.appendChild(d5);
                        tableBody.appendChild(row);
                    })
                    $('#totalSparePartPerPeriod')[0].innerText = "Total Spare Parts Price: " + totalSparePartsPrice.toFixed(2);
                    $('#totalPerPeriod')[0].innerText = "Total Price Per Period: " + totalPrice.toFixed(2);
                    $('#incomePerPeriod')[0].innerText = "Income Per Period: " + (totalPrice - totalSparePartsPrice).toFixed(2);
                }
            )
    } else {
        error[0].style.display = "block";
        table[0].style.display = "none";
    }
}

startDate.change(() => {
    getDetails(startDate[0].value, endDate[0].value, technician[0].value);
})

endDate.change(() => {
    getDetails(startDate[0].value, endDate[0].value, technician[0].value);
})

technician.change(() => {
    getDetails(startDate[0].value, endDate[0].value, technician[0].value);
})

$('#details').click(() => {
    $('#info')[0].style.display = "block";
    $('#details')[0].style.display = "none";
})

$(document).ready(function () {
    getDetails(startDate[0].value, endDate[0].value,"");
});

