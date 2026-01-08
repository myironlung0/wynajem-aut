package pl.wynajem.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pl.wynajem.models.Logowanie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LogowanieRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Logowanie> rowMapper= new RowMapper<Logowanie>() {
        @Override
        public Logowanie mapRow(ResultSet rs, int rowNum) throws SQLException {
            Logowanie logowanie = new Logowanie();
            logowanie.setId(rs.getInt("id"));
            logowanie.setIdUzytkownika(rs.getInt("id_uzytkownika"));
            logowanie.setNazwaUzytkownika(rs.getString("nazwa_uzytkownika"));
            logowanie.setHasloHash(rs.getString("haslo_hash"));
            return logowanie;
        }
    };

    public List<Logowanie> findAll() {
        return jdbcTemplate.query("SELECT * FROM logowanie ORDER BY nazwa_uzytkownika", rowMapper );
    }

    public Logowanie findById(int id) {
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM logowanie WHERE id = ?", rowMapper, id);
        }catch(Exception e){
            return null;
        }
    }

    public Logowanie findByIdUzytkownika(int idUzytkownika) {
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM logowanie WHERE id_uzytkownika = ?", rowMapper, idUzytkownika);
        }catch(Exception e){
            return null;
        }
    }

    public Logowanie findByNazwaUzytkownika(String nazwaUzytkownika){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM logowanie WHERE nazwa_uzytkownika = ? ", rowMapper, nazwaUzytkownika);
        }catch(Exception e){
            return null;
        }
    }

    public void create(Logowanie logowanie){
        if(nazwaUzytkownikaExists(logowanie.getNazwaUzytkownika())){
            throw new RuntimeException("Nazwa uzytkownika jzu istnieje.");
        }

        if(uzytkownikMaLogin(logowanie.getIdUzytkownika())){
            throw new RuntimeException("Uzytkownik juz istnieje.");
        }

        String sql = "INSERT INTO logowanie(id_uzytkownika, nazwa_uzytkownika, haslo_hash) " +
                "VALUES (?, ?, ?)";

        jdbcTemplate.update(sql,
                logowanie.getIdUzytkownika(),
                logowanie.getNazwaUzytkownika(),
                logowanie.getHasloHash()
        );
    }

    public void delete(int id){
        String sql = "DELETE FROM logowanie WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void update(Logowanie logowanie){
        String sql = "UPDATE logowanie SET nazwa_uzytkownika = ?, haslo_hash = ? WHERE id = ?";

        jdbcTemplate.update(sql,
                logowanie.getNazwaUzytkownika(),
                logowanie.getHasloHash(),
                logowanie.getId()
        );
    }

    public void updateHaslo(int uzytkownikId, String noweHasloHash) {
        String sql = "UPDATE logowanie SET haslo_hash = ? WHERE id_uzytkownika = ?";
        jdbcTemplate.update(sql, noweHasloHash, uzytkownikId);
    }

    public boolean nazwaUzytkownikaExists(String nazwaUzytkownika){
        String sql = "SELECT COUNT(*) FROM logowanie WHERE nazwa_uzytkownika = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, nazwaUzytkownika);
        return count > 0;
    }

    public boolean uzytkownikMaLogin(int idUzytkownik){
        String sql = "SELECT COUNT(*) FROM logowanie WHERE id_uzytkownika = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, idUzytkownik);
        return count > 0;
    }



}
