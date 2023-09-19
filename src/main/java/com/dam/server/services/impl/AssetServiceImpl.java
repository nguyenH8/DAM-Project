package com.dam.server.services.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.dam.server.entities.Asset;
import com.dam.server.entities.User;
import com.dam.server.repositories.AssetRepository;
import com.dam.server.repositories.UserRepository;
import com.dam.server.services.iServices.IAssetService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AssetServiceImpl implements IAssetService {
    private final String bucketName;
    private final AmazonS3 s3Client;
    private final AssetRepository assetRepository;
    private final UserRepository userRepository;

    @Autowired
    public AssetServiceImpl(@Value("${cloud.aws.s3.bucketName}") String bucketName, AmazonS3 s3Client, AssetRepository assetRepository, UserRepository userRepository) {
        this.bucketName = bucketName;
        this.s3Client = s3Client;
        this.assetRepository = assetRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String uploadAsset(MultipartFile file, String filename, Long userId) throws IOException {
        File convertedFile = convertMultipartFileToFile(file);
        Long fileSize = convertedFile.length();

        String contentType = file.getContentType();

        // Get current User
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Asset uploadedAsset = Asset.builder()
                .assetName(filename)
                .assetSize(fileSize)
                .assetType(contentType)
                .uploadedDate(new Date())
                .downloadedDate(new Date())
                .updatedDate(new Date())
                .user(currentUser)
                .build();
        assetRepository.save(uploadedAsset);

        //Upload file to S3
        s3Client.putObject(bucketName, uploadedAsset.getId(), convertedFile);

        if(!convertedFile.delete()){
            throw new RuntimeException("Failed to delete" + filename);
        }

        return "File was uploaded: " + filename;
    }

    @Override
    public byte[] downloadAsset(String assetId) throws IOException {
        Asset existingAsset = assetRepository.findById(assetId)
                .orElseThrow(() -> new RuntimeException("Asset not found with id: " + assetId));
        existingAsset.setDownloadedDate(new Date());
        existingAsset.setUpdatedDate(new Date());

        S3Object s3Object = s3Client.getObject(bucketName, assetId);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();


        return IOUtils.toByteArray(inputStream);
    }

    @Override
    public String deleteAsset(String assetId) {
        Asset existingAsset = assetRepository.findById(assetId)
                .orElseThrow(() -> new RuntimeException("Asset not found with id: " + assetId));

        User user = existingAsset.getUser();
        user.getAssets().remove(existingAsset);
        existingAsset.setUser(null);

        s3Client.deleteObject(bucketName, assetId);
        assetRepository.deleteById(assetId);

        return "File was deleted: " + assetId;
    }

    public List<Asset> getAllAssets(){
        return assetRepository.findAll();
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
