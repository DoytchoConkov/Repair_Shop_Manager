let input = $('#clientName');

input.keyup(() => {
    if (input[0].value.length >= 3) {
        console.log(input[0].value);
    }

})