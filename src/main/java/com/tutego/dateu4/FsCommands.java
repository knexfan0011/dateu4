package com.tutego.dateu4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.*;

import java.io.File;

@ShellComponent
public class FsCommands {

  private final FileSystem fs;
  @Autowired
  public FsCommands(FileSystem fs){this.fs = fs;}

  @ShellMethod( "Show application version" )
  public String version() {
    return "0.1";
  }

  @ShellMethod( "Lowercase string" )
  public String toLowercase( String input ) { return input.toLowerCase(); }

  @ShellMethod ("Show free disk space")
  public String freeDiskSpace(){
    return ""+fs.getFreeDiskSpace()/Math.pow(1024, 3)+" GB";
  }
}