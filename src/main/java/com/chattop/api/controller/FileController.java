package com.chattop.api.controller;

import com.chattop.api.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;




@RestController
public class FileController {
    @Autowired
    private FilesStorageService filesStorageService;
    @GetMapping("/api/uploads/{img}")
    public Resource getImg(@PathVariable("img")String url){
        return filesStorageService.load(url);
    }
}
