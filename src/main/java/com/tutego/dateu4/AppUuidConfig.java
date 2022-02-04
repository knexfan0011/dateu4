package com.tutego.dateu4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
// @Component
class AppUuidConfig {
  private final Logger log = LoggerFactory.getLogger( getClass() );
  public AppUuidConfig() { log.info( getClass().getName() ); }
  @Bean
  String appUuid() {
    String uuid = UUID.randomUUID().toString();
    log.info( "uuid -> {}", uuid );
    return uuid;
  }
  @Bean String shortAppUuid() {
    String uuid = appUuid().substring( 0, appUuid().length() / 2 );
    log.info( "short uuid -> {}", uuid );
    return uuid;
  }
}