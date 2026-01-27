package pl.wynajem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
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

}
