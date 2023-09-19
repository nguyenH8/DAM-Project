package com.dam.server.services.iServices;

import com.dam.server.dto.AssetDTO;
import com.dam.server.entities.Asset;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IAssetService {
    String uploadAsset(MultipartFile file, String filename, Long userId) throws IOException;
    byte[] downloadAsset(String assetId) throws IOException;
    String deleteAsset(String assetId);
}
