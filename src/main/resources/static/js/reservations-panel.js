function loadRezerwacje() {
    fetch('/rezerwacje/moje')
        .then(response => {
            if (!response.ok) {
                if (response.status === 401) {
                    // niezalogowany
                    window.location.href = '/login';
                    return;
                }
                throw new Error('Błąd pobierania rezerwacji');
            }
            return response.json();
        })
        .then(rezerwacje => {
            displayRezerwacje(rezerwacje);
        })
        .catch(error => {
            console.error('Błąd:', error);
            document.getElementById('rezerwacje-body').innerHTML =
                `<tr><td colspan="6" style="text-align: center; color: red;">Błąd ładowania danych</td></tr>`;
        });
}

// func do wyswietlania rezerwacji
function displayRezerwacje(rezerwacje) {
    const tbody = document.getElementById('rezerwacje-body');
    const brakDiv = document.getElementById('brak-rezerwacji');

    if (!rezerwacje || rezerwacje.length === 0) {
        tbody.innerHTML = '';
        brakDiv.style.display = 'block';
        return;
    }

    brakDiv.style.display = 'none';

    // tworzenie wierszy tabeli
    let html = '';
    rezerwacje.forEach(rezerwacja => {
        html += `
            <tr>
                <td>${formatDate(rezerwacja.dataOd)}</td>
                <td>${formatDate(rezerwacja.dataDo)}</td>
                <td>${rezerwacja.idSamochodu || 'ID: ' + rezerwacja.idSamochodu}</td>
                <td>${rezerwacja.cenaKoncowa || 0} PLN</td>
                <td>${rezerwacja.status || 'potwierdzona'}</td>
                <td>${rezerwacja.nrRezerwacji || 'BRAK'}</td>
            </tr>
        `;
    });

    tbody.innerHTML = html;
}

// formatownaie daty
function formatDate(dateString) {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toLocaleDateString('pl-PL') + ' ' + date.toLocaleTimeString('pl-PL', {
        hour: '2-digit',
        minute: '2-digit'
    });
}

function pokazPanel(panelId) {
    // ukryj wszystkie panele
    document.querySelectorAll('.uzytkownik_panel').forEach(panel => {
        panel.classList.add('hidden');
    });

    // pokaz wybrany panek
    const panel = document.getElementById(panelId);
    if (panel) {
        panel.classList.remove('hidden');

        // jesli to panel z histoira, zaladuj
        if (panelId === 'panel-historia') {
            loadRezerwacje();
        }
    }
}