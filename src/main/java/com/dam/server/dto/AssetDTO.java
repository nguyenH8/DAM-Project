package com.dam.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AssetDTO {
    private String assetName;
    private String assetSize;
    private Date uploadedDate;
    private Date updatedDate;
    private Date downloadedDate;
}
