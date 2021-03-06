package com.techprimers.security.securitydbexample.config;

import java.util.Arrays;

import com.techprimers.security.securitydbexample.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    private AuthSuccessHandler authSuccessHandler;


    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService)
        .passwordEncoder(this.getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.authorizeRequests()
        .antMatchers("/rest/home").permitAll()
        .antMatchers("/").permitAll()
        .antMatchers("/users/**").permitAll()
        .antMatchers("**/user-not-found").permitAll()
        .antMatchers("/add").permitAll()
        .antMatchers("/rest/user").permitAll()
        .antMatchers("/free/**").access("hasRole('ROLE_SUPERADMIN')")
        .antMatchers("/login").permitAll()
        .antMatchers("/rest/user-now").permitAll()  
        .anyRequest().authenticated()
        .and().cors().configurationSource(corsConfigurationSource())
        .and().formLogin().permitAll().successHandler(authSuccessHandler).failureHandler(customAuthenticationEntryPoint);
        http.exceptionHandling().accessDeniedPage("/acces-denied");
        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

    }

    @Bean   
    public PasswordEncoder getPasswordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
    }

    /*
  This will be used to create the json we'll send back to the client from
  the CustomAuthenticationEntryPoint class.
*/
  
//this is work for Spring security
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200",
                         "https://diary-for-travellers.herokuapp.com"));
    configuration.setAllowedMethods(Arrays.asList("*"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}
}
