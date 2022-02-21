package com.springboot.app;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ChartStatutSignalementMapper  implements RowMapper<ChartStatutSignalement> {
	public ChartStatutSignalement mapRow(ResultSet rs, int rowNum) throws SQLException {
		ChartStatutSignalement c = new ChartStatutSignalement();
			c.setIdStatussignalement(rs.getInt("idStatussignalement"));
			c.setPource(rs.getDouble("pource"));
			return c;
	   }
}
