package com.dam.server.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class AmazonService {
    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = "Filename";

        File convertedFile = convertMultipartFileToFile(file);
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, convertedFile));

        convertedFile.delete();
        if(!convertedFile.delete()) {
            System.out.println("Failed to delete " + convertedFile.getName());
        }

        return "File uploaded: " + fileName;
    }

    public byte[] downloadFile(String fileName) throws IOException{
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        byte[] content = IOUtils.toByteArray(inputStream);
        return content;
    }

    public String deleteFile(String fileName){
        s3Client.deleteObject(bucketName, fileName);
        return fileName + "removed";
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
