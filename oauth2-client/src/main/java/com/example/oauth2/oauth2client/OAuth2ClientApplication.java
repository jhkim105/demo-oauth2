package com.example.oauth2.oauth2client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableOAuth2Client
public class OAuth2ClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(OAuth2ClientApplication.class, args);
	}


  @Autowired
  OAuth2ClientContext oauth2ClientContext;

  @Bean
  public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(filter);
    registration.setOrder(-100);
    return registration;
  }

  private Filter ssoFilter() {

    CompositeFilter filter = new CompositeFilter();
    List<Filter> filters = new ArrayList<>();


    OAuth2ClientAuthenticationProcessingFilter oauthFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/oauth");
    OAuth2RestTemplate oauthTemplate = new OAuth2RestTemplate(oauth(), oauth2ClientContext);
    oauthFilter.setRestTemplate(oauthTemplate);
    UserInfoTokenServices tokenServices= new UserInfoTokenServices(oauthResource().getUserInfoUri(), oauth().getClientId());
    tokenServices.setRestTemplate(oauthTemplate);
    oauthFilter.setTokenServices(tokenServices);
    filters.add(oauthFilter);

    filter.setFilters(filters);
    return filter;

  }

  @Bean
  @ConfigurationProperties("oauth.client")
  public AuthorizationCodeResourceDetails oauth() {
    return new AuthorizationCodeResourceDetails();
  }

  @Bean
  @ConfigurationProperties("oauth.resource")
  public ResourceServerProperties oauthResource() {
    return new ResourceServerProperties();
  }



  @Configuration
  class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.antMatcher("/**")
            .authorizeRequests()
            .antMatchers("/", "/login/**").permitAll()
            .anyRequest().authenticated()
            .and()
          .logout()
            .logoutSuccessUrl("/").permitAll()
            .and()
          .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
          .and()
        .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
    }
  }

}
