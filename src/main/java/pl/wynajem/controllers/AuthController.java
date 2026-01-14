package pl.wynajem.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.wynajem.models.Uzytkownik;
import pl.wynajem.services.AuthService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String loginForm(HttpSession session) {
        // Czyszczenie starych komunikatow
        session.removeAttribute("error");
        session.removeAttribute("success");
        return "redirect:/login.html";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String login, @RequestParam("password") String haslo, HttpSession session) {
        try {
            Uzytkownik user = authService.login(login, haslo);
            session.setAttribute("user", user);
            session.removeAttribute("error");
            return "redirect:/glowna.html"; // po zalogowaniu na strone glowna od razu
        } catch (RuntimeException e) {
            session.setAttribute("error", e.getMessage());
            return "redirect:/login.html"; // wracamy do formularza z komunikatem
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login.html";
    }

    @PostMapping("/register") //obsluz POST request register
    public String registerSubmit(@RequestParam String login, @RequestParam String email, @RequestParam String password, @RequestParam String confirmPassword,
                                 @RequestParam String imie, @RequestParam String nazwisko, @RequestParam(required = false) String adres, @RequestParam(required = false) String miejscowosc,
                                 @RequestParam int nrTel, @RequestParam(required = false) String nrDowodu, @RequestParam LocalDate dataUr, HttpSession session){
        // czy hasla sie zgadzaja
        if (!password.equals(confirmPassword)) {
            session.setAttribute("error", "Hasła nie są takie same");
            return "redirect:/login.html"; // powrot
        }

        try {
            // tworzenie uzytkownika i login w vazie
            authService.register(login, email, password, imie, nazwisko, nrTel, adres, miejscowosc, nrDowodu, dataUr, "N");

            session.setAttribute("success", "Rejestracja zakończona. Możesz się zalogować.");
            return "redirect:/login.html";
        } catch (RuntimeException e) {
            session.setAttribute("error", e.getMessage());
            return "redirect:/login.html";
        }
    }

    // JavaScript wywoluje ten endpoint zeby sprawdzic czy user jest zalogowany
    @GetMapping("/api/session/check")
    @ResponseBody  // zwraca json a nie html
    public Map<String, Object> checkSession(HttpSession session) {
        // mapa zostanie zamieniona na jsona
        Map<String, Object> response = new HashMap<>();

        // czy user zapisany w sesji -> zapisuje go tam w login setAttribute("user")
        Uzytkownik user = (Uzytkownik) session.getAttribute("user");

        if (user != null) { // jest zalogowany -> zwracam jego dane
            response.put("zalogowany", true);
            response.put("imie", user.getImie());
            response.put("id", user.getId());
        } else {
            response.put("zalogowany", false);
        }

        // spring automatycznie zamieni Map na JSON:
        // { "zalogowany": true, "imie": "Jan", "id": 1 }
        return response;
    }
}
