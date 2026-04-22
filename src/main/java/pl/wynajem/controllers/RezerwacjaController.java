package pl.wynajem.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.wynajem.models.Rezerwacja;
import pl.wynajem.models.Samochod;
import pl.wynajem.models.Uzytkownik;
import pl.wynajem.services.RezerwacjaService;
import pl.wynajem.services.SamochodService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/rezerwacje")
public class RezerwacjaController {
    @Autowired
    private RezerwacjaService rezerwacjaService;

    @Autowired
    private SamochodService samochodService;

    // api do pobieranai danych samochodu
    @GetMapping("/nowa/{samochodId}")
    @ResponseBody
    public Map<String, Object> getDaneRezerwacji(@PathVariable int samochodId, HttpSession session) {

        Map<String, Object> response = new HashMap<>();

        Uzytkownik user = (Uzytkownik) session.getAttribute("user");
        if (user == null) {
            response.put("status", "error");
            response.put("message", "Nie jesteś zalogowany");
            return response;
        }

        // pobranie samochodu
        Samochod samochod = samochodService.findById(samochodId);
        if (samochod == null) {
            response.put("status", "error");
            response.put("message", "Nie znaleziono samochodu");
            return response;
        }

        // zwracam dane w JSON
        response.put("status", "success");
        response.put("samochod", samochod);
        response.put("user", user);

        // TODO: obsluzyc statusy
        //  w formularzu pola input na daty

        return response;
    }

    // formularz
    @GetMapping("/formularz/{samochodId}")
    public String formularzStrona(@PathVariable int samochodId, HttpSession session, RedirectAttributes redirectAttributes) {
        Uzytkownik user = (Uzytkownik) session.getAttribute("user");
        if (user == null) {

            redirectAttributes.addFlashAttribute("error", "Musisz być zalogowany, aby zarezerwować samochód");
            session.setAttribute("redirectAfterLogin", "/rezerwacje/formularz/" + samochodId);
            return "redirect:/login";
        }
        return "forward:/formularz.html";
    }

    // zapisz rezerwacje w systemie
//    @PostMapping("/zapisz")
//    public String zapisz(@RequestParam int samochodId, @RequestParam String dataOd, @RequestParam String dataDo,
//                         HttpSession session, RedirectAttributes redirectAttributes){
//
//        Uzytkownik user = (Uzytkownik)session.getAttribute("user");
//        if(user == null){
//            return "redirect:/login.html";
//        }
//        try {
//            Rezerwacja rezerwacja = new Rezerwacja();
//            rezerwacja.setDataOd(LocalDate.parse(dataOd));
//            rezerwacja.setDataDo(LocalDate.parse(dataDo)); // walidacja dat w service
//            rezerwacja.setIdSamochodu(samochodId);
//            rezerwacja.setIdUzytkownika(user.getId());
//            rezerwacja.setEmail(user.getEmail());
//            rezerwacja.setNrTel(user.getNrTel());
//
//            rezerwacjaService.create(rezerwacja);
//
//            redirectAttributes.addFlashAttribute("sukces", "Rezerwacja została utworzona!"); // pozwala zachowac attributes przy redirect
//            return "redirect:/rezerwacje/moje";
//
//        } catch (RuntimeException e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//            return "redirect:/rezerwacje/nowa/" + samochodId; // przekieruj znow do formularza
//        }
//    }

    @PostMapping("/zapisz")
    @ResponseBody
    public Map<String,String> zapisz(@RequestParam int samochodId, @RequestParam String dataOd, @RequestParam String dataDo, HttpSession session) {

        Map<String,String> response = new HashMap<>();

        Uzytkownik user = (Uzytkownik) session.getAttribute("user");
        if(user == null){
            response.put("status", "error");
            response.put("message", "Nie jesteś zalogowany");
            return response;
        }

        try {
            Rezerwacja rezerwacja = new Rezerwacja();
            rezerwacja.setDataOd(LocalDateTime.parse(dataOd));
            rezerwacja.setDataDo(LocalDateTime.parse(dataDo));
            rezerwacja.setIdSamochodu(samochodId);
            rezerwacja.setIdUzytkownika(user.getId());
            rezerwacja.setEmail(user.getEmail());
            rezerwacja.setNrTel(user.getNrTel());

            rezerwacjaService.create(rezerwacja);

            response.put("status", "success");
            response.put("message", "Rezerwacja została utworzona.");
            return response;

        } catch (RuntimeException e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
            return response;
        }
    }

    // TODO: frontend odczytac status i obsluzyc tego jsona response
    // jesli status "success" -> "Rezerwacja została utworzona"
    // jesli status "error" -> komunikat błędu

    @GetMapping("/moje")// TODO: wykorzystac tez ten endpoint w panelu uzytkownika: dodac podlgad rezerwacji uzytkownika
    @ResponseBody
    public List<Rezerwacja> mojeRezerwacjeJson(HttpSession session) {
        Uzytkownik user = (Uzytkownik) session.getAttribute("user");
        if (user == null) {
            throw new RuntimeException("Nie jesteś zalogowany");
        }
        return rezerwacjaService.findByUzytkownikId(user.getId()); // zwroocone w json do obslugi w js
    }

    // do obliczania ceny koncowej
    @PostMapping("/wycena")
    @ResponseBody
    public Map<String, Object> wycena(@RequestParam int samochodId, @RequestParam String dataOd, @RequestParam String dataDo){

        Map<String, Object> response = new HashMap<>();

        Samochod samochod = samochodService.findById(samochodId);
        if (samochod == null) {
            response.put("status", "error");
            response.put("message", "Nie znaleziono samochodu");
        }

        try{
            // z string na date parsuje
            LocalDateTime od = LocalDateTime.parse(dataOd);
            LocalDateTime do_ = LocalDateTime.parse(dataDo);

            BigDecimal cena = rezerwacjaService.obliczCeneKoncowa(od, do_, samochod.getCena());

            response.put("status", "success");
            response.put("cenaKoncowa", cena); // dodaje do mapy, ktora pozniej idze jako json
        }catch(RuntimeException e){
            response.put("status", "error");
            response.put("message", e.getMessage());
        }

        return response;
    }
}

