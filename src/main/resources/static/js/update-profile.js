// javascript
document.getElementById('form-dane').addEventListener('submit', function (e) {
    e.preventDefault();
    aktualizujProfil();
});

function aktualizujProfil() {
    const imie = document.getElementById('imie').value.trim();
    const nazwisko = document.getElementById('nazwisko').value.trim();
    const adres = document.getElementById('adres').value.trim();
    const miejscowosc = document.getElementById('miejscowosc').value.trim();
    const telefon = document.getElementById('nrTel').value.trim();
    const email = document.getElementById('email').value.trim();
    const nrDowodu = document.getElementById('nrDowodu').value.trim();
    const dataUr = document.getElementById('data_ur').value;

    const formData = new URLSearchParams();
    formData.append('imie', imie);
    formData.append('nazwisko', nazwisko);
    formData.append('telefon', telefon);
    formData.append('adres', adres);
    formData.append('miejscowosc', miejscowosc);
    formData.append('email', email);
    formData.append('dataUr', dataUr);
    formData.append('nrDowodu', nrDowodu);

    const csrf = document.querySelector('meta[name="_csrf"]')?.getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.getAttribute('content');

    fetch('/uzytkownik/aktualizuj', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8',
            ...(csrf && csrfHeader ? { [csrfHeader]: csrf } : {})
        },
        body: formData.toString()
    })
        .then(res => {
            if (!res.ok) throw new Error('Błąd aktualizacji danych');
            return res.text();
        })
        .then(msg => {
            alert(msg);
            document.getElementById('info-imie').textContent = imie;
            document.getElementById('info-nazwisko').textContent = nazwisko;
            document.getElementById('info-telefon').textContent = telefon;
            document.getElementById('info-adres').textContent = adres;
            document.getElementById('info-miejscowosc').textContent = miejscowosc;
            document.getElementById('info-email').textContent = email;
            document.getElementById('info-data-ur').textContent = dataUr;
            document.getElementById('info-dowod').textContent = nrDowodu;
            document.getElementById('form-dane').reset();
        })
        .catch(err => {
            alert(err.message);
        });
}
