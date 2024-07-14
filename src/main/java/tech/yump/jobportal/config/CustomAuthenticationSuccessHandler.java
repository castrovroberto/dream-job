package tech.yump.jobportal.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import tech.yump.jobportal.enums.UserTypeEnum;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        logger.debug("User {} logged in", username);
        boolean hasJobSeekerRole = authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals(UserTypeEnum.JOB_SEEKER.name()));
        boolean hasRecruiterRole = authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals(UserTypeEnum.RECRUITER.name()));

        if (hasRecruiterRole || hasJobSeekerRole) {
            response.sendRedirect("/dashboard");
        } else {
            response.sendRedirect("/login");
        }
    }
}