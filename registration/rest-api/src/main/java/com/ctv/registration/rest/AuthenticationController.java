package com.ctv.registration.rest;

import com.ctv.registration.adapter.rest.dto.AuthenticationRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.ctv.registration.rest.Endpoint.X_AUTH_TOKEN;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(Endpoint.TOKEN_PATH)
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private RedisOperationsSessionRepository sessionRepository;

    public AuthenticationController(AuthenticationManager authenticationManager, RedisOperationsSessionRepository sessionRepository) {
        this.authenticationManager = authenticationManager;
        this.sessionRepository = sessionRepository;
    }

    @ResponseStatus(CREATED)
    @RequestMapping(method = POST)
    public void authenticate(@RequestBody @Validated AuthenticationRequest authenticationRequest, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token = createUserPasswordToken(authenticationRequest);
        Authentication authentication = this.authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession session = request.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
    }

    @ResponseStatus(OK)
    @RequestMapping(method = DELETE, headers = X_AUTH_TOKEN)
    public void logout(@RequestHeader(X_AUTH_TOKEN) String token) {
        sessionRepository.delete(token);
    }

    private UsernamePasswordAuthenticationToken createUserPasswordToken(AuthenticationRequest authenticationRequest) {
        return new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword());
    }

}
