package pl.wynajem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.wynajem.models.Samochod;
import pl.wynajem.services.SamochodService;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class SamochodController {
    private final SamochodService samochodService;

    public SamochodController(SamochodService samochodService){
        this.samochodService = samochodService;
    }

    @GetMapping("/samochody") // fetch('/samochody') w JS
    @ResponseBody  // <- zwraca json zamiast nazwy widoku
    public List<Samochod> getAll() {
        return samochodService.findAll();
    }

    @GetMapping("/samochody/{id}")
    @ResponseBody
    public Samochod getById(@PathVariable int id) {
        return samochodService.findById(id);
    }

    @GetMapping("/samochody/marka/{marka}")
    @ResponseBody
    public List<Samochod> getByMarka(@PathVariable String marka) {
        return samochodService.findByMarka(marka);
    }

    @GetMapping("/samochody/marka/{marka}/model/{model}")
    @ResponseBody
    public List<Samochod> getByMarkaModel(@PathVariable String marka, @PathVariable String model) {
        return samochodService.findByMarkaModel(marka, model);
    }

    // tansze niz
    @GetMapping("/samochody/cena/max/{maxCena}")
    @ResponseBody
    public List<Samochod> getCheaperThan(@PathVariable BigDecimal maxCena) {
        return samochodService.findCheaperThan(maxCena);
    }

    // w przedziale cenowym
    @GetMapping("/samochody/cena/min/{minCena}/max/{maxCena}")
    @ResponseBody
    public List<Samochod> getByPriceRange(@PathVariable BigDecimal minCena, @PathVariable BigDecimal maxCena) {
        return samochodService.findCenaInRange(minCena, maxCena);
    }

    //formularz wyszukiwania aut
    @RequestMapping("/wyszukaj")
    public String stronaWyszukiwania() {
        return "redirect:/wyszukaj.html";
    }

    // TODO: metody dla admina: dodaj, usun, edytuj samochod
}
