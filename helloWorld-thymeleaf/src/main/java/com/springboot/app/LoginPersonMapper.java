package com.springboot.app;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPersonMapper implements RowMapper<LoginPerson> {
    public LoginPerson mapRow(ResultSet rs, int rowNum) throws SQLException {
        LoginPerson log = new LoginPerson();
        log.setId(rs.getInt("id"));
        log.setEmail(rs.getString("email"));
        log.setMdp(rs.getString("mdp"));
        log.setNom(rs.getString("nom"));
        log.setAge(rs.getInt("age"));
        return log;
    }


}
