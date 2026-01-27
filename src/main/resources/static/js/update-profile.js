
// przypisanie do formularza
document.getElementById('form-dane').addEventListener('submit', function(e){
    e.preventDefault(); // blokuje domyslne przeladowanie
    aktualizujProfil();
});

function aktualizujProfil(){
    // pobieranei wartosci z pol
    const imie = document.getElementById('imie').value.trim();
    const nazwisko = document.getElementById('nazwisko').value.trim();
    const adres = document.getElementById('adres').value.trim();
    const miejscowosc = document.getElementById('miejscowosc').value.trim();
    const telefon = document.getElementById('nrTel').value.trim();
    const email = document.getElementById('email').value.trim();
    const nrDowodu = document.getElementById('nrDowodu').value.trim();
    const dataUr = document.getElementById('data_ur').value; // ISO format: YYYY-MM-DD

    // przygotowuje dane do wyslania w formacie oczekiwanym przy @RequestParam
    const formData = new URLSearchParams();
    formData.append('imie', imie);
    formData.append('nazwisko', nazwisko);
    formData.append('telefon', telefon);
    formData.append('adres', adres);
    formData.append('miejscowosc', miejscowosc);
    formData.append('email', email);
    formData.append('dataUr', dataUr);
    formData.append('nrDowodu', nrDowodu);

    // fetch to funkcja do wysylania zadan HTTP, tutaj POST, body to zawartosc żądania i przekazuje to formData
    fetch('uzytkownik/aktualizuj', {
        method : 'POST',
        body : formData
    })
        .then(res => {
            if (!res.ok) throw new Error('Błąd aktualizacji danych');
            return res.text();
        })
        .then(msg => {
            alert(msg); // albo pokaz komunikat w DOM
            // aktualizacja widocznych danych w panelu bocznym
            document.getElementById('info-imie').textContent = imie;
            document.getElementById('info-nazwisko').textContent = nazwisko;
            document.getElementById('info-telefon').textContent = nrTel;
            document.getElementById('info-adres').textContent = adres;
            document.getElementById('info-miejscowosc').textContent = miejscowosc;
            document.getElementById('info-email').textContent = email;
            document.getElementById('info-data-ur').textContent = data_ur;
            document.getElementById('info-dowod').textContent = nrDowodu;

            document.getElementById('form-dane').reset(); // wyczyszc formualrz
        })
        .catch(err => {
            alert(err.message);
        });

}
