package com.example.springmvcrest.storage;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@AllArgsConstructor
public class LocalStorageService implements FileStorage {

    @Override
    public String upload(String fileName, String path, InputStream file) {
        try {
            String s = prepareDirectory(path);
            String fullName = String.format("%s/%s", s, fileName);
            Files.copy(file, Paths.get(fullName), StandardCopyOption.REPLACE_EXISTING);
            return fullName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileStorageException("error.file.upload");
        }
    }

    @Override
    public byte[] download(String fileName, String path) {
        try {
            String s = prepareDirectory(path);
            String fullName = String.format("%s/%s", s, fileName);
            if (!Paths.get(fullName).toFile().exists()) {
                return new byte[0];
            }
            return Files.readAllBytes(Paths.get(fullName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileStorageException("error.file.download");
        }
    }

    @Override
    public void delete(String fileName, String path) {
        try {
            String s = prepareDirectory(path);
            String fullName = String.format("%s/%s", s, fileName);
            if (Paths.get(fullName).toFile().exists()) {
                Files.delete(Paths.get(fullName));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileStorageException("error.file.delete");
        }
    }

    private String prepareDirectory(String path) {
        try {
            String dir = String.format("%s/%s", getHomeDirectory(), path);
            System.out.println(dir);
            Path fullPath = Paths.get(dir);
            if (!fullPath.toFile().exists()) {
                Files.createDirectory(fullPath);
            }
            return dir;
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileStorageException("error.file.create-dir.failed");
        }
    }

    private String getHomeDirectory() {
        return "C:\\Users\\ibrahim\\Desktop\\spring-mvc-rest";
        //return System.getProperty("user.home");
    }
}
