package org.data.redroleplay.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.util.List;

public class CustomLogoutHandler implements LogoutHandler {

    private final SessionRegistry sessionRegistry;

    public CustomLogoutHandler(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            List<SessionInformation> sessions = sessionRegistry.getAllSessions(authentication.getPrincipal(), false);
            for (SessionInformation session : sessions) {
                session.expireNow();
            }
        }
    }
}