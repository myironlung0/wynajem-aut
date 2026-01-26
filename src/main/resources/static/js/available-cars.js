function $(sel) {
    return document.querySelector(sel);
}

function fetchCars(query = '') {
    const url = query
        ? `/samochody/search?search=${encodeURIComponent(query)}`
        : `/samochody`;

    return fetch(url)
        .then(res => {
            if (!res.ok) throw new Error('Błąd pobierania danych');
            return res.json();
        });
}

function renderCars(cars) {
    let container = $('.lista-aut');

    if (!container) {
        container = document.createElement('div');
        container.className = 'lista-aut';
        const box = document.querySelector('.wyszukiwarka') || document.body;
        box.insertAdjacentElement('afterend', container);
    }

    if (!Array.isArray(cars) || cars.length === 0) {
        container.innerHTML = '<p>Brak wyników.</p>';
        return;
    }

    container = document.querySelector('.lista-aut');

    const html = cars.map(c => `
        <div class="auto-card">
            <div class="auto-img">
                <img src="/galery/${escapeHtml(c.zdjecie)}" alt="Samochód" width="300" height="200">
            </div>

            <div class="auto-info">
                <h3>${escapeHtml(c.marka)} ${escapeHtml(c.model)}</h3> 
                <p><strong>Przebieg:</strong> ${formatMileage(c.przebieg)}</p>
                <p class="cena"><strong>Cena:</strong> ${formatPrice(c.cena)}</p>
                <button class="rezerwacjaBtn" onclick="window.location.href='/samochody/${encodeURIComponent(c.id)}'">Zobacz szczegóły</button>
                <button class="rezerwacjaBtn" onclick="window.location.href='/rezerwacje/formularz/${encodeURIComponent(c.id)}'">Zarezerwuj</button>

            </div>
        </div>
    `).join('');

    container.innerHTML = html;
}

// ===== Formatowanie ceny =====
function formatPrice(val) {
    const num = Number(val);
    if (Number.isNaN(num)) return '';
    return `${num.toFixed(2)} PLN`;
}

// ===== Formatowanie przebiegu =====
function formatMileage(val) {
    const num = Number(val);
    if (Number.isNaN(num)) return '';
    return `${num.toLocaleString('pl-PL')} km`;
}

function escapeHtml(str) {
    return String(str)
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#39;');
}

function loadAllCars() {
    fetchCars()
        .then(renderCars)
        .catch(err => {
            console.error(err);
            $('.lista-aut').innerHTML = '<p>Błąd ładowania danych</p>';
        });
}

function searchCars() {
    const q = ($('#searchInput')?.value || '').trim();
    fetchCars(q)
        .then(renderCars)
        .catch(err => console.error(err));
}

window.loadAllCars = loadAllCars;
window.searchCars = searchCars;

window.addEventListener('DOMContentLoaded', loadAllCars);
