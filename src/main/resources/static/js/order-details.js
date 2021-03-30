let image = $('#image');
let clicked = false;
image.click(() => {
    if (clicked) {
        image[0].style.height = "300px";
        clicked=false;
    } else {
        image[0].style.height = "600px";
        image[0].style.marginLeft="auto";
        image[0].style.marginRight="auto";
        clicked=true;
    }
})