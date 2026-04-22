package pl.wynajem.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pl.wynajem.models.Samochod;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SamochodRepository {
    @Autowired  // <- spring automatycznie wstrzyknie jdbctemplate
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Samochod> rowMapper = new RowMapper<Samochod>(){
        @Override
        public Samochod mapRow(ResultSet rs, int rowNum) throws SQLException {
            Samochod samochod = new Samochod();
            samochod.setId(rs.getInt("id"));
            samochod.setMarka(rs.getString("marka"));
            samochod.setModel(rs.getString("model"));
            samochod.setNrVIN(rs.getString("nr_VIN"));
            samochod.setPrzebieg(rs.getInt("przebieg"));
            samochod.setCena(rs.getBigDecimal("cena"));
            samochod.setZdjecie(rs.getString("zdjecie"));

            return samochod;
        }
    };

    public List<Samochod> findAll() {
        return jdbcTemplate.query("SELECT * FROM samochod", rowMapper);
    }

    public Samochod findById(int id) {
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM samochod WHERE id = ?", rowMapper, id);
        }catch(Exception e){
            return null;
        }
    }

    public List<Samochod> findByMarka(String marka) {
        return jdbcTemplate.query("SELECT * FROM samochod WHERE marka = ?", rowMapper, marka);

    }


    public List<Samochod> findByMarkaModel(String marka, String model) {

        return jdbcTemplate.query("SELECT * FROM samochod WHERE marka = ? AND model = ?", rowMapper, marka, model);

    }

    public List<Samochod> findCheaperThan(BigDecimal max) {
        return jdbcTemplate.query("SELECT * FROM samochod WHERE cena <= ?", rowMapper, max);

    }

    public List<Samochod> findCenaInRange(BigDecimal min, BigDecimal max) {
        return jdbcTemplate.query("SELECT * FROM samochod WHERE cena<= ? AND cena>= ?", rowMapper, max, min);
    }

    public void create(Samochod samochod) {
        if(vinExists(samochod.getNrVIN())){
            throw new RuntimeException("Samochod z tym VIN juz istnieje");
        }

        String sql = "INSERT INTO samochod (marka, model, nr_VIN, przebieg, cena, zdjecie) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                samochod.getMarka(),
                samochod.getModel(),
                samochod.getNrVIN(),
                samochod.getPrzebieg(),
                samochod.getCena(),
                samochod.getZdjecie()
        );
    }

    public void delete(int id){
        String  sql = "DELETE FROM samochod WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void update(Samochod samochod){
        String sql = "UPDATE samochod SET " +
                "marka = ?, model = ?, nr_VIN = ?, przebieg = ?, cena = ?, zdjecie = ? " +
                "WHERE id = ?";

        jdbcTemplate.update(sql,
                samochod.getMarka(),
                samochod.getModel(),
                samochod.getNrVIN(),
                samochod.getPrzebieg(),
                samochod.getCena(),
                samochod.getZdjecie(),
                samochod.getId()
        );
    }

    // sprawdz czy VIn istnieje
    public boolean vinExists(String vin) {
        String sql = "SELECT COUNT(*) FROM samochod WHERE nr_VIN = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, vin);
        return count != null && count > 0;
    }

    public List<Samochod> search(String search) {
        if (search == null || search.isBlank()) {
            return findAll();
        }

        String sql = "SELECT * FROM samochod " +
                "WHERE LOWER(marka) LIKE ? " +
                "OR LOWER(model) LIKE ? " +
                "OR LOWER(nr_VIN) LIKE ?";

        String param = "%" + search.toLowerCase() + "%";

        return jdbcTemplate.query(sql, rowMapper, param, param, param);
    }
}
