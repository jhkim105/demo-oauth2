package com.example.demooauth2.oauth2server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

//@Configuration
//@EnableAuthorizationServer
public class AuthorizationServerConfig {

//	@Bean
//  public TokenStore jdbcTokenStore(DataSource dataSource) {
//	  return new JdbcTokenStore(dataSource);
//  }


}
