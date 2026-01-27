package pl.wynajem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.wynajem.models.Rezerwacja;
import pl.wynajem.models.Samochod;
import pl.wynajem.models.Uzytkownik;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void wyslijMailPowitalny(Uzytkownik user){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("rejestracja@wynajem-aut.pl");
        message.setTo(user.getEmail());
        message.setSubject("Witamy!");
        message.setText("Witaj" + user.getImie() + "! Zostałeś/aś pomyślnie zarejestrowany/a");
        mailSender.send(message);
    }

    public void wyslijPotwierdzenieRezerwacji(Uzytkownik user, Rezerwacja rezerwacja, Samochod samochod) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("rezerwacje@wynajem-aut.pl");
        message.setTo(user.getEmail());
        message.setSubject("Potwierdzenie rezerwacji #" + rezerwacja.getNrRezerwacji());

        String tekst = String.format(
                "Dzień dobry %s %s,\n\n" +
                        "Twoja rezerwacja została przyjęta!\n\n" +
                        "Szczegóły rezerwacji:\n" +
                        "• Numer rezerwacji: %s\n" +
                        "• Samochód: %s %s\n" +
                        "• Data odbioru: %s\n" +
                        "• Data zwrotu: %s\n" +
                        "• Koszt całkowity: %s PLN\n" +
                        "• Status: %s\n\n" +
                        "Dziękujemy za skorzystanie z naszych usług!\n" +
                        "Zespół Wynajem Samochodów\n" +
                        "rezerwacje@wynajem-aut.pl",
                user.getImie(),
                user.getNazwisko(),
                rezerwacja.getNrRezerwacji(),
                samochod.getMarka(),
                samochod.getModel(),
                rezerwacja.getDataOd(),
                rezerwacja.getDataDo(),
                rezerwacja.getCenaKoncowa(),
                rezerwacja.getStatus()
        );

        message.setText(tekst);
        mailSender.send(message);
    }

}
