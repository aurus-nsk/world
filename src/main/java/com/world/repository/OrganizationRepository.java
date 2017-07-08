package com.world.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
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
	@Autowired
	private PhoneRepository phoneRepository;
	
	private static final Logger log = LoggerFactory.getLogger(OrganizationRepository.class);
	
	@Transactional(readOnly=true)
    public Collection<Organization> findAll() {
		
		final Map<Integer, Organization> map = new HashMap<>();
		
		String sql = "SELECT org.id, org.name, org.home_number, org.scope, org.website, org.date_update, phone.id, phone.number, city.id, city.name, city.square, city.population, street.id, street.name, street.extent "
				+ "FROM organization org left join phones phone on org.id = phone.organization_id inner join city ON org.city_id = city.id inner join street ON org.street_id = street.id";
		
		jdbcTemplate.query(sql, new RowCallbackHandler() {

	        @Override
	        public void processRow(ResultSet rs) throws SQLException {
	        	
	        	Organization org = new Organization(rs.getInt("org.id"), rs.getString("org.name"), 
    					new City(rs.getInt("city.id"), rs.getString("city.name"), rs.getBigDecimal("city.square"), rs.getInt("city.population")),
    					new Street(rs.getInt("street.id"), rs.getString("street.name"), rs.getInt("street.extent")),
    					rs.getString("org.home_number"), rs.getString("org.scope"), rs.getString("org.website"), rs.getDate("org.date_update"));
	        	
	            Phone phone = new Phone(rs.getInt("phone.id"), rs.getString("phone.number"));

	            map.putIfAbsent(org.getId(), org);
	            map.get(org.getId()).addPhone(phone);
	        }
	    });
		
		map.forEach((k,v)->log.info("select: " + v));

		return map.values();
    }
	
	@Transactional
    public void save(Organization input) {
        jdbcTemplate.update("INSERT INTO organization(name, city_id, street_id, home_number, scope, website, date_update) VALUES (?,?,?,?,?,?,now())", 
        		new Object[] {input.getName(), input.getCity().getId(), input.getStreet().getId(), input.getHomeNumber(), input.getScope(), input.getWebsite(), input.getScope()});
        log.info("insert: " + input.toString());
        
        phoneRepository.saveAll(input.getPhone(), input.getId());
    }
	
	@Transactional
	public void saveAll(List<Organization> list) {
		String sql = "INSERT INTO organization(name, city_id, street_id, home_number, scope, website, date_update) VALUES (?,?,?,?,?,?,now())";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Organization item = list.get(i);
				ps.setString(1, item.getName());
				ps.setInt(2, item.getCity().getId());
				ps.setInt(3, item.getStreet().getId());
				ps.setString(4, item.getHomeNumber());
				ps.setString(5, item.getScope());
				ps.setString(6, item.getWebsite());
			}

			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
		log.info("insert: " + Arrays.toString(list.toArray()));
		
		list.forEach(item -> phoneRepository.saveAll(item.getPhone(), item.getId()));
	}
	
	@Transactional(readOnly=true)
	public Organization findById(String id) {
		
		final Map<Integer, Organization> map = new HashMap<>();
		
		String sql = "SELECT org.id, org.name, org.home_number, org.scope, org.website, org.date_update, phone.id, phone.number, city.id, city.name, city.square, city.population, street.id, street.name, street.extent "
				+ "FROM organization org left join phones phone on org.id = phone.organization_id inner join city ON org.city_id = city.id inner join street ON org.street_id = street.id WHERE org.id = ?";
		
		jdbcTemplate.query(sql, new Object[]{id}, new RowCallbackHandler() {

	        @Override
	        public void processRow(ResultSet rs) throws SQLException {
	        	
	        	Organization org = new Organization(rs.getInt("org.id"), rs.getString("org.name"), 
    					new City(rs.getInt("city.id"), rs.getString("city.name"), rs.getBigDecimal("city.square"), rs.getInt("city.population")),
    					new Street(rs.getInt("street.id"), rs.getString("street.name"), rs.getInt("street.extent")),
    					rs.getString("org.home_number"), rs.getString("org.scope"), rs.getString("org.website"), rs.getDate("org.date_update"));
	        	
	            Phone phone = new Phone(rs.getInt("phone.id"), rs.getString("phone.number"));

	            map.putIfAbsent(org.getId(), org);
	            map.get(org.getId()).addPhone(phone);
	        }
	    });
		
		map.forEach((k,v)->log.info("select: " + v));
		List<Organization> list = new ArrayList<Organization>(map.values());
		
		return list.get(0); 
	}
	
	@Transactional(readOnly=true)
	public Collection<Organization> findFromDateUpdate(Date from) {
		
		final Map<Integer, Organization> map = new HashMap<>();
		
		String sql = "SELECT org.id, org.name, org.home_number, org.scope, org.website, org.date_update, phone.id, phone.number, city.id, city.name, city.square, city.population, street.id, street.name, street.extent "
				+ "FROM organization org left join phones phone on org.id = phone.organization_id inner join city ON org.city_id = city.id inner join street ON org.street_id = street.id WHERE org.date_update > ?";
		
		jdbcTemplate.query(sql, new Object[]{from}, new RowCallbackHandler() {

	        @Override
	        public void processRow(ResultSet rs) throws SQLException {
	        	
	        	Organization org = new Organization(rs.getInt("org.id"), rs.getString("org.name"), 
    					new City(rs.getInt("city.id"), rs.getString("city.name"), rs.getBigDecimal("city.square"), rs.getInt("city.population")),
    					new Street(rs.getInt("street.id"), rs.getString("street.name"), rs.getInt("street.extent")),
    					rs.getString("org.home_number"), rs.getString("org.scope"), rs.getString("org.website"), rs.getDate("org.date_update"));
	        	
	            Phone phone = new Phone(rs.getInt("phone.id"), rs.getString("phone.number"));

	            map.putIfAbsent(org.getId(), org);
	            map.get(org.getId()).addPhone(phone);
	        }
	    });
		
		map.forEach((k,v)->log.info("select: " + v));
		return map.values(); 
	}
	
	@Transactional(readOnly=true)
	public Collection<Organization> findByName(String name) {
		
		final Map<Integer, Organization> map = new HashMap<>();
		
		String sql = "SELECT org.id, org.name, org.home_number, org.scope, org.website, org.date_update, phone.id, phone.number, city.id, city.name, city.square, city.population, street.id, street.name, street.extent "
				+ "FROM organization org left join phones phone on org.id = phone.organization_id inner join city ON org.city_id = city.id inner join street ON org.street_id = street.id WHERE org.name = ?";
		
		jdbcTemplate.query(sql, new Object[]{name}, new RowCallbackHandler() {

	        @Override
	        public void processRow(ResultSet rs) throws SQLException {
	        	
	        	Organization org = new Organization(rs.getInt("org.id"), rs.getString("org.name"), 
    					new City(rs.getInt("city.id"), rs.getString("city.name"), rs.getBigDecimal("city.square"), rs.getInt("city.population")),
    					new Street(rs.getInt("street.id"), rs.getString("street.name"), rs.getInt("street.extent")),
    					rs.getString("org.home_number"), rs.getString("org.scope"), rs.getString("org.website"), rs.getDate("org.date_update"));
	        	
	            Phone phone = new Phone(rs.getInt("phone.id"), rs.getString("phone.number"));

	            map.putIfAbsent(org.getId(), org);
	            map.get(org.getId()).addPhone(phone);
	        }
	    });
		
		map.forEach((k,v)->log.info("select: " + v));
		return map.values();
	}
	
	@Transactional
	public void deleteById(String id) {
		jdbcTemplate.update("DELETE from organization WHERE id = ?", new Object[] {id});
		log.info("deleteById: " + id);
	}
}