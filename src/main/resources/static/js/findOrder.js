let input = $('#serialNumber');

input.keyup(() => {
    if (input[0].value.length >= 3) {
        fetch('http://localhost:8080/orders/find-order?serialNumber=' + input[0].value)
            .then((response) => response.json())
            .then((json) => json.forEach((or) => {
               console.log(or);
            }))
    }
})