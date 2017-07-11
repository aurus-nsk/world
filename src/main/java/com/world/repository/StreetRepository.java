package com.world.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.world.domain.Street;

@Repository
public class StreetRepository {

	@Autowired
    JdbcTemplate jdbcTemplate;
	private static final Logger log = LoggerFactory.getLogger(StreetRepository.class);

    public List<Street> findAll() {
        List<Street> result = jdbcTemplate.query(
                "SELECT id, name, extent FROM street",
                (rs, rowNum) -> new Street(rs.getInt("id"), rs.getString("name"), rs.getInt("extent"))
        );
        result.forEach(street -> log.info("select: " + street.toString()));
        return result;
    }
	
	public int save(Street input) {
		String sql = "INSERT INTO street(name, extent) VALUES (?,?)"; 
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {           
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, input.getName());
				ps.setInt(2, input.getExtent());
				return ps;
			}
		}, holder);

		Map<String, Object> map = holder.getKeys(); 
		int id = (int) map.get("id");
		log.info("insert: " + id + " " + input.toString());
		return id;
	}

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
	
    public void updateFts() {
		jdbcTemplate.update("UPDATE street SET streetfts=setweight( coalesce( to_tsvector('ru', name),''),'D');");
        log.info("update street full text search index");
    }
	
	public Street findById(String id) {
		Street result =  jdbcTemplate.queryForObject(
                "SELECT id, name, extent FROM street WHERE id = ?", new Object[] {id},
                (rs, rowNum) -> new Street(rs.getInt("id"), rs.getString("name"), rs.getInt("extent"))
        );
		log.info("findById: " + result);
		return result;
	}
	
	public void deleteById(String id) {
		jdbcTemplate.update("DELETE from street WHERE id = ?", new Object[] {id});
		log.info("deleteById: " + id);
	}
}