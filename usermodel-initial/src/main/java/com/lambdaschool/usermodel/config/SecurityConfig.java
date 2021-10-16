package com.lambdaschool.usermodel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // who has access to what..?, this annotation set individual control level!
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    @Bean // this will be using this one many places so it will have to be bean.
    // making beans is a spring way to handling things
    protected AuthenticationManager authenticationManager() throws Exception
    {
        return super.authenticationManager();
    }

    @Autowired
    private UserDetailsService securityUserService; // our user model telling others to use this model...

    @Autowired
    private void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserService).passwordEncoder(encoder()); // this might have been the problem..?
    }

    @Bean
    public TokenStore tokenStore()
    {
        return new InMemoryTokenStore();
    }

    @Bean
    public PasswordEncoder encoder()
    {
        return new BCryptPasswordEncoder();
    }
}
