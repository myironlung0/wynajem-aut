package pl.wynajem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller //java annotation, np override
public class HomeController {

    // strona glowna
    @RequestMapping("/")
    public String glowna() {
        return "glowna";
    }

    //formularz wyszukiwania aut
    @RequestMapping("/wyszukaj")
    public String formularzWyszukiwania() {
        return "wyszukaj";
    }

    // wyniki wyszukiwania
    @RequestMapping("/wyniki")
    public String wynikiWyszukiwania() {
        return "wyniki";
    }

    //szczegoly samochodu
    @RequestMapping("/samochod/{id}")
    public String sczegolySamochodu() {
        return "szczegoly";
    }


    // strona o nas
    @RequestMapping("/onas")
    public String oNas() {
        return "onas";
    }

    // strona kontakt
    @RequestMapping("/kontakt")
    public String kontakt() {
        return "kontakt";
    }

    // logowanie
    @RequestMapping("/logowanie")
    public String logowanie() {
        return "logowanie";
    }

    //testowa   `
    @RequestMapping("/test")
    public String test() {
        return "test";
    }
}