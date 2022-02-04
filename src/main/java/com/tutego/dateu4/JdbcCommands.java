package com.tutego.dateu4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.w3c.dom.ls.LSOutput;

import java.lang.invoke.MethodHandles;

@ShellComponent
public class JdbcCommands {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @ShellMethod("hornlength")
    public void hornlength(String name) {
        String sql = "SELECT hornlength FROM Profile WHERE nickname = ?";//\""+name+"\"";
        Logger log = LoggerFactory.getLogger((MethodHandles.lookup().lookupClass()));

        int len = jdbcTemplate.queryForObject(sql, Integer.class, name);

        log.info( "Horn length: {}", len );
    }

    public int getHornlengthByNickname(String nick){
        String sql = "SELECT hornlength FROM profile WHERE nickname = ?";
        int len = jdbcTemplate.queryForObject(sql, Integer.class, nick);
        return len;
    }

}
