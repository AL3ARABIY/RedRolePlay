package org.data.redroleplay.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.data.redroleplay.entities.website.User;
import org.data.redroleplay.errorHandling.costums.UserNeedAuthentication;
import org.data.redroleplay.services.AuthenticationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IpCheckFilter extends OncePerRequestFilter {

    private final AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Optional<User> userOptional = authenticationService.getAuthenticatedUser();

        if(userOptional.isPresent()) {
            String currentIp = request.getRemoteAddr();
            String lastLoginIp = userOptional.get().getLastLoginIp();

            if (lastLoginIp != null && !lastLoginIp.equals(currentIp)) {
//                request.getSession().invalidate();
//                SecurityContextHolder.clearContext();
                response.sendRedirect("/logout?error=IP changed, please login again.");
                return;
            }
        };



        filterChain.doFilter(request, response);
    }
}
