package com.example.demooauth2.oauth2server;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig {

//	@Bean
//  public TokenStore jdbcTokenStore(DataSource dataSource) {
//	  return new JdbcTokenStore(dataSource);
//  }
}
