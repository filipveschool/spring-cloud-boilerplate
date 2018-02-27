package com.filip.authenticationservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * REST endpoint to be used by other micro-services using SSO to validate the
 * authentication of the logged in user.
 * <p>
 * Since the "me" endpoint needs to be protected to be accessed only after the
 * OAuth2 authentication is successful; the OAuth2 server also becomes a
 * resource server.
 *
 * @author Filip Vanden Eynde
 */
@RestController
public class AuthUserController {


    /**
     * Return the principal identifying the logged in user
     *
     * @param user
     * @return
     */
    @GetMapping("/me")
    @ResponseBody
    public Principal getCurrentLoggedInUser(Principal user) {
        return user;
    }
}
