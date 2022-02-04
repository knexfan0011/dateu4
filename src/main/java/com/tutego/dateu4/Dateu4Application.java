package com.tutego.dateu4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;

@SpringBootApplication
public class Dateu4Application {
    //private final Logger log = LoggerFactory.getLogger( getClass() );
    @Autowired JdbcTemplate jdbcTemplate;

     public static void main(String[] args) {
         Logger log = LoggerFactory.getLogger((MethodHandles.lookup().lookupClass()));
         FileSystem fs = new FileSystem();
         var ctx = SpringApplication.run(Dateu4Application.class, args);
         //Arrays.stream(ctx.getBeanDefinitionNames()).sorted().forEach(log::info);


    }
}
