package com.dam.server.controllers;

import com.dam.server.services.iServices.IAssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/assets")
@RequiredArgsConstructor
public class AssetController {
    @Autowired
    private final IAssetService assetService;


    @PostMapping("/upload")
    public ResponseEntity<String> uploadAsset(@RequestParam(value="file") MultipartFile file,
                                             @RequestParam String filename,
                                             @RequestParam Long userId)
            throws IOException {
        return new ResponseEntity<>(assetService.uploadAsset(file, filename, userId), HttpStatus.OK);
    }

    @GetMapping("/download/{assetId}")
    public ResponseEntity<ByteArrayResource> downloadAsset(@PathVariable String assetId) throws IOException {
        byte[] data = assetService.downloadAsset(assetId);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\""+ assetId + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete/{assetId}")
    public ResponseEntity<String> deleteAsset(@PathVariable String assetId){
        return new ResponseEntity<>(assetService.deleteAsset(assetId), HttpStatus.OK);
    }
}
