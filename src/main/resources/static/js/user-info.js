document.addEventListener('DOMContentLoaded', async () => {
    try {
        const res = await fetch('/uzytkownik/dane', { credentials: 'include' });
        if (res.status === 401) {
            window.location.href = '/login.html';
            return;
        }
        if (!res.ok) throw new Error('Błąd pobierania danych użytkownika');

        const u = await res.json();

        const setText = (id, val) => {
            const el = document.getElementById(id);
            if (el) el.textContent = val ?? '';
        };
        const setVal = (id, val) => {
            const el = document.getElementById(id);
            if (el && val != null) el.value = val;
        };
        const toIsoDate = (date) => {
            if (!date) return '';
            if (typeof date === 'string') return date.substring(0, 10);
            if (Array.isArray(date) && date.length >= 3) {
                const [y, m, d] = date;
                return `${y}-${String(m).padStart(2, '0')}-${String(d).padStart(2, '0')}`;
            }
            if (date.year && date.month && date.day) {
                return `${date.year}-${String(date.month).padStart(2, '0')}-${String(date.day).padStart(2, '0')}`;
            }
            return '';
        };

        // Panel boczny
        setText('info-imie', u.imie);
        setText('info-nazwisko', u.nazwisko);
        setText('info-email', u.email);
        setText('info-telefon', u.telefon ?? u.nrTel ?? '');
        setText('info-adres', u.adres);
        setText('info-miejscowosc', u.miejscowosc);
        setText('info-data-ur', toIsoDate(u.dataUr ?? u.dataUrodzenia));
        setText('info-dowod', u.nrDowodu);

        // Nagłówek "witaj"
        const witaj = document.getElementById('witaj-tekst');
        if (witaj) {
            const full = [u.imie, u.nazwisko].filter(Boolean).join(' ');
            witaj.textContent = full ? `Witaj, ${full}` : 'witaj';
        }

        // Prefill formularza edycji
        setVal('imie', u.imie);
        setVal('nazwisko', u.nazwisko);
        setVal('nrTel', u.telefon ?? u.nrTel ?? '');
        setVal('adres', u.adres);
        setVal('miejscowosc', u.miejscowosc);
        setVal('email', u.email);
        setVal('data_ur', toIsoDate(u.dataUr ?? u.dataUrodzenia));
        setVal('nrDowodu', u.nrDowodu);

        // Przełącz przyciski logowania
        const btnZaloguj = document.getElementById('btn-zaloguj');
        const btnWyloguj = document.getElementById('btn-wyloguj');
        const btnPanel = document.getElementById('btn-panel');
        btnZaloguj?.classList.add('hidden');
        btnWyloguj?.classList.remove('hidden');
        btnPanel?.classList.remove('hidden');
    } catch (err) {
        console.error(err);
    }
});
