package org.agingpuzzle.web.config;

import org.agingpuzzle.web.WebUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/*/organizations/**").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
            .formLogin()
                .loginProcessingUrl("/*/login")
                .failureHandler(authenticationFailureHandler())
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and()
            .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint());
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("Maxi")
                        .passwordEncoder(passwordEncoder::encode)
                        .password("puzzle" + LocalDateTime.now().getDayOfMonth())
                        .roles("ADMIN")
                        .build()
        );
    }

    private AuthenticationEntryPoint authenticationEntryPoint() {
        return new LoginUrlAuthenticationEntryPoint("/login") {
            @Override
            protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
                String url = getLoginFormUrl();
                String lang = WebUtils.getLanguageFromUrl(request.getServletPath());
                return String.format("/%s%s", lang, url);
            }
        };
    }

    private AuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                saveException(request, exception);

                String url = request.getServletPath() + "?error";
                logger.debug("Redirecting to " + url);
                getRedirectStrategy().sendRedirect(request, response, url);
            }
        };
    }

}
