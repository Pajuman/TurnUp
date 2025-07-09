package com.learn.turnup.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class Debugger {
  private static final Logger logger = LoggerFactory.getLogger(Debugger.class);

  @PostConstruct
  public void logProfile() {
    logger.info("Active Spring Profile logged here!");
  }
}
