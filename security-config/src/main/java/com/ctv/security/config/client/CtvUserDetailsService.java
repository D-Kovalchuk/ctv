package com.ctv.security.config.client;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Dmitry Kovalchuk
 */
public class CtvUserDetailsService extends JdbcDaoSupport implements UserDetailsService {

    private String usersAndAuthoritiesByUsernameQuery;

    public CtvUserDetailsService(String usersAndAuthoritiesByUsernameQuery) {
        this.usersAndAuthoritiesByUsernameQuery = usersAndAuthoritiesByUsernameQuery;
    }

    @Override
    public CtvUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getJdbcTemplate().queryForObject(usersAndAuthoritiesByUsernameQuery, new String[]{username}, (rs, rowNum) -> {
            int id = rs.getInt("id");
            String login = rs.getString("username");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String type = rs.getString("type");
            String site = rs.getString("site");
            String authority = rs.getString("authority");
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
