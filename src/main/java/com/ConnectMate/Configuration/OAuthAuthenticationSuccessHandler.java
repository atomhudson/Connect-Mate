package com.ConnectMate.Configuration;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import com.ConnectMate.Helpers.Message;
import com.ConnectMate.Helpers.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.util.List;
import com.ConnectMate.Entities.Providers;
import com.ConnectMate.Entities.User;
import com.ConnectMate.Helpers.AppConstants;
import com.ConnectMate.Repositories.UserRepo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Autowired
    private UserRepo userRepo;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        logger.info("OAuthAuthenticationSuccessHandler");

        // Ensure that we have an OAuth2AuthenticationToken
        if (!(authentication instanceof OAuth2AuthenticationToken)) {
            logger.error("Authentication is not OAuth2AuthenticationToken");
            return;
        }

        // Identify the provider
        OAuth2AuthenticationToken oauth2AuthenicationToken = (OAuth2AuthenticationToken) authentication;
        String authorizedClientRegistrationId = oauth2AuthenicationToken.getAuthorizedClientRegistrationId();
        logger.info("Provider: {}", authorizedClientRegistrationId);

        DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
        oauthUser.getAttributes().forEach((key, value) -> logger.info("{} : {}", key, value));

        // Create a new user object
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstants.ROLE_USER)); // Default role
        user.setEmailVerified(true);
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode("dummy"));

        // Populate user fields based on provider
        if ("google".equalsIgnoreCase(authorizedClientRegistrationId)) {
            user.setEmail(oauthUser.getAttribute("email"));
            user.setProfilePic(oauthUser.getAttribute("picture"));
            user.setName(oauthUser.getAttribute("name"));
            user.setProviderUserId(oauthUser.getName());
            user.setProvider(Providers.GOOGLE);
            user.setAbout("This account is created using Google.");
            user.setDate(new Date());
        } else if ("github".equalsIgnoreCase(authorizedClientRegistrationId)) {
            String email = oauthUser.getAttribute("email") != null
                    ? oauthUser.getAttribute("email")
                    : oauthUser.getAttribute("login") + "@github.com";
            user.setEmail(email);
            user.setProfilePic(oauthUser.getAttribute("avatar_url"));
            user.setName(oauthUser.getAttribute("login"));
            user.setProviderUserId(oauthUser.getName());
            user.setProvider(Providers.GITHUB);
            user.setAbout("This account is created using GitHub.");
            user.setDate(new Date());
            user.setLastUpdated(new Date());
        } else {
            logger.info("Unknown provider");
        }

        // Save or update user in database
        User existingUser = userRepo.findByEmail(user.getEmail()).orElse(null);
        if (existingUser == null) {
            userRepo.save(user);
            logger.info("New user saved: {}", user.getEmail());
        } else {
            user = existingUser;
            if (!user.isEnabled()) {
                request.getSession().setAttribute("message", Message.builder()
                        .type(MessageType.red)
                        .content("User is disabled, Email with verification link is sent on your email id or Contact Us!!")
                        .build());
                System.out.println("email not verified");
                logger.info("Email not verified");
                new DefaultRedirectStrategy().sendRedirect(request, response, "/login?error=true");

            } else {
                System.out.println("user logged in");
                logger.info("user logged in");
                new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
            }
            logger.info("Existing user loaded: {}", user.getEmail());
        }
    }
}
