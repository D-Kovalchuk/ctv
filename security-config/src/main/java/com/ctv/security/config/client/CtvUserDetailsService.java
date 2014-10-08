package com.ctv.security.config.client;

import org.springframework.security.core.authority.AuthorityUtils;
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
            int id = rs.getInt("id");
            String login = rs.getString("username");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String type = rs.getString("type");
            String site = rs.getString("site");
            return CtvUserDetailsBuilder.get()
                    .withUsername(login)
                    .withPassword(password)
                    .withAuthorities(AuthorityUtils.NO_AUTHORITIES)
                    .withId(id)
                    .withEmail(email)
                    .withType(type)
                    .withSite(site)
                    .build();
        });
    }
}
