package com.springboot.app;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class DetailSignalementMapper implements RowMapper<DetailSignalement> {
    public DetailSignalement mapRow(ResultSet rs, int rowNum) throws SQLException {
        DetailSignalement detailSignalement = new DetailSignalement(
        		new Signalement(
        				rs.getInt("id"),
        				rs.getInt("idType"),
        				rs.getInt("idRegion"),
        				rs.getString("titre"),
        				rs.getString("image"),
        				rs.getDouble("longitude"),
        				rs.getDouble("latitude")
        				),
        		new Type(
        				rs.getInt("idType"),
        				rs.getString("nomType")
        				),
        		new Region(
        				rs.getInt("idRegion"),
        				rs.getString("nomRegion"),
        				rs.getDouble("latitudeRegion"),
        				rs.getDouble("longitudeRegion")
        				),
        		rs.getString("description")
        		);
        return detailSignalement;
    }
}
