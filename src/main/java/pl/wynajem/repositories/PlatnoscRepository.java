package pl.wynajem.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pl.wynajem.models.Platnosc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class PlatnoscRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public RowMapper<Platnosc> rowMapper = new RowMapper<Platnosc>() {
        @Override
        public Platnosc mapRow(ResultSet rs, int rowNum) throws SQLException{
            Platnosc platnosc = new Platnosc();
            platnosc.setId(rs.getInt("id"));
            platnosc.setIdUzytkownika(rs.getInt("id_uzytkownika"));
            platnosc.setCzyZrealizowano(rs.getString("czy_zrealizowano"));

            java.sql.Date sqlDate = rs.getDate("data_platnosci");
            if (sqlDate != null) {
                platnosc.setDataPlatnosci(sqlDate.toLocalDate());
            } else {
                platnosc.setDataPlatnosci(null);
            }            return platnosc;
        }
    };

    public List<Platnosc> findAll(){
        return jdbcTemplate.query("SELECT * FROM platnosc ORDER BY data_platnosci", rowMapper);
    }

    public Platnosc findById(int id){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM platnosc WHERE id = ?", rowMapper, id);
        }catch(Exception e){
            return null;
        }
    }

    public List<Platnosc> findByIdUzytkownika(int idUzytkownika){
        return jdbcTemplate.query("SELECT * FROM platnosc WHERE id_uzytkownika = ?", rowMapper, idUzytkownika);
    }

    public List<Platnosc> findByDataPlatnosci(LocalDate dataPlatnosci){
        return jdbcTemplate.query("SELECT * FROM platnosc WHERE data_platnosci  = ?", rowMapper, dataPlatnosci);
    }

    public List<Platnosc> findZrealizowane(){
        return jdbcTemplate.query("SELECT * FROM platnosc WHERE czy_zrealizowano = 'T'", rowMapper);
    }

    public void create(Platnosc platnosc) {
        String sql = "INSERT INTO platnosc (id_uzytkownika, czy_zrealizowano, data_platnosci) VALUES (?, ?, ?)";

        jdbcTemplate.update(sql,
                platnosc.getIdUzytkownika(),
                platnosc.getCzyZrealizowano(),
                platnosc.getDataPlatnosci()
        );
    }

    public void update(Platnosc platnosc) {
        String sql = "UPDATE platnosc SET id_uzytkownika = ?, czy_zrealizowano = ?, data_platnosci = ? WHERE id = ?";

        jdbcTemplate.update(sql,
                platnosc.getIdUzytkownika(),
                platnosc.getCzyZrealizowano(),
                platnosc.getDataPlatnosci(),
                platnosc.getId()
        );
    }

    public void delete(int id) {
        String sql = "DELETE FROM platnosc WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }


}
