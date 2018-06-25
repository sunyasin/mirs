package org.saiku.database.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OlapUserDAO extends JdbcDaoSupport {

    public String getExternalUserNameByIdAndFio(Integer userIdExternal, String fio) {
        String username = getJdbcTemplate().query("SELECT * FROM users WHERE user_id_external = ?", new ResultSetExtractor<String>() {
            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    return rs.getString("user_login");
                }
                return null;
            }
        }, userIdExternal);
        if (username == null) {
            username = "user_foms_" + userIdExternal;
            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource());
            MapSqlParameterSource source = new MapSqlParameterSource()
                    .addValue("user_login", username)
                    .addValue("user_name", fio)
                    .addValue("user_id_external", userIdExternal);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update("INSERT INTO USERS (user_login, user_password, user_name, is_visible, user_id_external) \n" +
                                     "VALUES (:user_login, '$2a$10$mI7WPktKunswgnyTslnXEOVM2nEEEV2yRZYe7g1ifEClOV.aQdB7O', :user_name, 1, :user_id_external);", source, keyHolder);
            Integer userId = keyHolder.getKey().intValue();
            getJdbcTemplate().update("INSERT INTO USERS_ROLE (user_id, role_id) \n" +
                    "VALUES (?, 5);", userId);
            getJdbcTemplate().update("INSERT INTO USERS_ROLE (user_id, role_id) \n" +
                    "VALUES (?, 6);", userId);
        }
        return username;
    }

    public Integer getUserIdByUsername(String username) {
        return getJdbcTemplate().query("SELECT * FROM users WHERE user_login = ?", new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    return rs.getInt("user_id");
                }
                return null;
            }
        }, username);
    }

    public String getFioByUserId(Integer userId) {
        return getJdbcTemplate().query("SELECT * FROM users WHERE user_id = ?", new ResultSetExtractor<String>() {
            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    return rs.getString("user_name");
                }
                return null;
            }
        }, userId);
    }

    public Boolean checkFoms(Integer userId) {
        return getJdbcTemplate().query("SELECT role_id FROM users_role WHERE user_id = ?", new ResultSetExtractor<Boolean>() {
            @Override
            public Boolean extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    if (rs.getInt("role_id") == 6) {
                        return true;
                    }
                }
                return false;
            }
        }, userId);
    }

    public List<Integer> getUserIdsByRoleId(Integer roleId) {
        return getJdbcTemplate().queryForList("SELECT user_id FROM users_role WHERE role_id = ?", Integer.class, roleId);
    }
}
