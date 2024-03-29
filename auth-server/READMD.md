# OAuth2 Authorization Server


## Basic
pom.xml
```xml
      <depedency>
        <groupId>org.springframework.security.oauth.boot</groupId>
        <artifactId>spring-security-oauth2-autoconfigure</artifactId>
      </dependency>

```

OAuth2ServerConfig.java
```java
@Configuration
@RequiredArgsConstructor
@Slf4j
public class OAuth2ServerConfig {


  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter(Environment env) {
    JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
    String tokenKey = env.getProperty("jwt.tokenKey");
    log.info("jwt.tokenKey:{}", tokenKey);
    jwtAccessTokenConverter.setSigningKey(tokenKey);
    return jwtAccessTokenConverter;
  }


  @Bean
  public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
    return new JwtTokenStore(jwtAccessTokenConverter);
  }

  @Configuration
  @EnableAuthorizationServer
  @RequiredArgsConstructor
  @Slf4j
  public static class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final DataSource dataSource;

    private final TokenStore jwtTokenStore;

    private final JwtAccessTokenConverter jwtAccessTokenConverter;

    @Qualifier("authenticationManagerBean")
    private final AuthenticationManager authenticationManager;



    @Override
    public void configure(
        AuthorizationServerSecurityConfigurer oauthServer)
        throws Exception {
      oauthServer
          .tokenKeyAccess("permitAll()")
          .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
      endpoints
          .authenticationManager(authenticationManager)
          .accessTokenConverter(jwtAccessTokenConverter)
          .tokenStore(jwtTokenStore);
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
      clients.jdbc(dataSource);
    }


  }

  @Configuration
  @EnableResourceServer
  @RequiredArgsConstructor
  @Slf4j
  public static class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final TokenStore jwtTokenStore;

    @Override
    public void configure(HttpSecurity http) throws Exception {
      //@formatter:off
      http
          .antMatcher("/userinfo").cors().and()
          .authorizeRequests()
            .antMatchers("/userinfo")
            .access("#oauth2.hasScope('read')")
            .anyRequest().permitAll();
      //@formatter:on
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
      resources.tokenServices(tokenServices());
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
      DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
      defaultTokenServices.setTokenStore(jwtTokenStore);
      return defaultTokenServices;
    }
  }
}

```
