package com.springboot.app;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ChartRegionMapper implements RowMapper<ChartRegion> {
	public ChartRegion mapRow(ResultSet rs, int rowNum) throws SQLException {
		ChartRegion c = new ChartRegion();
			c.setIdRegion(rs.getInt("idRegion"));
			c.setPource(rs.getDouble("pource"));
			return c;
	   }
}
