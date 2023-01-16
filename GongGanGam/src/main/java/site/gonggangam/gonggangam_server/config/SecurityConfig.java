package site.gonggangam.gonggangam_server.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import site.gonggangam.gonggangam_server.config.auth.DelegatedAccessDeniedHandler;
import site.gonggangam.gonggangam_server.config.auth.DelegatedAuthenticationEntryPoint;
import site.gonggangam.gonggangam_server.config.auth.JwtAuthenticationFilter;
import site.gonggangam.gonggangam_server.config.auth.JwtProvider;

@EnableWebSecurity
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/swagger-ui/**",
            "/error",
            "/api/auth/**"
    };


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().mvcMatchers(AUTH_WHITELIST);
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver,
            JwtProvider jwtProvider
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
                    .antMatchers("/api/**").access("hasRole('USER') or hasRole('ADMIN')")
                    .anyRequest().permitAll()
                    .and()
                .exceptionHandling()
                    .authenticationEntryPoint(new DelegatedAuthenticationEntryPoint(resolver))
                    .accessDeniedHandler(new DelegatedAccessDeniedHandler(resolver))
                    .and()
                .formLogin().disable()
                .httpBasic().disable()
//                .addFilterBefore(new JwtAuthenticationFilter(resolver, jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
