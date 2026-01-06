package pl.wynajem.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pl.wynajem.models.Przeglad;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PrzegladRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public RowMapper<Przeglad> rowMapper = new RowMapper<Przeglad>() {
        @Override
        public Przeglad mapRow(ResultSet rs, int rowNum) throws SQLException {
            Przeglad przeglad = new Przeglad();
            przeglad.setId(rs.getInt("id"));
            java.sql.Date sqlDate = rs.getDate("data_przegladu");
            if (sqlDate != null) {
                przeglad.setDataPrzegladu(sqlDate.toLocalDate());
            } else {
                przeglad.setDataPrzegladu(null);
            }
            przeglad.setIdSamochodu(rs.getInt("id_samochodu"));
            return przeglad;
        }
    };

    public List<Przeglad> findAll(){
        return jdbcTemplate.query("SELECT * FROM przeglad", rowMapper);
    }

    public Przeglad findById(int id){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM przeglad WHERE id = ?", rowMapper, id);
        }catch(Exception e){
            return null;
        }
    }

    public Przeglad findByIdSamochodu(int idSamochodu ){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM przeglad WHERE id_samochodu = ?", rowMapper, idSamochodu);
        }catch(Exception e){
            return null;
        }
    }

    public void create(Przeglad przeglad){
        String sql = "INSERT INTO przeglad (data_przegladu, id_samochodu) VALUES (?, ?)";
        jdbcTemplate.update(sql,
                przeglad.getDataPrzegladu(),
                przeglad.getIdSamochodu()
        );
    }

    public void update(){

    }

    public void delete(){

    }
}
