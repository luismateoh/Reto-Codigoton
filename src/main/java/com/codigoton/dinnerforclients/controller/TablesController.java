package com.codigoton.dinnerforclients.controller;

import com.codigoton.dinnerforclients.service.FileStorageService;
import com.codigoton.dinnerforclients.service.TablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping(path = "/v1/tables")
@Validated
@CrossOrigin("http://localhost:8080")
public class TablesController {
    @Autowired
    private FileStorageService storageService;

    @Autowired
    private TablesService tablesService;

    @RequestMapping(path = "/tablesAssignment",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Resource> uploadFile(@RequestPart("file") MultipartFile file) throws IOException {
        storageService.saveFilters(file);
        Resource fileOut = tablesService.tablesAssignment(storageService.loadFilters(file.getOriginalFilename()).getFile());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileOut.getFilename() + "\"")
                .body(fileOut);

    }

}
