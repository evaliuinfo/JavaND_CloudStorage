package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileListService {
    private List<String> files;

    public FileListService() {
        this.files = new ArrayList<>();
    }

    public void addFile(String file) {
        files.add(file);
    }

    public List<String> getFiles() {
        return new ArrayList<>(this.files);
    }

}
