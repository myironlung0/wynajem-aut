function zmienHaslo() {
    // 1. Pobierz wartości z pól
    const currentPassword = document.getElementById('currentPassword').value;
    const newPassword = document.getElementById('newPassword').value;
    const confirmNewPassword = document.getElementById('confirmNewPassword').value;

    // 2. Pobierz miejsce na komunikat
    const messageDiv = document.getElementById('haslo-message');

    // 3. Walidacja podstawowa
    if (newPassword !== confirmNewPassword) {
        showMessage('Nowe hasła nie są identyczne', 'error');
        return;
    }

    // 4. Przygotuj dane do wysłania
    const formData = new URLSearchParams();
    formData.append('currentPassword', currentPassword);
    formData.append('newPassword', newPassword);
    formData.append('confirmNewPassword', confirmNewPassword);

    // 5. Wyślij do backendu
    fetch('/uzytkownik/zmiana-hasla', {
        method: 'POST',
        headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: formData
})
    .then(response => response.text())
    .then(text => {
    // 6. Sprawdź czy to JSON (błąd) czy string (sukces)
    try {
    // Jeśli to JSON (błąd) - parsuj
    const error = JSON.parse(text);
    showMessage('Błąd: ' + error, 'error');
} catch {
    // Jeśli to string (sukces)
    showMessage('Sukces: ' + text, 'success');

    // Wyczyść formularz
    document.getElementById('currentPassword').value = '';
    document.getElementById('newPassword').value = '';
    document.getElementById('confirmNewPassword').value = '';
}
})
    .catch(error => {
    showMessage('Błąd połączenia: ' + error.message, 'error');
});
}

    function showMessage(text, type) {
    const messageDiv = document.getElementById('haslo-message');
    messageDiv.textContent = text;
    messageDiv.style.display = 'block';

    if (type === 'success') {
    messageDiv.style.background = '#d4edda';
    messageDiv.style.color = '#155724';
    messageDiv.style.border = '1px solid #c3e6cb';
} else {
    messageDiv.style.background = '#f8d7da';
    messageDiv.style.color = '#721c24';
    messageDiv.style.border = '1px solid #f5c6cb';
}
}
