package pl.wynajem.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pl.wynajem.models.Rezerwacja;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class RezerwacjaRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Rezerwacja> rowMapper = new RowMapper<Rezerwacja>() {
        @Override
        public Rezerwacja mapRow(ResultSet rs, int rowNum) throws SQLException {
            Rezerwacja rezerwacja = new Rezerwacja();
            rezerwacja.setId(rs.getInt("id"));
            rezerwacja.setDataOd((rs.getDate("data_od")).toLocalDate());
            rezerwacja.setDataDo(rs.getDate("data_do").toLocalDate());
            rezerwacja.setNrTel(rs.getInt("nr_tel"));
            rezerwacja.setEmail(rs.getString("email"));
            rezerwacja.setIdUzytkownika(rs.getInt("id_uzytkownika"));
            rezerwacja.setIdSamochodu(rs.getInt("id_samochodu"));
            rezerwacja.setNrRezerwacji(rs.getString("nr_rezerwacji"));
            rezerwacja.setStatus(rs.getString("status"));

            return rezerwacja;
        }
    };

    public List<Rezerwacja> getAll() {
        return jdbcTemplate.query("SELECT * FROM rezerwacja ORDER BY nr_rezerwacji", rowMapper);
    }

    public Rezerwacja findById(int id) {
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM rezerwacja WHERE id = ?", rowMapper, id);
        }catch(Exception e){
            return null;
        }
    }

    public List<Rezerwacja> findByStatus(String  status) {
        return jdbcTemplate.query("SELECT * FROM rezerwacja WHERE status = ?", rowMapper, status);

    }

    public List<Rezerwacja> findByIdUzytkownika(int idUzytkownika){
        return jdbcTemplate.query("SELECT * FROM rezerwacja WHERE id_uzytkownika = ?", rowMapper, idUzytkownika);
    }

    public List<Rezerwacja> findByIdSamochodu(int idSamochodu){
        return jdbcTemplate.query("SELECT * FROM rezerwacja WHERE id_samochodu = ?", rowMapper, idSamochodu);
    }

    public Rezerwacja findByNrRezerwacji(String nrRezerwacji){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM rezerwacja WHERE nr_rezerwacji = ?", rowMapper, nrRezerwacji);
        }catch(Exception e){
            return null;
        }
    }

    public List<Rezerwacja> findAktywne(){
        return jdbcTemplate.query("SELECT * FROM rezerwacja WHERE status IN ('potwierdzona', 'w_trakcie') ", rowMapper);
    }

    public List<Rezerwacja> findByOkres(LocalDate dataOd, LocalDate dataDo){
        return jdbcTemplate.query("SELECT * FROM rezerwacja WHERE data_od >= ? AND data_do <= ? ORDER BY data_od",
                rowMapper, dataOd, dataDo);
    }

    public void updateStatus(int rezerwacjaId, String nowyStatus) {
        String sql = "UPDATE rezerwacja SET status = ? WHERE id = ?";
        jdbcTemplate.update(sql, nowyStatus, rezerwacjaId);
    }

    public boolean nrRezerwacjiExists(String nr){
        String sql = "SELECT COUNT(*) FROM rezerwacja WHERE nr_rezerwacji = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, nr);

        return count != null && count > 0;
    }

    public boolean czySamochodWolny(int idSamochodu, LocalDate dataOd, LocalDate dataDo){
        String sql = "SELECT COUNT(*) FROM rezerwacja WHERE id_samochodu = ? " +
                "AND status IN ('potwierdzona', 'w_trakcie') AND data_od <= ? AND data_do >= ?";
        // queryForObject moze zwrocic null, a int nie moze byc null
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, idSamochodu, dataOd, dataDo);
        return count == null || count == 0;
    }

    public void create(Rezerwacja rezerwacja) {
        if(nrRezerwacjiExists(rezerwacja.getNrRezerwacji())){
            throw new RuntimeException("Rezerwacja z tym numerem juz istnieje");
        }

        String sql = "INSERT INTO rezerwacja (data_od, data_do, nr_tel, email, id_uzytkownika, id_samochodu, nr_rezerwacji, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                rezerwacja.getDataOd(),
                rezerwacja.getDataDo(),
                rezerwacja.getNrTel(),
                rezerwacja.getEmail(),
                rezerwacja.getIdUzytkownika(),
                rezerwacja.getIdSamochodu(),
                rezerwacja.getNrRezerwacji(),
                rezerwacja.getStatus()
        );
    }

    public void update(Rezerwacja rezerwacja){
        String sql = "UPDATE rezerwacja SET data_od = ?, data_do = ?, nr_tel = ?, email = ?, " +
                "id_uzytkownika = ?, id_samochodu = ?, nr_rezerwacji = ?, status = ? WHERE id = ?";

        jdbcTemplate.update(sql,
                rezerwacja.getDataOd(),
                rezerwacja.getDataDo(),
                rezerwacja.getNrTel(),
                rezerwacja.getEmail(),
                rezerwacja.getIdUzytkownika(),
                rezerwacja.getIdSamochodu(),
                rezerwacja.getNrRezerwacji(),
                rezerwacja.getStatus(),
                rezerwacja.getId()
        );
    }

    public void delete(int id) {
        String sql = "DELETE FROM rezerwacja WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

}
