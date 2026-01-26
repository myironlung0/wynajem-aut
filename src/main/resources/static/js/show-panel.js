// Funkcja do pokazywania odpowiednich paneli
function pokazPanel(id) {
    document.querySelectorAll('.uzytkownik_panel')
        .forEach(panel => panel.classList.add('hidden'));

    document.getElementById(id)
        .classList.remove('hidden');
}