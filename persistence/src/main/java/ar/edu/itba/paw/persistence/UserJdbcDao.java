package ar.edu.itba.paw.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.model.User;

@Repository
public class UserJdbcDao implements UserDao{
	
	private JdbcTemplate jdbcTemplate;
	
	private final static RowMapper<User> ROW_MAPPER = new RowMapper<User>(){

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new User(rs.getInt("userid"),rs.getString("username"),rs.getString("password"));
		}
		
	};
	
	@Autowired
	public UserJdbcDao (final DataSource ds){
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
							.withTableName("users")
							.usingGeneratedKeyColumns("userid");
	}

	@Override
	public User findById(long id) {
		
		final List<User> list = jdbcTemplate.query("SELECT * FROM users WHERE userid = ?",
				ROW_MAPPER, id);
				if (list.isEmpty()) {
				return null;
				}
				return list.get(0);
	}
	
	private final SimpleJdbcInsert jdbcInsert;

	@Override
	public User findByName(String name) {

		final List<User> list = jdbcTemplate.query("SELECT * FROM users WHERE username = ?",
				ROW_MAPPER, name);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public User create(String username, String password) {
		final Map<String, Object> args = new HashMap<String,Object>();
		args.put("username", username); // la key es el nombre de la columna
		args.put("password", password);
		final Number userId = jdbcInsert.executeAndReturnKey(args);
		return new User(userId.longValue(), username,password);
	}
	

}
