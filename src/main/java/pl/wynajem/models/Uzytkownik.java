package pl.wynajem.models;

import java.time.LocalDate;

public class Uzytkownik {
    private int id;
    private String imie;
    private String nazwisko;
    private String adres;
    private String miejscowosc;
    private int nrTel;
    private String email;
    private String nrDowodu;
    private LocalDate dataUr;
    private String czyZweryfikowany;

    // constructors
    public Uzytkownik() {}

    public Uzytkownik(String firstName, String lastName, String email, int phoneNr) {
        this.imie = firstName;
        this.nazwisko = lastName;
        this.email = email;
        this.czyZweryfikowany = "N";  // default niezweryfikowany
        this.nrTel = phoneNr;
    }

    // getter n setters
    public int getId() { return id; }

    public String getImie() {return imie;}

    public String getNazwisko() {return nazwisko;}

    public String getAdres() {return adres;}

    public String getMiejscowosc() {return miejscowosc;}

    public int getNrTel() {return nrTel;}

    public String getEmail() {return email;}

    public String getNrDowodu() {return nrDowodu;}

    public LocalDate getDataUr() {return dataUr;}

    public String getCzyZweryfikowany() {return czyZweryfikowany;}

    public void setId(int id) {this.id = id;}

    public void setImie(String imie) {this.imie = imie;}

    public void setNazwisko(String nazwisko) {this.nazwisko = nazwisko;}

    public void setAdres(String adres) {this.adres = adres;}

    public void setMiejscowosc(String miejscowosc) {this.miejscowosc = miejscowosc;}

    public void setNrTel(int nrTel) {this.nrTel = nrTel;}

    public void setEmail(String email) {this.email = email;}

    public void setNrDowodu(String nrDowodu) {this.nrDowodu = nrDowodu;}

    public void setDataUr(LocalDate dataUr) { this.dataUr = dataUr; }

    public void setCzyZweryfikowany(String czyZweryfikowany) { this.czyZweryfikowany = czyZweryfikowany; }

    @Override
    public String toString() {
        return "Uzytkownik{" +
                "id=" + id +
                ", imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", adres='" + adres + '\'' +
                ", miejscowosc='" + miejscowosc + '\'' +
                ", nrTel=" + nrTel +
                ", email='" + email + '\'' +
                ", nrDowodu='" + nrDowodu + '\'' +
                ", dataUr=" + dataUr +
                ", czyZweryfikowany='" + czyZweryfikowany + '\'' +
                '}';
    }
}
