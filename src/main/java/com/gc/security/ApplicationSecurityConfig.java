package com.gc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import static com.gc.security.ApplicationUserRole.*;

import java.util.concurrent.TimeUnit;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
	                .antMatchers("/", "index", "/css/*", "/js/*", "/img/*").permitAll()
	                .antMatchers("/api/**").hasRole(USER.name())
	                //.antMatchers("/actuator/**").hasRole(ADMIN.name())
	                .anyRequest()
	                .authenticated()
	                .and()                
                .formLogin()
                    .loginPage("/login")
                    .permitAll()	//necessary so that anyone can try to login
                    .defaultSuccessUrl("/home", true) //true = force redirect
                    
                    //if password is to be added
                    //.passwordParameter("password")	//if changing parameter name, ex. passwordxyz then need to update name of input type=passoword to passwordxyz
                    .usernameParameter("username")
                .and()
                .rememberMe()	//defaults to 2 weeks, Network->Headers->Form Data->Remember Me: On and remember-me cookie
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21)) //tokenRepository if using your own database... this equals 21 days
                    .key("somethingverysecured") //key used to create md5 hash of username + expiration date
                    .rememberMeParameter("remember-me")
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) // because csrf is disabled- if enabled, delete this because it should be a POST- https://docs.spring.io/spring-security/site/docs/4.2.12.RELEASE/apidocs/org/springframework/security/config/annotation/web/configurers/LogoutConfigurer.html
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                    .logoutSuccessUrl("/login");
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() { //how users are retrieved from db
        UserDetails user = User.builder()
                .username("safe")
                .password(passwordEncoder.encode(""))
                .authorities(USER.getGrantedAuthorities())
                .build();

        UserDetails admin = User.builder()
                .username("unlimited")
                .password(passwordEncoder.encode(""))
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(
                user,
                admin
        );

    }
}
