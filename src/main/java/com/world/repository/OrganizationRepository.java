package com.world.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

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
	
    public Collection<Organization> findAll() {
		
		final Map<Integer, Organization> map = new HashMap<>();
		
		String sql = "SELECT org.id, org.name, org.home_number, org.scope, org.website, org.date_update, org.key_words, phone.id as phoneId, phone.number as phoneNumber, city.id as cityId, city.name as cityName, city.square as citySquare, city.population as cityPopulation, street.id as streetId, street.name as streetName, street.extent as streetExtent "
				+ "FROM organization org left join phones phone on org.id = phone.organization_id inner join city ON org.city_id = city.id inner join street ON org.street_id = street.id";
		
		jdbcTemplate.query(sql, new RowCallbackHandler() {

	        @Override
	        public void processRow(ResultSet rs) throws SQLException {
	        	
	        	Organization org = new Organization(rs.getInt("id"), rs.getString("name"), 
						new City(rs.getInt("cityId"), rs.getString("cityName"), rs.getBigDecimal("citySquare"), rs.getInt("cityPopulation")),
						new Street(rs.getInt("streetId"), rs.getString("streetName"), rs.getInt("streetExtent")),
						rs.getString("home_number"), rs.getString("scope"), rs.getString("website"), rs.getDate("date_update"), rs.getString("key_words") );
	        	
	        	Phone phone = new Phone(rs.getInt("phoneId"), rs.getString("phoneNumber"));

	            map.putIfAbsent(org.getId(), org);
	            map.get(org.getId()).addPhone(phone);
	        }
	    });
		
		map.forEach((k,v)->log.info("select: " + v));

		return map.values();
    }
	
    public int save(Organization input) {
		String sql = "INSERT INTO organization(name, city_id, street_id, home_number, scope, website, date_update, key_words) VALUES (?,?,?,?,?,?,now(),?)"; 
		KeyHolder holder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(new PreparedStatementCreator() {           
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, input.getName());
                ps.setInt(2, input.getCity().getId());
                ps.setInt(3, input.getStreet().getId());
                ps.setString(4, input.getHomeNumber());
                ps.setString(5, input.getScope());
                ps.setString(6, input.getWebsite());
                ps.setString(7, input.getKeyWords());
                return ps;
            }
        }, holder);
		
        Map<String, Object> map = holder.getKeys(); 
        int id = (int) map.get("id");
        log.info("insert: " + id + " " + input.toString());
        
        phoneRepository.saveAll(input.getPhone(), id);
        return id;
    }
	
	public void saveAll(List<Organization> list) {
		list.forEach(item -> this.save(item));
	}
	
	public Organization findById(int id) {
		
		final Map<Integer, Organization> map = new HashMap<>();
		
		String sql = "SELECT org.id, org.name, org.home_number, org.scope, org.website, org.date_update, org.key_words, phone.id as phoneId, phone.number as phoneNumber, city.id as cityId, city.name as cityName, city.square as citySquare, city.population as cityPopulation, street.id as streetId, street.name as streetName, street.extent as streetExtent "
				+ "FROM organization org left join phones phone on org.id = phone.organization_id inner join city ON org.city_id = city.id inner join street ON org.street_id = street.id WHERE org.id = ?";
		
		jdbcTemplate.query(sql, new Object[]{id}, new RowCallbackHandler() {

	        @Override
	        public void processRow(ResultSet rs) throws SQLException {
	        	
	        	Organization org = new Organization(rs.getInt("id"), rs.getString("name"), 
						new City(rs.getInt("cityId"), rs.getString("cityName"), rs.getBigDecimal("citySquare"), rs.getInt("cityPopulation")),
						new Street(rs.getInt("streetId"), rs.getString("streetName"), rs.getInt("streetExtent")),
						rs.getString("home_number"), rs.getString("scope"), rs.getString("website"), rs.getDate("date_update"), rs.getString("key_words") );
	        	
	        	Phone phone = new Phone(rs.getInt("phoneId"), rs.getString("phoneNumber"));

	            map.putIfAbsent(org.getId(), org);
	            map.get(org.getId()).addPhone(phone);
	        }
	    });
		
		map.forEach((k,v)->log.info("select: " + v));
		List<Organization> list = new ArrayList<Organization>(map.values());
		
		return list.get(0); 
	}
	
	public Collection<Organization> findFromDateUpdate(Date from) {
		
		final Map<Integer, Organization> map = new HashMap<>();
		
		String sql = "SELECT org.id, org.name, org.home_number, org.scope, org.website, org.date_update, org.key_words, phone.id as phoneId, phone.number as phoneNumber, city.id as cityId, city.name as cityName, city.square as citySquare, city.population as cityPopulation, street.id as streetId, street.name as streetName, street.extent as streetExtent "
				+ "FROM organization org left join phones phone on org.id = phone.organization_id inner join city ON org.city_id = city.id inner join street ON org.street_id = street.id WHERE org.date_update > ?";
		
		jdbcTemplate.query(sql, new Object[]{from}, new RowCallbackHandler() {

	        @Override
	        public void processRow(ResultSet rs) throws SQLException {
	        	
	        	Organization org = new Organization(rs.getInt("id"), rs.getString("name"), 
						new City(rs.getInt("cityId"), rs.getString("cityName"), rs.getBigDecimal("citySquare"), rs.getInt("cityPopulation")),
						new Street(rs.getInt("streetId"), rs.getString("streetName"), rs.getInt("streetExtent")),
						rs.getString("home_number"), rs.getString("scope"), rs.getString("website"), rs.getDate("date_update"), rs.getString("key_words") );
	        	
	            Phone phone = new Phone(rs.getInt("phoneId"), rs.getString("phoneNumber"));

	            map.putIfAbsent(org.getId(), org);
	            map.get(org.getId()).addPhone(phone);
	        }
	    });
		
		map.forEach((k,v)->log.info("select: " + v));
		return map.values(); 
	}
	
	public Collection<Organization> findByName(String name) {
		
		final Map<Integer, Organization> map = new HashMap<>();
		
		String sql = "SELECT org.id, org.name, org.home_number, org.scope, org.website, org.date_update, org.key_words, phone.id as phoneId, phone.number as phoneNumber, city.id as cityId, city.name as cityName, city.square as citySquare, city.population as cityPopulation, street.id as streetId, street.name as streetName, street.extent as streetExtent "
				+ "FROM organization as org left join phones as phone on org.id = phone.organization_id left join city ON org.city_id = city.id left join street ON org.street_id = street.id WHERE org.name = ?";
		
		jdbcTemplate.query(sql, new Object[]{name}, new RowCallbackHandler() {

	        @Override
	        public void processRow(ResultSet rs) throws SQLException {
	        	
	        	Organization org = new Organization(rs.getInt("id"), rs.getString("name"), 
						new City(rs.getInt("cityId"), rs.getString("cityName"), rs.getBigDecimal("citySquare"), rs.getInt("cityPopulation")),
						new Street(rs.getInt("streetId"), rs.getString("streetName"), rs.getInt("streetExtent")),
						rs.getString("home_number"), rs.getString("scope"), rs.getString("website"), rs.getDate("date_update"), rs.getString("key_words") );
	        	
				Phone phone = new Phone(rs.getInt("phoneId"), rs.getString("phoneNumber"));

	            map.putIfAbsent(org.getId(), org);
	            map.get(org.getId()).addPhone(phone);
	        }
	    });
		
		map.forEach((k,v)->log.info("select: " + v));
		return map.values();
	}
	
    public void updateFts() {
		jdbcTemplate.update("UPDATE organization SET orgfts=setweight( coalesce( to_tsvector('ru', name),''),'A') || ' ' || setweight( coalesce( to_tsvector('ru', scope),''),'B') || ' ' || setweight( coalesce( to_tsvector('ru', key_words),''),'B');");
        log.info("update organization full text search index");
    }
	
	public void deleteById(int id) {
		jdbcTemplate.update("DELETE from organization WHERE id = ?", new Object[] {id});
		log.info("deleteById: " + id);
	}
	
	public Collection<Organization> search(String param) {
		String sql = "SELECT org.id, org.name, org.home_number, org.scope, org.website, org.date_update, org.key_words, phone.id as phoneId, phone.number as phoneNumber, city.id as cityId, city.name as cityName, city.square as citySquare, city.population as cityPopulation, street.id as streetId, street.name as streetName, street.extent as streetExtent, ts_rank_cd(( coalesce(org.orgfts, '') ||' '|| coalesce(city.cityfts, '') ||' '|| coalesce(street.streetfts, '')), query) AS rank FROM organization as org left join phones as phone on org.id = phone.organization_id left join city on org.city_id = city.id left join street on org.street_id = street.id, to_tsquery(?) query WHERE ( coalesce(org.orgfts, '') ||' '|| coalesce(city.cityfts, '') ||' '|| coalesce(street.streetfts, '')) @@ query ORDER BY rank DESC";
		
		Map<Integer, Organization> map = new HashMap<>();
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				Organization org = new Organization(rs.getInt("id"), rs.getString("name"), 
						new City(rs.getInt("cityId"), rs.getString("cityName"), rs.getBigDecimal("citySquare"), rs.getInt("cityPopulation")),
						new Street(rs.getInt("streetId"), rs.getString("streetName"), rs.getInt("streetExtent")),
						rs.getString("home_number"), rs.getString("scope"), rs.getString("website"), rs.getDate("date_update"), rs.getString("key_words") );

				Phone phone = new Phone(rs.getInt("phoneId"), rs.getString("phoneNumber"));

				map.putIfAbsent(org.getId(), org);
				map.get(org.getId()).addPhone(phone);
			}
		}, param);

		map.forEach((k,v)->log.info("select: " + v));
		return map.values();
	}
	
	public List<Map<String,Object>> analyze(String scope) {
		String sql = "select city.name as cityName, org.scope, count(org.scope) as quantity, city.population as cityPopulation "
		+"FROM organization as org left join city on org.city_id = city.id left join street on org.street_id = street.id "
		+"WHERE org.scope = ? "
		+"GROUP BY org.scope, city.name, city.population";
		
		List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("cityName",rs.getString("cityName"));
				map.put("scope",rs.getString("scope"));
				map.put("quantity",rs.getInt("quantity"));
				map.put("cityPopulation",rs.getInt("cityPopulation"));
				result.add(map);
			}
		}, scope);
		
		return result;
	} 
}