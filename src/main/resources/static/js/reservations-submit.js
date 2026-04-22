(function () {
    function parseIntSafe(val) {
        const n = Number.parseInt(String(val), 10);
        return Number.isFinite(n) ? n : NaN;
    }

    function getSamochodIdFromUrl() {
        try {
            const href = window.location.href;
            const mPath = href.match(/\/rezerwacje\/formularz\/(\d+)/i);
            if (mPath && mPath[1]) {
                const n = parseIntSafe(mPath[1]);
                if (Number.isInteger(n)) return n;
            }
            const url = new URL(href);
            for (const key of ["samochodId", "id", "carId"]) {
                const v = url.searchParams.get(key);
                const n = parseIntSafe(v);
                if (Number.isInteger(n)) return n;
            }
        } catch {}
        return NaN;
    }

    function buildLocalDateTime(dateInputId, timeInputId) {
        const d = document.getElementById(dateInputId)?.value;
        const t = document.getElementById(timeInputId)?.value;
        if (!d || !t) return null;
        // RezerwacjaController parsuje LocalDateTime.parse -> 'YYYY-MM-DDTHH:mm'
        return d + "T" + t;
    }

    async function handleSubmit(e) {
        e.preventDefault();

        const samochodId = getSamochodIdFromUrl();
        if (!Number.isInteger(samochodId)) {
            alert("Brak poprawnego identyfikatora samochodu.");
            return;
        }

        const dataOd = buildLocalDateTime("dateOdbior", "timeOdbior");
        const dataDo = buildLocalDateTime("dataZwrot", "timeZwrot");
        if (!dataOd || !dataDo) {
            alert("Uzupełnij daty i godziny odbioru oraz zwrotu.");
            return;
        }

        try {
            const form = new URLSearchParams();
            form.set("samochodId", String(samochodId));
            form.set("dataOd", dataOd);
            form.set("dataDo", dataDo);

            const res = await fetch("/rezerwacje/zapisz", {
                method: "POST",
                credentials: "include",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                    "Accept": "application/json"
                },
                body: form.toString()
            });

            const ct = res.headers.get("content-type") || "";
            const isJson = ct.includes("application/json");
            const payload = isJson ? await res.json() : null;

            if (!res.ok) {
                alert(payload?.message || "Błąd zapisu rezerwacji.");
                return;
            }

            if (payload?.status === "success") {
                alert(payload.message || "Rezerwacja została utworzona.");
                window.location.href = "/rezerwacje/moje";
            } else {
                alert(payload?.message || "Nie udało się utworzyć rezerwacji.");
            }
        } catch (err) {
            console.error(err);
            alert("Wystąpił błąd połączenia.");
        }
    }

    function init() {
        const form = document.getElementById("reservationForm");
        if (form) {
            form.addEventListener("submit", handleSubmit);
        }
        // Fallback, jeśli brak formularza lub przycisk poza form
        const btn = document.getElementById("reserveSubmit");
        if (btn && !form) {
            btn.addEventListener("click", handleSubmit);
        }
    }

    document.addEventListener("DOMContentLoaded", init);
})();
