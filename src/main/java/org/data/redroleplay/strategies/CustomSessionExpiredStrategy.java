package org.data.redroleplay.strategies;

import jakarta.servlet.ServletException;
import lombok.AllArgsConstructor;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import java.io.IOException;

@AllArgsConstructor
public class CustomSessionExpiredStrategy implements SessionInformationExpiredStrategy {

    private String redirectUrl;

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        event.getResponse().sendRedirect(redirectUrl);
    }
}
