# Spring Boot OAuth2 Client


## Client Setting for Custom OAuth Provider
pom.xml
```xml
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-oauth2-client</artifactId>
		</dependency>
```

application.properties
```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          my-oauth:
            provider: custom
            client-id: client01
            client-secret: secret01
            client-name: client01
            scope: read
            redirect-uri: http://localhost:8080/login/oauth2/code/my-oauth
            authorization-grant-type: authorization_code
            client-authentication-method: basic
        provider:
          custom:
            authorization-uri: http://localhost:8081/oauth/authorize
            token-uri: http://localhost:8081/oauth/token
            user-info-uri: http://localhost:8081/userinfo
            user-name-attribute: username
```

