package com.ctv.registration.controller;

import com.ctv.registration.dto.AuthenticationRequest;
import com.ctv.registration.dto.AuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("token")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private RedisOperationsSessionRepository sessionRepository;

    public AuthenticationController(AuthenticationManager authenticationManager, RedisOperationsSessionRepository sessionRepository) {
        this.authenticationManager = authenticationManager;
        this.sessionRepository = sessionRepository;
    }

    @ResponseStatus(CREATED)
    @RequestMapping(method = POST)
    public AuthenticationToken authenticate(
            @RequestBody AuthenticationRequest authenticationRequest,
            HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token = createUserPasswordToken(authenticationRequest);
        Authentication authentication = this.authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return new AuthenticationToken(userDetails.getUsername(), userDetails.getAuthorities(), session.getId());
    }

    @ResponseStatus(OK)
    @RequestMapping(method = DELETE, headers = "x-auth-token")
    public void logout(@RequestHeader("x-auth-token") String token) {
        sessionRepository.delete(token);
    }

    private UsernamePasswordAuthenticationToken createUserPasswordToken(AuthenticationRequest authenticationRequest) {
        return new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword());
    }

}
