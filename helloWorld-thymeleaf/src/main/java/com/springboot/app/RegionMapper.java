package com.springboot.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class RegionMapper implements RowMapper<Region> {
	public Region mapRow(ResultSet rs, int rowNum) throws SQLException {
		Region region = new Region();
			region.setId(rs.getInt("id"));
			region.setNom(rs.getString("nom"));
			region.setLatitude(rs.getDouble("latitude"));
			region.setLongitude(rs.getDouble("longitude"));
			return region;
	   }
}
