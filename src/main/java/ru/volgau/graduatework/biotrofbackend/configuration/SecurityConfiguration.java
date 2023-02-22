package ru.volgau.graduatework.biotrofbackend.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import ru.volgau.graduatework.biotrofbackend.security.JwtConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private final JwtConfigurer jwtConfigurer;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/register").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .apply(jwtConfigurer);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    AuthenticationManager customAuthenticationManager(UserDetailsService userDetailsService, PasswordEncoder encoder) {
        return authentication -> {
            String username = authentication.getPrincipal() + "";
            String password = authentication.getCredentials() + "";

            UserDetails user = userDetailsService.loadUserByUsername(username);

            if (!encoder.matches(password, user.getPassword())) {
                throw new BadCredentialsException("Bad credentials");
            }

            if (!user.isEnabled()) {
                throw new DisabledException("User account is not active");
            }

            return new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
        };
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }
}

