package pl.wynajem.models;

public class Logowanie {
    private int id;
    private int idUzytkownika;
    private String nazwaUzytkownika;
    private String hasloHash;

    // constructors
    public Logowanie() {}

    public Logowanie( int idUzytkownika, String nazwaUzytkownika, String hasloHash) {
        this.idUzytkownika = idUzytkownika;
        this.nazwaUzytkownika = nazwaUzytkownika;
        this.hasloHash = hasloHash;
    }

    // getter n setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUzytkownika() {
        return idUzytkownika;
    }

    public void setIdUzytkownika(int idUzytkownika) {
        this.idUzytkownika = idUzytkownika;
    }

    public String getNazwaUzytkownika() {
        return nazwaUzytkownika;
    }

    public void setNazwaUzytkownika(String nazwaUzytkownika) {
        this.nazwaUzytkownika = nazwaUzytkownika;
    }

    public String getHasloHash() {
        return hasloHash;
    }

    public void setHasloHash(String hasloHash) {
        this.hasloHash = hasloHash;
    }

    @Override
    public String toString() {
        return "Logowanie{" +
                "id=" + id +
                ", idUzytkownika=" + idUzytkownika +
                ", nazwaUzytkownika='" + nazwaUzytkownika + '\'' +
                ", hasloHash='" + hasloHash + '\'' +
                '}';
    }
}
