package com.example.oauth2;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @GetMapping(value = "/userinfo", produces = MediaType.APPLICATION_JSON_VALUE)
  public Map<String, Object> user(Principal principal) {
    return Collections.singletonMap("username", principal.getName());
  }

}
