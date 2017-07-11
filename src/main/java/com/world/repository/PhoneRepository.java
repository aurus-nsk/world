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

import com.world.domain.Phone;

@Repository
public class PhoneRepository {

	@Autowired
    JdbcTemplate jdbcTemplate;
	private static final Logger log = LoggerFactory.getLogger(PhoneRepository.class);
	
    public List<Phone> findAll() {
        List<Phone> result = jdbcTemplate.query(
                "SELECT id, number FROM phone",
                (rs, rowNum) -> new Phone(rs.getInt("id"), rs.getString("number"))
        );
        result.forEach(item -> log.info("select: " + item.toString()));
        return result;
    }
	
    public void save(Phone input, final int id) {
        jdbcTemplate.update("INSERT INTO phone(number, organization_id) VALUES (?,?)", new Object[] {input.getNumber(), id});
        log.info("insert: " + input.toString());
    }
	
    public void saveAll(List<Phone> list, final int id) {
		if(list == null || list.isEmpty()) return;
		
		String sql = "INSERT INTO phones(number, organization_id) VALUES (?,?)";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Phone item = list.get(i);
				ps.setString(1, item.getNumber());
				ps.setInt(2, id);
			}

			@Override
			public int getBatchSize() {
				return list.size();
			}
		});

		log.info("insert: " + Arrays.toString(list.toArray()));
    }
	
	public void deleteById(String id) {
		jdbcTemplate.update("DELETE from phone WHERE id = ?", new Object[] {id});
		log.info("deleteById: " + id);
	}
}