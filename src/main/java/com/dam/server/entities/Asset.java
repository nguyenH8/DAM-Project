package com.dam.server.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="asset")
public class Asset {

    @Id
    @Builder.Default
    @Column(name="id")
    private String id = UUID.randomUUID().toString();

    @Column(name="asset_name")
    private String assetName;

    @Column(name="asset_size")
    private Long assetSize;

    @Column(name="asset_type")
    private String assetType;

    @Column(name="uploaded_date")
    private Date uploadedDate;

    @Column(name="downloaded_date")
    private Date downloadedDate;

    @Column(name="updated_date")
    private Date updatedDate;

    @ManyToOne
    @JoinColumn
    private User user;
}
