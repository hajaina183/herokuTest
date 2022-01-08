package com.springboot.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class SignalementMapper implements RowMapper<Signalement> {
    public Signalement mapRow(ResultSet rs, int rowNum) throws SQLException {
        Signalement signalement = new Signalement();
        signalement.setId(rs.getInt("id"));
        signalement.setIdType(rs.getInt("idType"));
        signalement.setIdRegion(rs.getInt("idRegion"));
        signalement.setImage(rs.getString("image"));
        signalement.setTitre(rs.getString("titre"));
        signalement.setImage(rs.getString("image"));
        signalement.setLatitude(rs.getDouble("latitude"));
        signalement.setLongitude(rs.getDouble("longitude"));
        return signalement;
    }
}

