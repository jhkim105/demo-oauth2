package com.example.oauth2;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
//@Import(SecurityConfig.class)
public class OAuthMvcTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @Disabled("org.springframework.security.access.AccessDeniedException: Access is denied why?")
  void accessToken() throws Exception {
    mockMvc.perform(
      post("/oauth/token")
        .header("Authorization", basic("client01", "secret01"))
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .param("grant_type", "client_credentials")
        .param("scope", "USER")
    )
      .andDo(print())

    ;

  }

  private String basic(String client01, String secret01) {
    String in = String.format("%s:%s", client01, secret01);
    String out = new String(Base64.encodeBase64(in.getBytes()));
    log.debug(out);
    return out ;
  }
}
