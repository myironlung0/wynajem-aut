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
        public Samochod mapRow(ResultSet rs, int rowNum) throws SQLException {
            Samochod samochod = new Samochod();
            samochod.setId(rs.getInt("id"));
            samochod.setMarka(rs.getString("marka"));
            samochod.setModel(rs.getString("model"));
            samochod.setNrVIN(rs.getString("nr_VIN"));
            samochod.setPrzebieg(rs.getInt("przebieg"));
            samochod.setCena(rs.getBigDecimal("cena"));

            return samochod;
        }
    };

    public List<Samochod> findAll() {
        return jdbcTemplate.query("SELECT * FROM samochod", rowMapper);
    }

    public Samochod findById(int id) {
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM Samochod WHERE id = ?", rowMapper, id);
        }catch(Exception e){
            return null;
        }
    }

    public List<Samochod> findByMarka(String marka) {
        try{
            return jdbcTemplate.query("SELECT * FROM samochod WHERE marka = ?", rowMapper, marka);
        }catch(Exception e){
            return null;
        }
    }


    public List<Samochod> findByMarkaModel(String marka, String model) {
        try{
            return jdbcTemplate.query("SELECT * FROM Samochod WHERE marka = ? AND model = ?", rowMapper, marka, model);
        }catch(Exception e){
            return null;
        }
    }

    public List<Samochod> findCheaperThan(BigDecimal max) {
        try{
            return jdbcTemplate.query("SELECT * FROM Samochod WHERE cena <= ?", rowMapper, max);
        }catch(Exception e){
            return null;
        }
    }

    public List<Samochod> findCheaperThan(BigDecimal min, BigDecimal max) {
        try{
            return jdbcTemplate.query("SELECT * FROM Samochod WHERE cena<= ? AND cena>= ?", rowMapper, max, min);
        }catch(Exception e){
            return null;
        }
    }

    public void create(Samochod samochod) {
        if(vinExists(samochod.getNrVIN())){
            throw new RuntimeException("Samochod z tym VIN juz istnieje");
        }

        String sql = "INSERT INTO samochod (marka, model, nr_VIN, przebieg, cena) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                samochod.getMarka(),
                samochod.getModel(),
                samochod.getNrVIN(),
                samochod.getPrzebieg(),
                samochod.getCena()
        );
    }

    public void delete(int id){
        String  sql = "DELETE FROM samochod WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void update(Samochod samochod){
        String sql = "UPDATE samochod SET " +
                "marka = ?, model = ?, nr_VIN = ?, przebieg = ?, cena = ? " +
                "WHERE id = ?";

        jdbcTemplate.update(sql,
                samochod.getMarka(),
                samochod.getModel(),
                samochod.getNrVIN(),
                samochod.getPrzebieg(),
                samochod.getCena(),
                samochod.getId()
        );
    }

    // sprawdz czy VIn istnieje
    public boolean vinExists(String vin) {
        String sql = "SELECT COUNT(*) FROM samochod WHERE nr_VIN = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, vin);
        return count != null && count > 0;
    }





}
