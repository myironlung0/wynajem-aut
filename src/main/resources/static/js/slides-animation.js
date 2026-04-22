let slideIndex = 0;
showSlides();

function showSlides() {
    let i;
    let slajdy = document.getElementsByClassName("animacja");
    let kropki = document.getElementsByClassName("kropka");
    for (i = 0; i < slajdy.length; i++) {
        slajdy[i].style.display = "none";
    }
    slideIndex++;
    if (slideIndex > slajdy.length) {slideIndex = 1}
    for (i = 0; i < kropki.length; i++) {
        kropki[i].className = kropki[i].className.replace(" active", "");
    }
    slajdy[slideIndex-1].style.display = "block";
    kropki[slideIndex-1].className += " active";
    setTimeout(showSlides, 5000); //zmienia obraz co 5 sekund
}