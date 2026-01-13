package pl.wynajem.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.wynajem.models.Uzytkownik;
import pl.wynajem.services.AuthService;

import java.time.LocalDate;

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
        return "redirect:/login";
    }

    @PostMapping("/register") //obsluz POST request register
    public String registerSubmit(@RequestParam String login, @RequestParam String email, @RequestParam String password, @RequestParam String confirmPassword,
                                 @RequestParam String imie, @RequestParam String nazwisko, @RequestParam String adres, @RequestParam String miejscowosc,
                                 @RequestParam int nrTel, @RequestParam String nrDowodu, @RequestParam LocalDate dataUr, HttpSession session){
        // czy hasla sie zgadzaja
        if (!password.equals(confirmPassword)) {
            session.setAttribute("error", "Hasła nie są takie same");
            return "login"; // powrot
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
}
