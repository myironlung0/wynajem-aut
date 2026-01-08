package pl.wynajem.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import pl.wynajem.models.Uzytkownik;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class UzytkownikRepository {
    @Autowired  // <- spring automatycznie wstrzyknie jdbctemplate
    private JdbcTemplate jdbcTemplate;

    // zamienia wiersze z bazy na obiekt Uzytkownik
    private RowMapper<Uzytkownik> rowMapper = new RowMapper<Uzytkownik>(){
        @Override
        public Uzytkownik mapRow(ResultSet rs, int rowNum) throws SQLException {
            Uzytkownik uzytkownik = new Uzytkownik();
            uzytkownik.setId(rs.getInt("id"));
            uzytkownik.setImie(rs.getString("imie"));
            uzytkownik.setNazwisko(rs.getString("nazwisko"));
            uzytkownik.setAdres(rs.getString("adres"));
            uzytkownik.setMiejscowosc(rs.getString("miejscowosc"));
            uzytkownik.setNrTel(rs.getInt("nr_tel"));
            uzytkownik.setEmail(rs.getString("email"));
            uzytkownik.setNrDowodu(rs.getString("nr_dowodu"));
            java.sql.Date dataUr = rs.getDate("data_ur");
            if(dataUr != null){ // bo data ur moze byc null
                uzytkownik.setDataUr(dataUr.toLocalDate());
            }
            uzytkownik.setCzyZweryfikowany(rs.getString("czy_zweryfikowany"));

            return uzytkownik;
        }
    };

    // METHODS
    public List<Uzytkownik> findAll(){
        return jdbcTemplate.query("select * from uzytkownik ORDER BY nazwisko, imie", rowMapper);
    }

    public Uzytkownik findById(int id){
        try {
            return jdbcTemplate.queryForObject("select * from uzytkownik where id = ?", rowMapper, id);
        } catch (Exception e) {
            return null; // jesli nic nie znajdzie
        }
    }

    public Uzytkownik findByImie(String imie){
        try {
            return jdbcTemplate.queryForObject("select * from uzytkownik where imie = ?", rowMapper, imie);
        } catch (Exception e) {
            return null;
        }
    }

    public Uzytkownik findByNazwisko(String nazwisko) {
        try {
            return jdbcTemplate.queryForObject("select * from uzytkownik where nazwisko = ?", rowMapper, nazwisko);
        } catch (Exception e) {
            return null;
        }
    }

    // wyszukaj po emailu, dodaj uzytkownika, sprawdz czy mail/telefon istnieje
    public Uzytkownik findByEmail(String email){
        try {
            return jdbcTemplate.queryForObject("select * from uzytkownik where email = ?", rowMapper, email);
        }catch (Exception e){
            return null;
        }
    }

    public void create(Uzytkownik uzytkownik){ // returns the num of rows affected
        if (emailExists(uzytkownik.getEmail())) {
            throw new RuntimeException("Email juz istnieje.");
        }

        if (phoneExists(uzytkownik.getNrTel())) {
            throw new RuntimeException("Numer telefonu juz istnieje.");
        }

        String sql = "INSERT INTO uzytkownik (imie, nazwisko, adres, miejscowosc, nr_tel, email, nr_dowodu, data_ur, czy_zweryfikowany) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, uzytkownik.getImie());
            ps.setString(2, uzytkownik.getNazwisko());
            ps.setString(3, uzytkownik.getAdres());
            ps.setString(4, uzytkownik.getMiejscowosc());
            ps.setInt(5, uzytkownik.getNrTel());
            ps.setString(6, uzytkownik.getEmail());
            ps.setString(7, uzytkownik.getNrDowodu());
            ps.setObject(8, uzytkownik.getDataUr());
            ps.setString(9, uzytkownik.getCzyZweryfikowany());
            return ps;
        }, keyHolder);

        // teraz ID użytkownika jest ustawione poprawnie
        uzytkownik.setId(keyHolder.getKey().intValue());
    }

    public void delete(int id){
        String sql = "DELETE FROM uzytkownik WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void update(Uzytkownik uzytkownik){
        String sql = "UPDATE uzytkownik SET " +
                "imie = ?, nazwisko = ?, adres = ?, miejscowosc = ?, nr_tel = ?, email = ?, nr_dowodu = ?, data_ur = ?, czy_zweryfikowany = ?" +
                " WHERE id = ? ";

        jdbcTemplate.update(sql,
                uzytkownik.getImie(),
                uzytkownik.getNazwisko(),
                uzytkownik.getAdres(),
                uzytkownik.getMiejscowosc(),
                uzytkownik.getNrTel(),
                uzytkownik.getEmail(),
                uzytkownik.getNrDowodu(),
                uzytkownik.getDataUr(),
                uzytkownik.getCzyZweryfikowany(),
                uzytkownik.getId()
        );
    }

    public boolean phoneExists(int nrTel){
        String sql = "SELECT COUNT(*) FROM uzytkownik WHERE nr_tel = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, nrTel);
        return count != null && count > 0;
    }

    public boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM uzytkownik WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

}
