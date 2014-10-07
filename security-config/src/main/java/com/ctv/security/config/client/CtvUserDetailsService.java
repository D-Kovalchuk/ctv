package com.ctv.security.config.client;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import java.util.List;

/**
 * @author Dmitry Kovalchuk
 */
public class CtvUserDetailsService extends JdbcDaoImpl {

    @Override
    protected List<UserDetails> loadUsersByUsername(String username) {
        return getJdbcTemplate().query(getUsersByUsernameQuery(), new String[]{username}, (rs, rowNum) -> {
            String login = rs.getString(1);
            String password = rs.getString(2);
            boolean enabled = rs.getBoolean(3);
            //TODO load another prop
            return new User(login, password, enabled, true, true, true, AuthorityUtils.NO_AUTHORITIES);
        });
    }
}
