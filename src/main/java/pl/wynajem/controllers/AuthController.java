package pl.wynajem.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
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
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String login, @RequestParam("password") String haslo, HttpSession session, Model model) {
        try {
            Uzytkownik user = authService.login(login, haslo);
            session.setAttribute("user", user);
            return "redirect:/"; // po zalogowaniu na strone glowna od razu
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "login"; // wracamy do formularza z komunikatem
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping("/register")
    public String registerSubmit(@RequestParam String login, @RequestParam String email, @RequestParam String password, @RequestParam String confirmPassword,
                                 @RequestParam String imie, @RequestParam String nazwisko, @RequestParam String adres, @RequestParam String miejscowosc,
                                 @RequestParam int nrTel, @RequestParam String nrDowodu, @RequestParam LocalDate dataUr, Model model){
        // czy hasla sie zgadzaja
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Hasła nie są takie same");
            return "login"; // powrot
        }

        try {
            // tworzenie uzytkownika i login w vazie
            authService.register(login, email, password, imie, nazwisko, nrTel, adres, miejscowosc, nrDowodu, dataUr, "N");

            model.addAttribute("success", "Rejestracja zakończona. Możesz się zalogować.");
            return "login";

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }
}
