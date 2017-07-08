package com.world.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.world.domain.Street;

@Repository
public class StreetRepository {

	@Autowired
    JdbcTemplate jdbcTemplate;
	private static final Logger log = LoggerFactory.getLogger(StreetRepository.class);

	@Transactional(readOnly=true)
    public List<Street> findAll() {
        List<Street> result = jdbcTemplate.query(
                "SELECT id, name, extent FROM street",
                (rs, rowNum) -> new Street(rs.getInt("id"), rs.getString("name"), rs.getInt("extent"))
        );
        result.forEach(street -> log.info("select: " + street.toString()));
        return result;
    }
	
	@Transactional
    public void save(Street input) {
        jdbcTemplate.update("INSERT INTO street(name, extent) VALUES (?,?)", new Object[] {input.getName(), input.getExtent()});
        log.info("insert: " + input.toString());
    }
	
	@Transactional
	public void saveAll(List<Street> list) {
		String sql = "INSERT INTO street(name, extent) VALUES (?,?)";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Street item = list.get(i);
				ps.setString(1, item.getName());
				ps.setInt(2, item.getExtent());
			}

			@Override
			public int getBatchSize() {
				return list.size();
			}
		});

		log.info("insert: " + Arrays.toString(list.toArray()));
	}
	
	@Transactional(readOnly=true)
	public Street findById(String id) {
		Street result =  jdbcTemplate.queryForObject(
                "SELECT id, name, extent FROM street WHERE id = ?", new Object[] {id},
                (rs, rowNum) -> new Street(rs.getInt("id"), rs.getString("name"), rs.getInt("extent"))
        );
		log.info("findById: " + result);
		return result;
	}
	
	@Transactional
	public void deleteById(String id) {
		jdbcTemplate.update("DELETE from street WHERE id = ?", new Object[] {id});
		log.info("deleteById: " + id);
	}
}