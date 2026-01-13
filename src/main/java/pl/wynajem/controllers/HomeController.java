package pl.wynajem.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller //java annotation, np override
public class HomeController {

    // strona glowna
    @RequestMapping("/")
    public String glowna() {
        return "redirect:/glowna.html";
    }

    //formularz wyszukiwania aut
    @RequestMapping("/wyszukaj")
    public String formularzWyszukiwania() {
        return "redirect:/wyszukaj.html";
    }

    // wyniki wyszukiwania
    @RequestMapping("/wyniki")
    public String wynikiWyszukiwania() {
        return "redirect:/wyniki.html";
    }

    //szczegoly samochodu
    @RequestMapping("/samochod/{id}")
    public String sczegolySamochodu() {
        return "redirect:/szczegoly.html";
    }


    // strona o nas
    @RequestMapping("/onas")
    public String oNas() {
        return "redirect:/onas.html";
    }

    // strona kontakt
    @RequestMapping("/kontakt")
    public String kontakt() {
        return "redirect:/kontakt.html";
    }

//    // logowanie przeniesione do AuthController
//    @RequestMapping("/login")
//    public String login() {
//        return "login";
//    }

    //testowa   `
    @RequestMapping("/test")
    public String test() {
        return "test";
    }
}