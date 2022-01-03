package com.springboot.app;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TypeMapper implements RowMapper<Type> {
	public Type mapRow(ResultSet rs, int rowNum) throws SQLException {
		Type type = new Type();
			type.setId(rs.getInt("id"));
			type.setIntitule(rs.getString("intitule"));
			return type;
	   }
}
