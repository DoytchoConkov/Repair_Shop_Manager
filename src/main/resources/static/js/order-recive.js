let brand = $('#brand');
let models = $('#modelsList');

brand.change(()=>{
    models.empty();
    models.append('<option value="">Select model</option>');
    fetch('http://localhost:8080/orders/models?brandName=' + brand[0].value)
        .then((response) => response.json())
        .then((json) => json.forEach((ml) => {
            let option = `<option value="${ml}">${ml}</option>`;
            models.append(option);
        }))
})

autocomplete(document.getElementById("clientPhoneNumber"))

function autocomplete(inp) {
    let currentFocus;
    fetch('http://localhost:8080/client/allPhones')
        .then((response) => response.json())
        .then((arr) => {
            inp.addEventListener("input", function (e) {
                let a, b, i, val = this.value;
                closeAllLists();
                if (!val) {
                    return false;
                }
                currentFocus = -1;
                a = document.createElement("DIV");
                a.setAttribute("id", this.id + "autocomplete-list");
                a.setAttribute("class", "autocomplete-items");
                let cnt = 0;
                for (i = 0; i < arr.length; i++) {
                    if (arr[i].clientName.toUpperCase().includes(val.toUpperCase()) || arr[i].clientPhoneNumber.includes(val)) {
                        cnt++;
                        b = document.createElement("DIV");
                        b.innerHTML = "<p>" + arr[i].clientPhoneNumber + "</p>";
                        b.innerHTML += "<input type='hidden' value='" + arr[i].id + "'>";
                        b.addEventListener("click", function (e) {
                            inp.value = this.getElementsByTagName("p")[0].innerText;
                            fetch('http://localhost:8080/client/find-clientById?clientId=' + this.getElementsByTagName("input")[0].value)
                                .then((response) => response.json())
                                .then((or) => {
                                    $('#clientEmail')[0].value = or.clientEmail;
                                    $('#clientName')[0].value = or.clientName;
                                    $('#clientNote')[0].value = or.clientNote;
                                })
                            closeAllLists();
                        });
                        if(cnt>0&&cnt<=5){
                            a.appendChild(b);
                            document.getElementById("clientPhoneNumber").parentElement.appendChild(a);
                        }
                    }
                }
            });
            inp.addEventListener("keydown", function (e) {
                let x = document.getElementById(this.id + "autocomplete-list");
                if (x) x = x.getElementsByTagName("div");
                if (e.keyCode == 40) {
                    currentFocus++;
                    addActive(x);
                } else if (e.keyCode == 38) {
                    currentFocus--;
                    addActive(x);
                } else if (e.keyCode == 13) {
                    e.preventDefault();
                    if (currentFocus > -1) {
                        if (x) x[currentFocus].click();
                    }
                }
            });

            function addActive(x) {
                if (!x) return false;
                removeActive(x);
                if (currentFocus >= x.length) currentFocus = 0;
                if (currentFocus < 0) currentFocus = (x.length - 1);
                x[currentFocus].classList.add("autocomplete-active");
            }

            function removeActive(x) {
                for (let i = 0; i < x.length; i++) {
                    x[i].classList.remove("autocomplete-active");
                }
            }

            function closeAllLists(elmnt) {
                let x = document.getElementsByClassName("autocomplete-items");
                for (let i = 0; i < x.length; i++) {
                    if (elmnt != x[i] && elmnt != inp) {
                        x[i].parentNode.removeChild(x[i]);
                    }
                }
            }

            document.addEventListener("click", function (e) {
                closeAllLists(e.target);
            });
        });
}