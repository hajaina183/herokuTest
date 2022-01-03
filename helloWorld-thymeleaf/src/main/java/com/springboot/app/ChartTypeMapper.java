package com.springboot.app;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ChartTypeMapper implements RowMapper<ChartType> {
	public ChartType mapRow(ResultSet rs, int rowNum) throws SQLException {
		ChartType c = new ChartType();
			c.setIdType(rs.getInt("idType"));
			c.setPource(rs.getDouble("pource"));
			return c;
	   }
}
