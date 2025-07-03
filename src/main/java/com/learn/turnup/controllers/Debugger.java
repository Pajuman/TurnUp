package com.learn.turnup.controllers;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Debugger {
  @Value("${spring.profiles.active:default}")
  private String activeProfile;

  @PostConstruct
  public void logProfile() {
    System.out.println("Active Spring Profile: " + activeProfile);
  }
}
