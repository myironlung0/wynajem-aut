package pl.wynajem.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pl.wynajem.models.Ubezpieczenie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class UbezpieczenieRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public RowMapper<Ubezpieczenie> rowMapper= new RowMapper<Ubezpieczenie>(){
        @Override
        public Ubezpieczenie mapRow(ResultSet rs, int rowNum) throws SQLException {
            Ubezpieczenie ubezpieczenie = new Ubezpieczenie();
            ubezpieczenie.setId(rs.getInt("id"));
            ubezpieczenie.setDataUbezpieczenia((rs.getDate("data_ubezpieczenia")).toLocalDate());
            ubezpieczenie.setKoszt(rs.getBigDecimal("koszt"));
            ubezpieczenie.setIdSamochodu(rs.getInt("id_samochodu"));

            return ubezpieczenie;
        }
    };

    public List<Ubezpieczenie> findAll(){
        return jdbcTemplate.query("SELECT * FROM ubezpieczenie", rowMapper);
    }

    public Ubezpieczenie findById(int id){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM ubezpieczenie WHERE id = ?", rowMapper, id);
        }catch(Exception e){
            return null;
        }
    }

    public List<Ubezpieczenie> findByIdSamochodu(int idSamochodu){
        return jdbcTemplate.query("SELECT * FROM ubezpieczenie WHERE id_samochodu = ?", rowMapper, idSamochodu);
    }

    public List<Ubezpieczenie> findByDataUbezpieczenia(LocalDate dataUbezpieczenia){
        return jdbcTemplate.query("SELECT * FROM ubezpieczenie WHERE data_ubezpieczenia = ?", rowMapper, dataUbezpieczenia);
    }

    public void create(Ubezpieczenie ubezpieczenie){
        String sql = "INSERT INTO ubezpieczenie (data_ubezpieczenia, koszt, id_samochodu) VALUES (?, ?, ?)";

        jdbcTemplate.update(sql,
                ubezpieczenie.getDataUbezpieczenia(),
                ubezpieczenie.getKoszt(),
                ubezpieczenie.getIdSamochodu()
        );
    }

    public void update(Ubezpieczenie ubezpieczenie){
        String sql = "UPDATE ubezpieczenie SET data_ubezpieczenia = ?, koszt = ?,  id_samochodu = ? WHERE id = ?";

        jdbcTemplate.update(sql,
                ubezpieczenie.getDataUbezpieczenia(),
                ubezpieczenie.getKoszt(),
                ubezpieczenie.getIdSamochodu(),
                ubezpieczenie.getId()
        );
    }

    public void delete(int id){
        String sql = "DELETE FROM ubezpieczenie WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
