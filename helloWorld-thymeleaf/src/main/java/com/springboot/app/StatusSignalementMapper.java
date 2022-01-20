package com.springboot.app;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatusSignalementMapper implements RowMapper<StatusSignalement> {
    public StatusSignalement mapRow(ResultSet rs, int rowNum) throws SQLException {
        StatusSignalement statusSignalement = new StatusSignalement();
        statusSignalement.setId(rs.getInt("id"));
        statusSignalement.setIntitule(rs.getString("intitule"));
        return statusSignalement;
    }
}

