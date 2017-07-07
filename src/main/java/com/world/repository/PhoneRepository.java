package com.world.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.world.domain.Phone;

@Repository
public class PhoneRepository {

	@Autowired
    JdbcTemplate jdbcTemplate;
	private static final Logger log = LoggerFactory.getLogger(PhoneRepository.class);
	
	@Transactional(readOnly=true)
    public List<Phone> findAll() {
        List<Phone> result = jdbcTemplate.query(
                "SELECT id, number FROM phone",
                (rs, rowNum) -> new Phone(rs.getInt("id"), rs.getString("number"))
        );
        result.forEach(item -> log.info("select: " + item.toString()));
        return result;
    }
	
	@Transactional
    public void save(Phone input, int organizationId) {
        jdbcTemplate.update("INSERT INTO phone(number, organization_id) VALUES (?,?)", new Object[] {input.getNumber(), organizationId});
        log.info("insert: " + input.toString());
    }
	
	//findById
	
	@Transactional
	public void deleteById(String id) {
		jdbcTemplate.update("DELETE from phone WHERE id = ?", new Object[] {id});
		log.info("deleteById: " + id);
	}
}