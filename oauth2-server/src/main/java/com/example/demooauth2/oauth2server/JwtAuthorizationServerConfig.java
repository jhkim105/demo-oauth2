package com.example.demooauth2.oauth2server;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.OAuth2AuthorizationServerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class JwtAuthorizationServerConfig extends OAuth2AuthorizationServerConfiguration {

  public JwtAuthorizationServerConfig(BaseClientDetails details, AuthenticationManager authenticationManager, ObjectProvider<TokenStore> tokenStore, ObjectProvider<AccessTokenConverter> tokenConverter, AuthorizationServerProperties properties) {
    super(details, authenticationManager, tokenStore, tokenConverter, properties);
  }

}

