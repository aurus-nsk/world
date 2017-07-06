package com.world.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;


import com.world.domain.City;

@Repository
public class CityRepository {

	@Autowired
    JdbcTemplate jdbcTemplate;
	
	@Transactional(readOnly=true)
    public List<City> findAll() {
        List<City> result = jdbcTemplate.query(
                "SELECT id, name, square, population FROM city",
                (rs, rowNum) -> new City(rs.getInt("id"), rs.getString("name"), rs.getDouble("square"), rs.getInt("population"))
        );

        return result;
    }
}