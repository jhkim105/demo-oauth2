package com.example.demooauth2.demoapi;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.io.Serializable;

@Entity
@Getter @Setter @ToString
public class User implements Serializable {

  @Id
  @GeneratedValue
  private Long id;

  private String name;

  private String username;


  @Lob
  private String description;


}
