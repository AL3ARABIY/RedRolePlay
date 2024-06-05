package org.data.redroleplay.configurations;

import lombok.RequiredArgsConstructor;
import org.data.redroleplay.enums.BaseAuthority;
import org.data.redroleplay.filters.LoginPageFilter;
import org.data.redroleplay.filters.RegistrationPageFilter;
import org.data.redroleplay.handlers.CustomAuthenticationSuccessHandler;
import org.data.redroleplay.services.implementations.SecurityUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig  {

    private final SecurityUserDetailsService securityUserDetailsService;

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.addFilterBefore(new LoginPageFilter(), DefaultLoginPageGeneratingFilter.class);

        http.addFilterBefore(new RegistrationPageFilter(), UsernamePasswordAuthenticationFilter.class);

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
                .formLogin(login -> login
                        .loginPage("/login")
                        .failureUrl("/login?error=Invalid username or password.")
                        .defaultSuccessUrl("/home", true)
                        .successHandler(customAuthenticationSuccessHandler)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout&success=You have been logged out.")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
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

}
