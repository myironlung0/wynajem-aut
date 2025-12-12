package pl.wynajem.models;

import java.time.LocalDate;

public class Rezerwacja {
    private int id;
    private LocalDate dataOd;
    private LocalDate dataDo;
    private int nrTel;
    private String email;
    private int idUzytkownika;
    private int idSamochodu;
    private String nrRezerwacji;
    private String status;  // 'potwierdzona', 'anulowana', 'w_trakcie', 'zakonczona'

    public Rezerwacja() {}

    public Rezerwacja(LocalDate dataOd, LocalDate dataDo, int nrTel, String email, int idUzytkownika, int idSamochodu, String nrRezerwacji) {
        this.dataOd = dataOd;
        this.dataDo = dataDo;
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

    public LocalDate getDataOd() {
        return dataOd;
    }

    public void setDataOd(LocalDate dataOd) {
        this.dataOd = dataOd;
    }

    public LocalDate getDataDo() {
        return dataDo;
    }

    public void setDataDo(LocalDate dataDo) {
        this.dataDo = dataDo;
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
