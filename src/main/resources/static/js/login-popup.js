(function () {
    const DEFAULT_RESERVATION_PAGE = "/formularz.html";
    const LOGIN_URL = window.LOGIN_URL || "/login";
    const STATUS_URL = window.STATUS_URL || "/api/session/check";

    const RESERVE_SELECTORS = "[data-action='reserve'], #reserveBtn, .reserveButton, .btn-zarezerwuj";

    async function fetchLoginStatus() {
        try {
            const res = await fetch(STATUS_URL, {
                method: "GET",
                credentials: "include",
                cache: "no-store",
                redirect: "follow",
                headers: {
                    "Accept": "application/json",
                    "Cache-Control": "no-store"
                }
            });

            if (!res.ok) return false;

            const ct = res.headers.get("content-type") || "";
            if (!ct.includes("application/json")) return false;

            const data = await res.json();
            return data && data.zalogowany === true;
        } catch {
            return false;
        }
    }

    function buildLoginUrlWithRedirect(redirectTo) {
        try {
            const url = new URL(LOGIN_URL, window.location.origin);
            url.searchParams.set("redirect", redirectTo);
            return url.toString();
        } catch {
            const sep = LOGIN_URL.includes("?") ? "&" : "?";
            return LOGIN_URL + sep + "redirect=" + encodeURIComponent(redirectTo);
        }
    }

    function isReserveButton(el) {
        return !!el.closest(RESERVE_SELECTORS);
    }

    function resolveReservationTarget(el) {
        const btn = el.closest(RESERVE_SELECTORS);
        return (btn && btn.getAttribute("data-reserve-url")) || DEFAULT_RESERVATION_PAGE;
    }

    async function handleClick(e) {
        if (!isReserveButton(e.target)) return;
        e.preventDefault();

        const targetPage = resolveReservationTarget(e.target);
        const loggedIn = await fetchLoginStatus();

        if (loggedIn === true) {
            window.location.href = targetPage;
            return;
        }

        alert("Musisz być zalogowany, aby przejść do formularza rezerwacji.");
        window.location.href = buildLoginUrlWithRedirect(targetPage);
    }

    document.addEventListener("click", handleClick);
})();
