package site.gonggangam.gonggangam_server.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import site.gonggangam.gonggangam_server.config.auth.DelegatedAccessDeniedHandler;
import site.gonggangam.gonggangam_server.config.auth.DelegatedAuthenticationEntryPoint;
import site.gonggangam.gonggangam_server.config.auth.JwtAuthenticationFilter;
import site.gonggangam.gonggangam_server.config.auth.JwtProvider;
import site.gonggangam.gonggangam_server.service.OAuthService;

@EnableWebSecurity
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/swagger-ui/**",
            "/api-docs/**",
            "/error/**",
            "/api/auth/**"
    };

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(AUTH_WHITELIST);
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver,
            JwtProvider jwtProvider,
            OAuthService oAuthService
    ) throws Exception {
        return http
                .httpBasic()
                    .disable()
                .cors()
                    .disable()
                .csrf()
                    .disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .authorizeRequests()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/api/**").access("hasRole('ADMIN') or hasRole('USER')")
                    .anyRequest().permitAll()
                    .and()
                .exceptionHandling()
                    .authenticationEntryPoint(new DelegatedAuthenticationEntryPoint(resolver))
                    .accessDeniedHandler(new DelegatedAccessDeniedHandler(resolver))
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider, oAuthService), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
