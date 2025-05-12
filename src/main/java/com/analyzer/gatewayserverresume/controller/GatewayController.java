package com.analyzer.gatewayserverresume.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;

@Controller
public class GatewayController {

    @Value("${frontend.url}")
    private String frontendUrl;

    @GetMapping("/user")
    public Principal redirectToProfile(Principal user) {
        return user;
    }

    @GetMapping("/profile")
    public Mono<Void> profile(OAuth2AuthenticationToken token, ServerHttpResponse response) {
        String registrationId = token.getAuthorizedClientRegistrationId();

        String name = "";
        String email = "";
        String photo = "";

        if ("google".equals(registrationId)) {
            name = token.getPrincipal().getAttribute("name");
            email = token.getPrincipal().getAttribute("email");
            photo = token.getPrincipal().getAttribute("picture");
        } else if ("github".equals(registrationId)) {
            name = token.getPrincipal().getAttribute("name");
            email = token.getPrincipal().getAttribute("email");
            photo = token.getPrincipal().getAttribute("avatar_url");
        }

        // URL-encode each parameter to avoid invalid characters
        String redirectUrl = String.format(
                "%s/profile?name=%s&email=%s&photo=%s",
                frontendUrl,
                URLEncoder.encode(name != null ? name : "", StandardCharsets.UTF_8),
                URLEncoder.encode(email != null ? email : "", StandardCharsets.UTF_8),
                URLEncoder.encode(photo != null ? photo : "", StandardCharsets.UTF_8)
        );
        response.setStatusCode(HttpStatus.FOUND);
        response.getHeaders().setLocation(URI.create(redirectUrl));
        return response.setComplete();
    }


}
