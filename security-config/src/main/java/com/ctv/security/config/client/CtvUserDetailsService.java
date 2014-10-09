package com.ctv.security.config.client;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import java.util.List;

/**
 * @author Dmitry Kovalchuk
 */
public class CtvUserDetailsService extends JdbcDaoSupport implements UserDetailsService {

    @Override
    public CtvUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String authority = getJdbcTemplate().queryForObject("select username,authority from authorities where username = ?", new String[]{username}, (rs, rowNum) -> {
            return rs.getString("authority");
        });
        return getJdbcTemplate().queryForObject("select * from users where username = ?", new String[]{username}, (rs, rowNum) -> {
            int id = rs.getInt("id");
            String login = rs.getString("username");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String type = rs.getString("type");
            String site = rs.getString("site");
            return CtvUserDetailsBuilder.get()
                    .withUsername(login)
                    .withPassword(password)
                    .withAuthorities(AuthorityUtils.createAuthorityList(authority))
                    .withId(id)
                    .withEmail(email)
                    .withType(type)
                    .withSite(site)
                    .build();
        });
    }
}
