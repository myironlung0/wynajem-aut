package pl.wynajem.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Rezerwacja {
    private int id;
    private LocalDateTime dataOd;
    private LocalDateTime dataDo;
    private BigDecimal cenaKoncowa;
    private int nrTel;
    private String email;
    private int idUzytkownika;
    private int idSamochodu;
    private String nrRezerwacji;
    private String status;  // 'potwierdzona', 'anulowana', 'w_trakcie', 'zakonczona'

    public Rezerwacja() {}

    public Rezerwacja(LocalDateTime dataOd, LocalDateTime dataDo, BigDecimal cenaKoncowa, int nrTel, String email, int idUzytkownika, int idSamochodu, String nrRezerwacji) {
        this.dataOd = dataOd;
        this.dataDo = dataDo;
        this.cenaKoncowa = cenaKoncowa;
        this.nrTel = nrTel;
        this.email = email;
        this.idUzytkownika = idUzytkownika;
        this.idSamochodu = idSamochodu;
        this.nrRezerwacji = nrRezerwacji;
        this.status = "potwierdzona"; // domyslnie
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDataOd() {
        return dataOd;
    }

    public void setDataOd(LocalDateTime dataOd) {
        this.dataOd = dataOd;
    }

    public LocalDateTime getDataDo() {
        return dataDo;
    }

    public void setDataDo(LocalDateTime dataDo) {
        this.dataDo = dataDo;
    }

    public BigDecimal getCenaKoncowa() {
        return cenaKoncowa;
    }

    public void setCenaKoncowa(BigDecimal cenaKoncowa) {
        this.cenaKoncowa = cenaKoncowa;
    }

    public int getNrTel() {
        return nrTel;
    }

    public void setNrTel(int nrTel) {
        this.nrTel = nrTel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdUzytkownika() {
        return idUzytkownika;
    }

    public void setIdUzytkownika(int idUzytkownika) {
        this.idUzytkownika = idUzytkownika;
    }

    public int getIdSamochodu() {
        return idSamochodu;
    }

    public void setIdSamochodu(int idSamochodu) {
        this.idSamochodu = idSamochodu;
    }

    public String getNrRezerwacji() {
        return nrRezerwacji;
    }

    public void setNrRezerwacji(String nrRezerwacji) {
        this.nrRezerwacji = nrRezerwacji;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Rezerwacja{" +
                "id=" + id +
                ", dataOd=" + dataOd +
                ", dataDo=" + dataDo +
                ", nrTel=" + nrTel +
                ", email='" + email + '\'' +
                ", idUzytkownika=" + idUzytkownika +
                ", idSamochodu=" + idSamochodu +
                ", nrRezerwacji='" + nrRezerwacji + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

//    public boolean jestZakonczona() {
//        return status.equals("zakonczona") ||
//                (status.equals("w_trakcie") && dataDo.isBefore(LocalDate.now()));
//    }
}
