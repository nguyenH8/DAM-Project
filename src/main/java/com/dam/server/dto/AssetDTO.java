package com.dam.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AssetDTO {
    private String assetId;
    private String assetName;
    private Long assetSize;
    private Date uploadedDate;
    private Date updatedDate;
    private Date downloadedDate;
}
