package com.example.Binarfud.security;

import com.example.Binarfud.security.jwt.JwtAuthTokenFilter;

import com.example.Binarfud.security.service.UserDetailsServiceImpl;
import com.example.Binarfud.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableMethodSecurity
@Slf4j
public class SecurityConfig implements WebMvcConfigurer {


    final UserDetailsServiceImpl userDetailsService;
    final UsersService userService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, @Lazy UsersService userService) {

        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }


    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/api/auth/signup").permitAll()
                                .requestMatchers("/api/auth/signin").permitAll()
                                .requestMatchers("/api/auth/loginoauth").permitAll()
                                .requestMatchers("/api/auth/login").permitAll()
                                .requestMatchers("/api/auth/oauth2/success").permitAll()
                                .requestMatchers("/login").permitAll()
                                .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                                .loginPage("/login")
                                .userInfoEndpoint(userInfo -> userInfo
                                        .oidcUserService(this.oidcUserService())
                                )
                        .successHandler((request, response, authentication) -> {
                            try {
                                if (authentication != null) {
                                    // Logika bisnis setelah autentikasi berhasil
                                    log.info("Authentication success: " + authentication);
                                    log.info("response" + response);
                                    DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
                                    userService.createUserPostLogin(oidcUser.getAttribute("email"), oidcUser.getAttribute("email"));
                                    log.info("User created: " + oidcUser);

                                    // Redirect to the new endpoint after successful login
                                    response.sendRedirect("/api/auth/oauth2/success");
                                } else {
                                    // Jika autentikasi null, log pesan kesalahan
                                    log.error("Authentication is null");
                                    // Tambahkan respons atau tindakan lain sesuai kebutuhan
                                }
                            } catch (NullPointerException e) {
                                // Tangani kasus NullPointerException
                                log.error("Exception occurred during authentication success handling: " + e.getMessage());
                                // Tambahkan respons atau tindakan lain sesuai kebutuhan
                            }
                        })
                );
        return http.build();
    }

    @Bean
    public JwtAuthTokenFilter authTokenFilter(){
        return new JwtAuthTokenFilter();
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.debug(false)
                .ignoring()
                .requestMatchers("/webjars/**", "/images/**", "/css/**", "/assets/**", "/favicon.ico");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    @Bean
    public OidcUserService oidcUserService() {
        OidcUserService delegate = new OidcUserService();
        return delegate;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
                .favorParameter(false)
                .ignoreAcceptHeader(true)
                .defaultContentType(MediaType.APPLICATION_JSON);
    }
}