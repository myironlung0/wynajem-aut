// ====================================================================
// MECHANIZM DYNAMICZNEGO POKAZYWANIA/UKRYWANIA PRZYCISKÓW
// ====================================================================

// DOMContentLoaded to event, ktory wywoluje sie, gdy caly html sie sparsowal
document.addEventListener('DOMContentLoaded', function() {

    // --------------------------------------------------------------------
    // KROK 1: Sprawdzamy czy użytkownik jest zalogowany
    // --------------------------------------------------------------------
    // Wywołujemy endpoint na serwerze, który zwraca informację o sesji
    // fetch() to funkcja do wykonywania żądań HTTP (jak AJAX)

    fetch('/api/session/check')
        .then(function(response) {
            // .then() wykonuje się gdy serwer odpowie
            // response.json() zamienia odpowiedź na obiekt JavaScript
            return response.json();
        })
        .then(function(data) {
            // 'data' to obiekt z serwera, np. { zalogowany: true, imie: "Jan" }

            // --------------------------------------------------------------------
            // KROK 2: Pobieramy elementy HTML które chcemy pokazać/ukryć
            // --------------------------------------------------------------------
            // document.getElementById() szuka elementu po jego atrybucie id=""
            var przyciskZaloguj = document.getElementById('btn-zaloguj');
            var przyciskWyloguj = document.getElementById('btn-wyloguj');
            var przyciskPanel = document.getElementById('btn-panel');
            var witajText = document.getElementById('witaj-tekst');

            // --------------------------------------------------------------------
            // KROK 3: Pokazujemy/ukrywamy elementy w zależności od stanu sesji
            // --------------------------------------------------------------------
            if (data.zalogowany) {
                // display: none = element niewidoczny
                if (przyciskZaloguj) przyciskZaloguj.style.display = 'none';
                if (przyciskWyloguj) przyciskWyloguj.style.display = 'inline-block';
                if (przyciskPanel) przyciskPanel.style.display = 'inline-block';    // przycisk moje konto

                // wysiwetl imie uzytkownika
                if (witajText && data.imie) {
                    witajText.textContent = 'Witaj, ' + data.imie + '!';
                    witajText.style.display = 'inline';
                }

            } else {
                if (przyciskZaloguj) przyciskZaloguj.style.display = 'inline-block';
                if (przyciskWyloguj) przyciskWyloguj.style.display = 'none';
                if (przyciskPanel) przyciskPanel.style.display = 'none';

                if (witajText) witajText.style.display = 'none';
            }
        })
        .catch(function(error) {    // jak wystapi blad
            console.error('Błąd sprawdzania sesji:', error);

            // W razie błędu - domyślnie pokazujemy przycisk logowania
            var przyciskZaloguj = document.getElementById('btn-zaloguj');
            if (przyciskZaloguj) przyciskZaloguj.style.display = 'inline-block';
        });
});
