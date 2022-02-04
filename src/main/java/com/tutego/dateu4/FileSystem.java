package com.tutego.dateu4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileSystem {  // https://tiny.one/2p8whad4

    long SPACE_NEEDED = Long.MAX_VALUE;
    private final Path root =
            Paths.get(System.getProperty("user.home")).resolve("fs").resolve("img");

    public FileSystem(){
        Logger log = LoggerFactory.getLogger((MethodHandles.lookup().lookupClass()));
        log.info("---> Log in FS constructor <---");
        try {
            if (!Files.isDirectory(root)) Files.createDirectory(root);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        try {
            if (getFreeDiskSpace() < SPACE_NEEDED)
                throw new NotEnoughSpaceException(""+getFreeDiskSpace()+"----> I NEEDZ MOAR SPAZE!!1!1! <----");
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public long getFreeDiskSpace() {

        return com.tutego.dateu4.Dateu4Properties.FileSystemCustom.freeDiskSpace;
        /*root.toFile().getFreeSpace();*/
    }

    public byte[] load(String filename) {
        try {
            return Files.readAllBytes(root.resolve(filename));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void store(String filename, byte[] bytes) {
        try {
            Files.write(root.resolve(filename), bytes);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    static class NotEnoughSpaceException extends Exception implements ExitCodeGenerator {
        NotEnoughSpaceException(final String message) {
            super(message);
        }
        /**
         * @return Always return 42 as exit code when this exception occurs.
         */
        @Override
        public int getExitCode() {
            return 42;
        }
    }
}
