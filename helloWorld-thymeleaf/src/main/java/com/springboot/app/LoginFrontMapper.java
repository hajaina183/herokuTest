package com.springboot.app;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class LoginFrontMapper implements RowMapper<LoginFront>{
	public LoginFront mapRow(ResultSet rs, int rowNum) throws SQLException {
		LoginFront loginFront = new LoginFront();
			loginFront.setId(rs.getInt("id"));
			loginFront.setIdRegion(rs.getInt("idRegion"));
			loginFront.setNom(rs.getString("nom"));
			loginFront.setMdp(rs.getString("mdp"));
			return loginFront;
	   }
}
