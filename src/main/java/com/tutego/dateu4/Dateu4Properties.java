package com.tutego.dateu4;

import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class Dateu4Properties {

    public static class FileSystemCustom {
        //private final Path root = Paths.get(System.getProperty("user.home")).resolve("fs");

        public static long freeDiskSpace = new com.tutego.dateu4.FileSystem().getFreeDiskSpace();


        /*
        public long freeDiskSpace() {
            return root.toFile().getFreeSpace();
        }
        public void setFreeDiskSpace(long bytes){

        }*/

    }


    public static record fileSystemRecord(long freeBytesLeft){

    }
}

