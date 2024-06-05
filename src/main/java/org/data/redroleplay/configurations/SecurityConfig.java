package org.data.redroleplay.configurations;

import lombok.RequiredArgsConstructor;
import org.data.redroleplay.enums.BaseAuthority;
import org.data.redroleplay.filters.IpCheckFilter;
import org.data.redroleplay.filters.LoginPageFilter;
import org.data.redroleplay.filters.RegistrationPageFilter;
import org.data.redroleplay.handlers.CustomAuthenticationSuccessHandler;
import org.data.redroleplay.handlers.CustomLogoutHandler;
import org.data.redroleplay.services.implementations.SecurityUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig  {

    private final SecurityUserDetailsService securityUserDetailsService;

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    private final IpCheckFilter ipCheckFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.addFilterBefore(new LoginPageFilter(), DefaultLoginPageGeneratingFilter.class);

        http.addFilterBefore(new RegistrationPageFilter(), UsernamePasswordAuthenticationFilter.class);

        http.addFilterAfter(ipCheckFilter, UsernamePasswordAuthenticationFilter.class);

        return http
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers(
                                        "/login",
                                        "/registration**",
                                        "/registration/discord",
                                        "/images/**",
                                        "/error**"
                                ).permitAll()
                                .requestMatchers(
                                        "/admin/whitelist/**",
                                        "/admin/user/details/**"
                                ).hasAuthority(BaseAuthority.WHITE_LISTER.name())
                                .anyRequest().hasAuthority(BaseAuthority.SIMPLE_ACCESS.name())
                )
                .sessionManagement(session -> session
//                        .invalidSessionUrl("/login?error=Invalid session.")
                        .sessionAuthenticationErrorUrl("/login?error=Session authentication error.")
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                        .expiredUrl("/login?error=Session expired.")
                        .sessionRegistry(sessionRegistry())
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .failureUrl("/login?error=Invalid username or password.")
                        .defaultSuccessUrl("/home", true)
                        .successHandler(customAuthenticationSuccessHandler)
                )
                .logout(logout -> logout
                        .deleteCookies("JSESSIONID")
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout&success=You have been logged out.")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .addLogoutHandler(new CustomLogoutHandler(sessionRegistry()))
                )
                .build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(securityUserDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

}
