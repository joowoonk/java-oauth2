package com.lambdaschool.usermodel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private static String RESOURCE_ID = "resource_id";

    @Override
    public void configure (ResourceServerSecurityConfigurer resources)
    {
        resources.resourceId(RESOURCE_ID)
                .stateless(false); //REST API is a stateless system so backend wont remember.
        // true could work but whenenver we are trying to make autmoation testing to work, we have to say this as false
    }

    @Override
    public void configure (HttpSecurity http) throws Exception
    {
        // who has access to what
        http.authorizeRequests()
                .antMatchers("/",
                        "/h2-console/**",
                        "/swagger-resources/**",
                        "/swagger-resource/**",
                        "/swagger-ui.html",
                        "/v2/api-docs",
                        "/webjars/**",
                        "/createnewuser") // everyone has h2 access
                .permitAll()
                .antMatchers("/users/**")
                .hasAnyRole("ADMIN","USER")
                .antMatchers("/roles/**")
                .hasAnyRole("DATA")
                .antMatchers("/logout") // only those who has access tokens can run this
                .authenticated()
                .anyRequest().denyAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new OAuth2AccessDeniedHandler()); //Only OAtuh work this... others will be denied!

        http.csrf().disable();

        http.headers().frameOptions().disable(); // required by H2

        http.logout().disable();
    }
}
