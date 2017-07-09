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

import com.world.domain.City;

@Repository
public class CityRepository {

	@Autowired
    JdbcTemplate jdbcTemplate;
	private static final Logger log = LoggerFactory.getLogger(CityRepository.class);

	@Transactional(readOnly=true)
    public List<City> findAll() {
        List<City> result = jdbcTemplate.query(
                "SELECT id, name, square, population FROM city",
                (rs, rowNum) -> new City(rs.getInt("id"), rs.getString("name"), rs.getBigDecimal("square"), rs.getInt("population"))
        );
        result.forEach(city -> log.info("select: " + city.toString()));
        return result;
    }
	
	@Transactional
    public void save(City input) {
		//TODO: holderkey 
		//https://stackoverflow.com/questions/10597477/getting-auto-generated-key-from-row-insertion-in-spring-3-postgresql-8-4-9
        jdbcTemplate.update("INSERT INTO city(name, square, population) VALUES (?,?,?)", new Object[] {input.getName(), input.getSquare(), input.getPopulation()});
        log.info("insert: " + input.toString());
    }
	
	@Transactional
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
	
	@Transactional(readOnly=true)
	public City findById(String id) {
		City result =  jdbcTemplate.queryForObject(
                "SELECT id, name, square, population FROM city WHERE id = ?", new Object[] {id},
                (rs, rowNum) -> new City(rs.getInt("id"), rs.getString("name"), rs.getBigDecimal("square"), rs.getInt("population"))
        );
		log.info("findById: " + result);
		return result;
	}
	
	@Transactional
	public void deleteById(String id) {
		jdbcTemplate.update("DELETE from city WHERE id = ?", new Object[] {id});
		log.info("deleteById: " + id);
	}
}