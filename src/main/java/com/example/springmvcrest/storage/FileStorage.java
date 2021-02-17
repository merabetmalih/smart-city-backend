package com.example.springmvcrest.storage;

import java.io.InputStream;

public interface FileStorage {

    public String upload(String fileName, String path, InputStream file);

    public byte[] download(String fileName, String path);

    public void delete(String fileName, String path);
}
