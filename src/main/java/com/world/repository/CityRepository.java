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

import com.world.domain.City;

@Repository
public class CityRepository {

	@Autowired
    JdbcTemplate jdbcTemplate;
	private static final Logger log = LoggerFactory.getLogger(CityRepository.class);

    public List<City> findAll() {
        List<City> result = jdbcTemplate.query(
                "SELECT id, name, square, population FROM city",
                (rs, rowNum) -> new City(rs.getInt("id"), rs.getString("name"), rs.getBigDecimal("square"), rs.getInt("population"))
        );
        result.forEach(city -> log.info("select: " + city.toString()));
        return result;
    }
	
    public int save(City input) {
        String sql = "INSERT INTO city(name, square, population) VALUES (?,?,?)"; 
		KeyHolder holder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(new PreparedStatementCreator() {           
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, input.getName());
                ps.setBigDecimal(2, input.getSquare());
                ps.setInt(3, input.getPopulation());
                return ps;
            }
        }, holder);
		
        Map<String, Object> map = holder.getKeys(); 
        int id = (int) map.get("id");
        log.info("insert: " + id + " " + input.toString());
        return id;
    }
	
	public void saveAll(List<City> list) {
		String sql = "INSERT INTO city(name, square, population) VALUES (?,?,?)";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				City item = list.get(i);
				ps.setString(1, item.getName());
				ps.setBigDecimal(2, item.getSquare());
				ps.setInt(3, item.getPopulation());
			}

			@Override
			public int getBatchSize() {
				return list.size();
			}
		});

		log.info("insert: " + Arrays.toString(list.toArray()));
	}
	
    public void updateFts() {
		jdbcTemplate.update("UPDATE city SET cityfts=setweight( coalesce( to_tsvector('ru', name),''),'C');");
        log.info("update city full text search index");
    }
	
	public City findById(String id) {
		City result =  jdbcTemplate.queryForObject(
                "SELECT id, name, square, population FROM city WHERE id = ?", new Object[] {id},
                (rs, rowNum) -> new City(rs.getInt("id"), rs.getString("name"), rs.getBigDecimal("square"), rs.getInt("population"))
        );
		log.info("findById: " + result);
		return result;
	}
	
	public void deleteById(String id) {
		jdbcTemplate.update("DELETE from city WHERE id = ?", new Object[] {id});
		log.info("deleteById: " + id);
	}
}