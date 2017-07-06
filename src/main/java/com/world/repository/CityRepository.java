package com.world.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
                (rs, rowNum) -> new City(rs.getInt("id"), rs.getString("name"), rs.getDouble("square"), rs.getInt("population"))
        );
        result.forEach(city -> log.info("select: " + city.toString()));
        return result;
    }
	
	@Transactional
    public void save(City input) {
        jdbcTemplate.update("INSERT INTO city(name, square, population) VALUES (?,?,?)", new Object[] {input.getName(), input.getSquare(), input.getPopulation()});
        log.info("insert: " + input.toString());
    }
	
	@Transactional(readOnly=true)
	public City findById(String id) {
		City result =  jdbcTemplate.queryForObject(
                "SELECT id, name, square, population FROM city WHERE id = ?", new Object[] {id},
                (rs, rowNum) -> new City(rs.getInt("id"), rs.getString("name"), rs.getDouble("square"), rs.getInt("population"))
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