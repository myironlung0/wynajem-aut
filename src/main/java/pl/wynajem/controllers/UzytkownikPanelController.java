package pl.wynajem.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.wynajem.models.Uzytkownik;
import pl.wynajem.repositories.UzytkownikRepository;
import pl.wynajem.services.AuthService;
import pl.wynajem.services.UzytkownikService;

import java.time.LocalDate;

@Controller
@RequestMapping("/uzytkownik")  // ← wszystkie endpointy pod /uzytkownik
public class UzytkownikPanelController {
    @Autowired
    private UzytkownikService uzytkownikService;
    @Autowired
    private AuthService authService;

    @GetMapping("/panel")
    public String getUzytkownikPanel(Model model, HttpSession session){
        Uzytkownik user = (Uzytkownik) session.getAttribute("user");
        if(user == null){
            return "redirect:/login.html";
        }

        return "redirect:/uzytkownik_panel.html";
    }

    @GetMapping("/dane")
    @ResponseBody
    public ResponseEntity<?> getDaneUzytkownika(HttpSession session) {

        Uzytkownik user = (Uzytkownik) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nie jesteś zalogowany");
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("/aktualizuj")
    @ResponseBody
    public ResponseEntity<?> aktualizujDaneOsobiste(@RequestParam(required = false) String imie,
                                                    @RequestParam(required = false) String nazwisko,
                                                    @RequestParam(required = false) String adres,
                                                    @RequestParam(required = false) String miejscowosc,
                                                    @RequestParam(required = false) String telefon,
                                                    @RequestParam(required = false) String email,
                                                    @RequestParam(required = false) String nrDowodu,
                                                    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataUr,
                                                    HttpSession session) {

        Uzytkownik user = (Uzytkownik) session.getAttribute("user"); // getAttribute zwraca object
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Nie jesteś zalogowany");
        }

        uzytkownikService.aktualizujDaneOsobiste(user.getId(), imie, nazwisko, adres, miejscowosc, telefon, email, nrDowodu, dataUr);
        // aktualizuj obiekt w sesji
        Uzytkownik updatedUser = uzytkownikService.findById(user.getId());
        session.setAttribute("user", updatedUser);

        return ResponseEntity.ok("Zaktualizowano dane");

    }

    @PostMapping("/zmiana-hasla")
    @ResponseBody
    public ResponseEntity<?> zmienHaslo(@RequestParam String currentPassword, @RequestParam String newPassword, @RequestParam String confirmNewPassword, HttpSession session){
        Uzytkownik user = (Uzytkownik) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nie jesteś zalogowany");
        }

        try {
            authService.updateHaslo(user.getId(), currentPassword, newPassword, confirmNewPassword);
            return ResponseEntity.ok("Zmieniono hasło");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}


