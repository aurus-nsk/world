package com.world.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.world.domain.City;
import com.world.domain.Organization;
import com.world.domain.Phone;
import com.world.domain.Street;

@Repository
public class OrganizationRepository {

	@Autowired
    JdbcTemplate jdbcTemplate;
	private static final Logger log = LoggerFactory.getLogger(OrganizationRepository.class);
	
	@Transactional(readOnly=true)
    public List<Organization> findAll() {
		
		/*
        List<Organization> result = jdbcTemplate.query(
                "SELECT id, name, city.id, city.name, city.square, city.population, street.id, street.name, street.extent, homeNumber, scope, date_update FROM organization, city, street "
                + "WHERE city_id = city.id AND street_id = street.id",
                (rs, rowNum) -> new Organization(rs.getInt("id"), rs.getString("name"), 
                					new City(rs.getInt("city.id"), rs.getString("city.name"), rs.getDouble("city.square"), rs.getInt("city.population")),
                					new Street(rs.getInt("street.id"), rs.getString("street.name"), rs.getInt("street.extent")),
                					rs.getString("homeNumber"), rs.getString("scope"), rs.getDate("date_update"))
        );
        */
		final Map<Integer, Organization> map = new HashMap<>();
		
		String sql = 
			     " select phone.id, phone.number, org.id, org.name, org.homeNumber, org.scope, org.date_update" +
			     " from phone inner join organization org on phone.organization_id = org.id"; // + street + city
		
		jdbcTemplate.query(sql, new RowCallbackHandler() {

	        @Override
	        public void processRow(ResultSet rs) throws SQLException {
	        	
	        	Organization org = new Organization(rs.getInt("org.id"), rs.getString("org.name"), 
    					new City(rs.getInt("city.id"), rs.getString("city.name"), rs.getDouble("city.square"), rs.getInt("city.population")),
    					new Street(rs.getInt("street.id"), rs.getString("street.name"), rs.getInt("street.extent")),
    					rs.getString("org.homeNumber"), rs.getString("org.scope"), rs.getString("org.website"), rs.getDate("org.date_update"));
	        	
	            Phone phone = new Phone(rs.getInt("phone.id"), rs.getString("phone.number"));

	            map.putIfAbsent(org.getId(), org);
	            map.get(org.getId()).addPhone(phone);
	        }
	    });
		
		map.forEach((k,v)->log.info("select: organization : " + v));

		return new ArrayList<Organization>(map.values());
    }
	
	@Transactional
    public void save(Organization input) {
		//validate NOt NULL getcity get id
        jdbcTemplate.update("INSERT INTO organization(name, city_id, street_id, homeNumber, scope, date_update) VALUES (?,?,?,?,?,now())", 
        		new Object[] {input.getName(), input.getCity().getId(), input.getStreet().getId(), input.getHomeNumber(), input.getScope(), input.getScope()});
        log.info("insert: " + input.toString());
    }
	
	@Transactional(readOnly=true)
	public Organization findById(String id) {
		
		Organization result =  jdbcTemplate.queryForObject(
				"SELECT id, name, city.id, city.name, city.square, city.population, street.id, street.name, street.extent, homeNumber, scope, website, date_update FROM organization, city, street "
		                + "WHERE city_id = city.id AND street_id = street.id AND id = ?", new Object[] {id},
		                (rs, rowNum) -> new Organization(rs.getInt("id"), rs.getString("name"), 
            					new City(rs.getInt("city.id"), rs.getString("city.name"), rs.getDouble("city.square"), rs.getInt("city.population")),
            					new Street(rs.getInt("street.id"), rs.getString("street.name"), rs.getInt("street.extent")),
            					rs.getString("homeNumber"), rs.getString("scope"), rs.getString("website"), rs.getDate("date_update"))
        );
		log.info("findById: " + result);
		return result;
	}
	
	@Transactional
	public void deleteById(String id) {
		jdbcTemplate.update("DELETE from organization WHERE id = ?", new Object[] {id});
		log.info("deleteById: " + id);
	}
}